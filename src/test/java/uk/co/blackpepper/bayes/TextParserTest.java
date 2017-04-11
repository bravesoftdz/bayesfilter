package uk.co.blackpepper.bayes;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by davidg on 11/04/2017.
 */
public class TextParserTest {
    @Test
    public void ifTextContainsOneWordThenReturnAListOfOneWord() {
        TextParser textParser = new TextParser();
        List<String> tokens = textParser.tokenise("word");
        assertEquals(1, tokens.size());
        assertEquals("word", tokens.get(0));
    }
    @Test
    public void ifTextContainsTwoWordsThenReturnAListOfTwoWords() {
        TextParser textParser = new TextParser();
        List<String> tokens = textParser.tokenise("some words");
        assertEquals(2, tokens.size());
    }
}