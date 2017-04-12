package uk.co.blackpepper.bayes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by davidg on 12/04/2017.
 */
public class Concordance {

    private final Map<String, Integer> map;

    public Concordance(Collection<String> words) {
        map = new HashMap<>();
        for (String word : words) {
            if (map.containsKey(word)) {
                int count = map.get(word);
                map.put(word, count + 1);
            } else {
                map.put(word, 1);
            }
        }

    }

    public Concordance(String words) {
        this((new TextParser()).tokenise(words));
    }

    private Concordance(Map<String,Integer> map) {
        this.map = map;
    }

    public int count(String word) {
        if (map.containsKey(word)) {
            return map.get(word);
        }
        return 0;
    }

    public Concordance merge(Concordance other) {
        if (other == null) {
            return this;
        }
        Map<String, Integer> thisMap = this.map;
        Map<String, Integer> otherMap = other.map;
        HashMap<String, Integer> resultMap = new HashMap<>(thisMap);
        for (Map.Entry<String,Integer> entry : otherMap.entrySet()) {
            resultMap.merge(entry.getKey(), entry.getValue(), (integer, integer2) -> integer + integer2);
        }
        return new Concordance(resultMap);
    }
}
