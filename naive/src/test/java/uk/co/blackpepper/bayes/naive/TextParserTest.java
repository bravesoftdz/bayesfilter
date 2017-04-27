package uk.co.blackpepper.bayes.naive;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

/**
 * Created by davidg on 13/04/2017.
 */
public class TextParserTest {
    /**
     * Can create a parseable from a lambda.
     */
    @Test
    public void canCreateAParseableFromALambda() {
        TextParser p = t -> asList(t.split(" "));

        assertEquals(2, p.words("hello world").size());
        assertEquals("hello", p.words("hello world").get(0));
        assertEquals("world", p.words("hello world").get(1));
    }
}