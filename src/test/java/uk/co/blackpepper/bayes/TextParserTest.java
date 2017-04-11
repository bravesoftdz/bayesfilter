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
        //<editor-fold desc="Given">
        TextParser textParser = new TextParser();
        //</editor-fold>

        //<editor-fold desc="When">
        List<String> tokens = textParser.tokenise("word");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(1, tokens.size());
        assertEquals("word", tokens.get(0));
        //</editor-fold>
    }
    @Test
    public void ifTextContainsTwoWordsThenReturnAListOfTwoWords() {
        //<editor-fold desc="Given">
        TextParser textParser = new TextParser();
        //</editor-fold>

        //<editor-fold desc="When">
        List<String> tokens = textParser.tokenise("some words");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(2, tokens.size());
        assertEquals("some", tokens.get(0));
        assertEquals("words", tokens.get(1));
        //</editor-fold>
    }

    @Test
    public void canSplitWordsWithAnyWhitespaceCharacters() {
        //<editor-fold desc="Given">
        TextParser textParser = new TextParser();
        //</editor-fold>

        //<editor-fold desc="When">
        List<String> tokens = textParser.tokenise("some words\tare\nhere");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(4, tokens.size());
        assertEquals("some", tokens.get(0));
        assertEquals("words", tokens.get(1));
        assertEquals("are", tokens.get(2));
        assertEquals("here", tokens.get(3));
        //</editor-fold>
    }

    @Test
    public void exclamationPointsAreConstituentCharacters() {
        //<editor-fold desc="Given">
        TextParser textParser = new TextParser();
        //</editor-fold>

        //<editor-fold desc="When">
        List<String> tokens = textParser.tokenise("some! words");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(2, tokens.size());
        assertEquals("some!", tokens.get(0));
        assertEquals("words", tokens.get(1));
        //</editor-fold>
    }

    @Test
    public void exclamationPointsAreConstituentCharactersEvenIfNoGapAfter() {
        //<editor-fold desc="Given">
        TextParser textParser = new TextParser();
        //</editor-fold>

        //<editor-fold desc="When">
        List<String> tokens = textParser.tokenise("some!words");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(2, tokens.size());
        assertEquals("some!", tokens.get(0));
        assertEquals("words", tokens.get(1));
        //</editor-fold>
    }
}