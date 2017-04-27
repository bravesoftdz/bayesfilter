package uk.co.blackpepper.bayes.naive;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by davidg on 11/04/2017.
 */
public class Categoriser {

    private final Map<String, SampleSource> sampleSourceMap;
    private final TextParser textParser;
    private final int topWordsConsidered;

    /**
     * Create a blank categoriser with no sample data.
     */
    public Categoriser() {
        this(new HashMap<>());
    }

    /**
     * Instantiates a new Categoriser using a specific parser.
     *
     * @param textParser the text parser
     */
    public Categoriser(TextParser textParser) {
        this(new HashMap<>(), textParser);
    }

    /**
     * Instantiates a new Categoriser with a map of sample sources: category-name --> sample-source
     *
     * @param sampleSourceMap the sample source map
     */
    public Categoriser(Map<String,SampleSource> sampleSourceMap) {
        this(sampleSourceMap, new AsciiTextParser());
    }

    /**
     * Instantiates a new Categoriser with a map of sample sources: category-name --> sample-source and a parser
     *
     * @param sampleSourceMap the sample source map
     * @param textParser      the text parser
     */
    public Categoriser(Map<String,SampleSource> sampleSourceMap, TextParser textParser) {
        this(sampleSourceMap, textParser, 15);
    }

    /**
     * Instantiates a new Categoriser with a map of sample sources: category-name --> sample-source, a parser and a
     * specified number of 'top words' to consider when analysing a document.
     *
     * @param sampleSourceMap    the sample source map
     * @param textParser         the text parser
     * @param topWordsConsidered the top words considered
     */
    public Categoriser(Map<String,SampleSource> sampleSourceMap, TextParser textParser, int topWordsConsidered) {
        this.sampleSourceMap = sampleSourceMap;
        this.textParser = textParser;
        this.topWordsConsidered = topWordsConsidered;
    }

    /**
     * Method to make it a little simpler to create a categoriser with a given category and source, e.g.
     *
     * new Categoriser().category("spam", spamSampleSource).category("cornedBeed", cornedBeefSource);
     *
     * @param categoryName the category name
     * @param sample       the sample
     * @return the categoriser
     */
    public Categoriser category(String categoryName, SampleSource sample) {
        Map<String, SampleSource> map = new HashMap<>(sampleSourceMap);
        map.put(categoryName, sample);
        return new Categoriser(map, textParser, topWordsConsidered);
    }

    /**
     * Set the number of words considered when analysing. The higher the number, the greater the accuracy, but the
     * slower the processing. Defaults to 15.
     *
     * @param words -- the number of 'most important' words to consider
     * @return a new categoriser
     */
    public Categoriser topWordsConsidered(int words) {
        return new Categoriser(new HashMap<>(sampleSourceMap), textParser, words);
    }

    /**
     * Which category is the most likely for the given text
     * <p>
     * If all categories have probability 0, return "UNKNOWN" (that probably means it found no words in common at all).
     *
     * @param text -- what we're analysing
     * @return the name of the category
     */
    public String getProbableCategoryFor(String text) {
        double prob = 0;
        String category = "UNKNOWN";

        for (Map.Entry<String,SampleSource> entry : sampleSourceMap.entrySet()) {
            double probability = getProbability(text, entry.getValue(), createCombinedSourceOfOthers(entry.getKey()));

            if (probability > prob) {
                prob = probability;
                category = entry.getKey();
            }
        }

        return category;
    }

    /**
     * Get the probability that the given piece of text is in the specified category
     *
     * @param text     -- the text we're analysing
     * @param category -- the category we're checking against
     * @return the probability (0.0 to 1.0)
     */
    public double getProbabilityInCategory(String text, String category) {
        return getProbability(text, sampleSourceMap.get(category), createCombinedSourceOfOthers(category));
    }

