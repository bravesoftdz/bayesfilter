package uk.co.blackpepper.bayes;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by davidg on 11/04/2017.
 */
public class Bayes {
    public double getProbability(String text, int sportCount, Map<String, Integer> sportConcordance, int nonSportCount, Map<String, Integer> nonSportConcordance) {
        HashMap<String, Double> probabilityMap = getProbabilityMap(
                text,
                sportConcordance, nonSportConcordance, sportCount, nonSportCount);


        HashMap<String, Double> topMap = getTopMap(probabilityMap);
        System.err.println("topMap = " + topMap);

        Collection<Double> values = topMap.values();
        double product = values.stream().reduce(
                1.0,
                (a, b) -> a * b).doubleValue();
        double productOneMinus = values.stream().reduce(
                1.0,
                (a, b) -> a * (1 - b)).doubleValue();
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

    private HashMap<String, Double> getProbabilityMap(String text, Map<String, Integer> spam, Map<String, Integer> nonSpam,
                                                      int spamCount, int nonSpamCount) {
        TextParser textParser = new TextParser();
        List<String> tokenise = textParser.tokenise(text);
        List<String> distinctWords = tokenise.stream().distinct().collect(Collectors.toList());

        HashMap<String, Double> probs = new HashMap<>();
        for (String word : distinctWords) {
            double value = probSpam(word, nonSpam, spam, nonSpamCount, spamCount);
            if (value >= 0) {
                probs.put(word, value);
            }
        }
        return probs;
    }

    public double probSpam(String word, Map<String,Integer> nonSpam, Map<String,Integer> spam, int numNonSpam, int numSpam) {
        int goodCount = nonSpam.containsKey(word) ? nonSpam.get(word) : 0;
        int badCount = spam.containsKey(word) ? spam.get(word) : 0;
        double g = 2 * goodCount;
        double b = badCount;
        if (g + b >= 5) {
            return Math.max(.01, Math.min(.99,
                    Math.min(1, (b / numSpam))
                            /
                            (Math.min(1, (g / numNonSpam)) + Math.min(1, (b / numSpam)))
            ));
        }
        return -1;
    }

    public static Map<String, Double> sort(Map<String, Double> map) {
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

    public static Comparator<Map.Entry<String,Double>> comparingByDistinctFromHalf() {
        return (Comparator<Map.Entry<String, Double>> & Serializable)
                (c1, c2) -> {
                    double v1 = Math.abs(c1.getValue().doubleValue() - 0.5);
                    double v2 = Math.abs(c2.getValue().doubleValue() - 0.5);
                    int i = (int) (1000000000 * (v2 - v1));
                    return i;
                };
    }

}
