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

    @Test
    public void multipleExclamationPointsAreConstituentCharactersEvenIfNoGapAfter() {
        //<editor-fold desc="Given">
        TextParser textParser = new TextParser();
        //</editor-fold>

        //<editor-fold desc="When">
        List<String> tokens = textParser.tokenise("some!!!!words");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(2, tokens.size());
        assertEquals("some!!!!", tokens.get(0));
        assertEquals("words", tokens.get(1));
        //</editor-fold>
    }

    @Test
    public void canHaveMultipleWordsEndingWithExMarks() {
        //<editor-fold desc="Given">
        TextParser textParser = new TextParser();
        //</editor-fold>

        //<editor-fold desc="When">
        List<String> tokens = textParser.tokenise("some!!!!words!! here");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(3, tokens.size());
        assertEquals("some!!!!", tokens.get(0));
        assertEquals("words!!", tokens.get(1));
        assertEquals("here", tokens.get(2));
        //</editor-fold>
    }

    @Test
    public void periodsCanSplitWords() {
        //<editor-fold desc="Given">
        TextParser textParser = new TextParser();
        //</editor-fold>

        //<editor-fold desc="When">
        List<String> tokens = textParser.tokenise("hello.world");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(2, tokens.size());
        assertEquals("hello", tokens.get(0));
        assertEquals("world", tokens.get(1));
        //</editor-fold>
    }

    @Test
    public void periodsDoNotSplitNumbers() {
        //<editor-fold desc="Given">
        TextParser textParser = new TextParser();
        //</editor-fold>

        //<editor-fold desc="When">
        List<String> tokens = textParser.tokenise("hello.1.2.world.3.4 15.4 and. hello");
        //</editor-fold>

        //<editor-fold desc="Then">
        System.err.println("Results = " + String.join(", ", tokens));
        assertEquals(7, tokens.size());
        assertEquals("hello", tokens.get(0));
        assertEquals("1.2", tokens.get(1));
        assertEquals("world", tokens.get(2));
        assertEquals("3.4", tokens.get(3));
        assertEquals("15.4", tokens.get(4));
        assertEquals("and", tokens.get(5));
        assertEquals("hello", tokens.get(6));
        //</editor-fold>
    }
}