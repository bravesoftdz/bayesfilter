package uk.co.blackpepper.bayes;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

/**
 * Created by davidg on 13/04/2017.
 */
public class TokenizerTest {
    @Test
    public void canCreateAParseableFromALambda() {
        Tokenizer p = t -> asList(t.split(" "));

        assertEquals(2, p.tokenise("hello world").size());
        assertEquals("hello", p.tokenise("hello world").get(0));
        assertEquals("world", p.tokenise("hello world").get(1));
    }
}