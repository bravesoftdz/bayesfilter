package uk.co.blackpepper.bayes.naive;

import java.util.List;

/**
 * Takes a string and breaks it into words. It's a functional interface, so you can create one with a lambda.
 * <p>
 * Created by davidg on 13/04/2017.
 */
@FunctionalInterface
public interface TextParser {
    /**
     * Return the words from the given piece of text.
     *
     * @param text the text
     * @return the list
     */
    public List<String> words(String text);
}
