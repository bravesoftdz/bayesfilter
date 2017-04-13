package uk.co.blackpepper.bayes;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by davidg on 11/04/2017.
 */
public class Categoriser {

    private final Map<String, SampleSource> sampleSourceMap;
    private TextParser textParser;

    public Categoriser() {
        this(new HashMap<>());
    }

    public Categoriser(TextParser textParser) {
        this(new HashMap<>(), textParser);
    }

    public Categoriser(Map<String,SampleSource> sampleSourceMap) {
        this(sampleSourceMap, new AsciiTextParser());
    }

    public Categoriser(Map<String,SampleSource> sampleSourceMap, TextParser textParser) {
        this.sampleSourceMap = sampleSourceMap;
        this.textParser = textParser;
    }

    public Categoriser category(String categoryName, SampleSource sample) {
        textParser = new AsciiTextParser();
        Map<String, SampleSource> map = new HashMap<>(sampleSourceMap);
        map.put(categoryName, sample);
        return new Categoriser(map);
    }

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

    public double getProbabilityInCategory(String text, String category) {
        return getProbability(text, sampleSourceMap.get(category), createCombinedSourceOfOthers(category));
    }

    /**
     * A map of words that are used to decide if the given text matches the given category.
     * each word is matched to the weight of evidence of a match.
     *
     * For example, if you are comparing a piece of text against a category of business news items,
     * then the map might contain the word "money" with a value of 0.8 (this a good match for the category)
     * and "kitten" with a value of 0.1 (this is a bad match for the category).
     *
     * This map might be used to highlight significant words with a high value for the match.
     *
     * @param text -- the text we're analysing
     * @param category -- the category we are comparing the text against.
     * @return
     */
    public Map<String,Double> interestingWords(String text, String category) {

        return interestingWords(getProbabilityMap(text, sampleSourceMap.get(category), createCombinedSourceOfOthers(category)));
    }

    //<editor-fold desc="Utility methods">
    /**
     * Create a sample source that represents all of the samples *except* the given category.
     * So, if this categoriser has categories a, b, c and d, calling this method for category "d" will
     * create a sample source representing a combined source of samples including everything from a, b and c
     *
     * @param category
     * @return
     */
    private SampleSource createCombinedSourceOfOthers(String category) {
        Concordance othersConcordance = new Concordance("");
        int othersCount = 0;
        for (Map.Entry<String,SampleSource> others : sampleSourceMap.entrySet()) {
            if (!others.getKey().equals(category)) {
                othersConcordance = othersConcordance.merge(others.getValue().concordance());
                othersCount += others.getValue().sampleCount();
            }
        }
        final Concordance concordance = othersConcordance;
        final int sampleCount = othersCount;
        return new SampleSource() {
            @Override
            public int sampleCount() {
                return sampleCount;
            }

            @Override
            public Concordance concordance() {
                return concordance;
            }
        };
    }

    private double getProbability(String text,
                                  SampleSource inSamples,
                                  SampleSource outSamples) {

        Map<String, Double> topMap = interestingWords(getProbabilityMap(text, inSamples, outSamples));

        Collection<Double> values = topMap.values();
        double product = values.stream().reduce(1.0, (a, b) -> a * b);
        double productOneMinuses = values.stream().reduce(1.0, (a, b) -> a * (1 - b));
        return product / (product + productOneMinuses);
    }

    private Map<String, Double> interestingWords(Map<String, Double> probabilityMap) {
        Map<String, Double> sortedProbabilityMap = sort(probabilityMap);
        int count = 15;
        HashMap<String, Double> topMap = new HashMap<>();
        for (Map.Entry<String,Double> entry : sortedProbabilityMap.entrySet()) {
            if (count-- == 0) {
                break;
            }
            topMap.put(entry.getKey(), entry.getValue());
        }
        return topMap;
    }

    private Map<String, Double> getProbabilityMap(String text,
                                                      SampleSource inSamples,
                                                      SampleSource outSamples) {
        List<String> tokenise = textParser.words(text);
        List<String> distinctWords = tokenise.stream().distinct().collect(Collectors.toList());

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