    /**
     * A map of words that are used to decide if the given text matches the given category.
     * each word is matched to the weight of evidence of a match.
     * <p>
     * For example, if you are comparing a piece of text against a category of business news items,
     * then the map might contain the word "money" with a value of 0.8 (this a good match for the category)
     * and "kitten" with a value of 0.1 (this is a bad match for the category).
     * <p>
     * This map might be used to highlight significant words with a high value for the match.
     *
     * @param text     -- the text we're analysing
     * @param category -- the category we are comparing the text against.
     * @return map
     */
    public Map<String,Double> interestingWords(String text, String category) {

        if (!sampleSourceMap.containsKey(category)) {
            return new HashMap<>();
        }
        return interestingWords(getProbabilityMap(text, sampleSourceMap.get(category),
                createCombinedSourceOfOthers(category)));
    }

    //<editor-fold desc="Utility methods">
    /**
     * Create a sample source that represents all of the samples *except* the given category.
     * So, if this categoriser has categories a, b, c and d, calling this method for category "d" will
     * create a sample source representing a combined source of samples including everything from a, b and c
     *
     * @param category -- the name of the category we *don't* want
     * @return everything except the given category as a single source
     */
    private SampleSource createCombinedSourceOfOthers(String category) {
        return sampleSourceMap.entrySet().stream()
                .filter(e -> !e.getKey().equals(category))
                .map(Map.Entry::getValue)
                .reduce(new SimpleSampleSource(), SampleSource::merge);
    }

    private double getProbability(String text, SampleSource inSamples, SampleSource outSamples) {
        Map<String, Double> topMap = interestingWords(getProbabilityMap(text, inSamples, outSamples));

        Collection<Double> values = topMap.values();
        double product = values.stream().reduce(1.0, (a, b) -> a * b);
        double productOneMinuses = values.stream().reduce(1.0, (a, b) -> a * (1 - b));
        return product / (product + productOneMinuses);
    }

    /**
     * Get the most interesting words from a word probability map
     *
     * Creates a sorted map, and limits it to the first n, where n is given by topWordsConsidered.
     *
     * @param probabilityMap -- the map to find the words from
     * @return A map of the most interesting words, word --> probability
     */
    private Map<String, Double> interestingWords(Map<String, Double> probabilityMap) {
        return sort(probabilityMap).entrySet().stream().limit(topWordsConsidered)
                .collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);
    }

    private Map<String, Double> getProbabilityMap(String text, SampleSource inSamples, SampleSource outSamples) {
        List<String> words = textParser.words(text);
        List<String> distinctWords = words.stream().distinct().collect(toList());

        HashMap<String, Double> probs = new HashMap<>();
        for (String word : distinctWords) {
            double value = wordProbability(word, inSamples, outSamples);
            if (value >= 0) {
                probs.put(word, value);
            }
        }

        return probs;
    }

    private double wordProbability(String word, SampleSource inSamples, SampleSource outSamples) {
        int goodCount = outSamples.concordance().count(word);
        int badCount = inSamples.concordance().count(word);
        double g = 2 * (double)goodCount;
        if (g + (double) badCount >= 5) {
            int inSampleCount = inSamples.sampleCount();
            int outSampleCount = outSamples.sampleCount();
            return Math.max(.01, Math.min(.99,
                    Math.min(1, (double) badCount / inSampleCount)
                            /
                            (Math.min(1, g / outSampleCount) + Math.min(1, (double) badCount / inSampleCount))
            ));
        }
        return -1;
    }

    private static Map<String, Double> sort(Map<String, Double> map) {
        return map.entrySet()
                .stream()
                .sorted(comparingByDistinctFromHalf())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private static Comparator<Map.Entry<String,Double>> comparingByDistinctFromHalf() {
        return (c1, c2) -> {
            double v1 = Math.abs(c1.getValue() - 0.5);
            double v2 = Math.abs(c2.getValue() - 0.5);
            return (int) (1000000000 * (v2 - v1));
        };
    }
    //</editor-fold>
}
