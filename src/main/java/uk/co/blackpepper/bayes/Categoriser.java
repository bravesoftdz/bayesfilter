package uk.co.blackpepper.bayes;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by davidg on 11/04/2017.
 */
public class Categoriser {

    private final Map<String, SampleSource> sampleSourceMap;

    public Categoriser() {
        this(new HashMap<>());
    }

    public Categoriser(Map<String,SampleSource> sampleSourceMap) {
        this.sampleSourceMap = sampleSourceMap;
    }

    public Categoriser category(String categoryName, SampleSource sample) {
        Map<String, SampleSource> map = new HashMap<>(sampleSourceMap);
        map.put(categoryName, sample);
        return new Categoriser(map);
    }

    public String getProbableCategoryFor(String text) throws IOException {
        double prob = 0;
        String category = "UNKNOWN";

        for (Map.Entry<String,SampleSource> entry : sampleSourceMap.entrySet()) {
            double probability = getProbability(text, entry);

            if (probability > prob) {
                prob = probability;
                category = entry.getKey();
            }
        }

        return category;
    }

    public double getProbabilityInCategory(String text, String category) {
        for (Map.Entry<String, SampleSource> entry : sampleSourceMap.entrySet()) {
            if (entry.getKey().equals(category)) {
                return getProbability(text, entry);
            }
        }
        return 0;
    }

    //<editor-fold desc="Utility methods">
    private double getProbability(String text, Map.Entry<String, SampleSource> entry) {
        SampleSource sampleSource = entry.getValue();
        Concordance othersConcordance = new Concordance("");
        int othersCount = 0;
        for (Map.Entry<String,SampleSource> others : sampleSourceMap.entrySet()) {
            if (!others.getKey().equals(entry.getKey())) {
                othersConcordance = othersConcordance.merge(others.getValue().concordance());
                othersCount += others.getValue().sampleCount();
            }
        }
        return getProbability(text,
                sampleSource.sampleCount(), sampleSource.concordance(),
                othersCount, othersConcordance);
    }

    private double getProbability(String text,
                                 int inCategoryCount, Concordance categoryConcordance,
                                 int outOfCategoryCount, Concordance outOfCategoryConcordance) {
        HashMap<String, Double> probabilityMap = getProbabilityMap(
                text,
                categoryConcordance, outOfCategoryConcordance,
                inCategoryCount, outOfCategoryCount);

        HashMap<String, Double> topMap = getTopMap(probabilityMap);
        System.err.println("topMap = " + topMap);

        Collection<Double> values = topMap.values();
        double product = values.stream().reduce(1.0, (a, b) -> a * b);
        double productOneMinus = values.stream().reduce(1.0, (a, b) -> a * (1 - b));
        return product / (product + productOneMinus);
    }

    private HashMap<String, Double> getTopMap(HashMap<String, Double> probabilityMap) {
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

    private HashMap<String, Double> getProbabilityMap(String text, Concordance categoryConcordance,
                                                      Concordance outOfCategoryConcordance,
                                                      int inCategoryCount, int outOfCategoryCount) {
        Parseable parseable = new TextParser();
        List<String> tokenise = parseable.tokenise(text);
        List<String> distinctWords = tokenise.stream().distinct().collect(Collectors.toList());

        HashMap<String, Double> probs = new HashMap<>();
        for (String word : distinctWords) {
            double value = wordProbability(word,
                    outOfCategoryConcordance, categoryConcordance,
                    outOfCategoryCount, inCategoryCount);
            if (value >= 0) {
                probs.put(word, value);
            }
        }
        return probs;
    }

    private double wordProbability(String word,
                                   Concordance outOfCategoryConcordance, Concordance categoryConcordance,
                                   int outOfCategoryCount, int inCategoryCount) {
        int goodCount = outOfCategoryConcordance.count(word);
        int badCount = categoryConcordance.count(word);
        double g = 2 * goodCount;
        if (g + (double) badCount >= 5) {
            return Math.max(.01, Math.min(.99,
                    Math.min(1, ((double) badCount / inCategoryCount))
                            /
                            (Math.min(1, (g / outOfCategoryCount)) + Math.min(1, ((double) badCount / inCategoryCount)))
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
        return (Comparator<Map.Entry<String, Double>> & Serializable)
                (c1, c2) -> {
                    double v1 = Math.abs(c1.getValue().doubleValue() - 0.5);
                    double v2 = Math.abs(c2.getValue().doubleValue() - 0.5);
                    int i = (int) (1000000000 * (v2 - v1));
                    return i;
                };
    }
    //</editor-fold>

}
