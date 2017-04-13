package uk.co.blackpepper.bayes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A concordance counts words, e.g.
 * <p>
 * <code>
 * Concordance c = new Concordance("hello world world");
 * System.out.println(c.count("world")); // Displays "2"
 * </code>
 * <p>
 * Concordances can be merged together:
 * <p>
 * <code>
 * // Equivalent to (new Concordance("hello goodbye world world")
 * (new Concordance("hello world")).merge(new Concordance("goodbye world"));
 * </code>
 * <p>
 * Created by davidg on 12/04/2017.
 */
public class Concordance {

    private final Map<String, Integer> map;

    /**
     * Instantiates a new Concordance based on a collection of words.
     *
     * @param words the words
     */
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

    /**
     * Instantiates a new Concordance from a string. It will split the string using an {@link AsciiTextParser}
     *
     * @param words the words
     */
    public Concordance(String words) {
        this(words, new AsciiTextParser());
    }

    /**
     * Instantiates a new Concordance from a string using a specific parser.
     *
     * @param words      the words
     * @param textParser the text parser
     */
    public Concordance(String words, TextParser textParser) {
        this(textParser.words(words));
    }

    private Concordance(Map<String,Integer> map) {
        this.map = map;
    }

    /**
     * The number of times this word appears in the sample source
     *
     * @param word the word
     * @return the int
     */
    public int count(String word) {
        if (map.containsKey(word)) {
            return map.get(word);
        }
        return 0;
    }

    /**
     * Create a new concordance by merging this one with another.
     *
     * @param other the other
     * @return the concordance
     */
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
