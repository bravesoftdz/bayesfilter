package uk.co.blackpepper.bayes;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by davidg on 11/04/2017.
 */
public class AsciiTextParserTest {

    private final TextParser textParser = new AsciiTextParser();

    /**
     * If text contains one word then return a list of one word.
     */
    @Test
    public void ifTextContainsOneWordThenReturnAListOfOneWord() {
        //<editor-fold desc="When">
        List<String> tokens = textParser.words("word");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(1, tokens.size());
        assertEquals("word", tokens.get(0));
        //</editor-fold>
    }

    /**
     * Ignores weird chars.
     */
    @Test
    public void ignoresWeirdChars() {
        //<editor-fold desc="When">
        List<String> tokens = textParser.words("| - : /");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(0, tokens.size());
        //</editor-fold>
    }

    /**
     * Is text is null then return an empty list.
     */
    @Test
    public void isTextIsNullThenReturnAnEmptyList() {
        //<editor-fold desc="When">
        List<String> tokens = textParser.words(null);
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(0, tokens.size());
        //</editor-fold>
    }

    /**
     * Is text is blank then return an empty list.
     */
    @Test
    public void isTextIsBlankThenReturnAnEmptyList() {
        //<editor-fold desc="When">
        List<String> tokens = textParser.words("     \n\n\n\n\n \t   \r\n   ");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(0, tokens.size());
        //</editor-fold>
    }

    /**
     * If text contains two words then return a list of two words.
     */
    @Test
    public void ifTextContainsTwoWordsThenReturnAListOfTwoWords() {
        //<editor-fold desc="When">
        List<String> tokens = textParser.words("some words");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(2, tokens.size());
        assertEquals("some", tokens.get(0));
        assertEquals("words", tokens.get(1));
        //</editor-fold>
    }

    /**
     * Can split words with any whitespace characters.
     */
    @Test
    public void canSplitWordsWithAnyWhitespaceCharacters() {
        //<editor-fold desc="When">
        List<String> tokens = textParser.words("some words\tare\nhere");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(4, tokens.size());
        assertEquals("some", tokens.get(0));
        assertEquals("words", tokens.get(1));
        assertEquals("are", tokens.get(2));
        assertEquals("here", tokens.get(3));
        //</editor-fold>
    }

    /**
     * Exclamation points are constituent characters.
     */
    @Test
    public void exclamationPointsAreConstituentCharacters() {
        //<editor-fold desc="When">
        List<String> tokens = textParser.words("some! words");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(2, tokens.size());
        assertEquals("some!", tokens.get(0));
        assertEquals("words", tokens.get(1));
        //</editor-fold>
    }

    /**
     * Exclamation points are constituent characters even if no gap after.
     */
    @Test
    public void exclamationPointsAreConstituentCharactersEvenIfNoGapAfter() {
        //<editor-fold desc="When">
        List<String> tokens = textParser.words("some!words");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(2, tokens.size());
        assertEquals("some!", tokens.get(0));
        assertEquals("words", tokens.get(1));
        //</editor-fold>
    }

    /**
     * Multiple exclamation points are constituent characters even if no gap after.
     */
    @Test
    public void multipleExclamationPointsAreConstituentCharactersEvenIfNoGapAfter() {
        //<editor-fold desc="When">
        List<String> tokens = textParser.words("some!!!!words");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(2, tokens.size());
        assertEquals("some!!!!", tokens.get(0));
        assertEquals("words", tokens.get(1));
        //</editor-fold>
    }

    /**
     * Can have multiple words ending with ex marks.
     */
    @Test
    public void canHaveMultipleWordsEndingWithExMarks() {
        //<editor-fold desc="When">
        List<String> tokens = textParser.words("some!!!!words!! here");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(3, tokens.size());
        assertEquals("some!!!!", tokens.get(0));
        assertEquals("words!!", tokens.get(1));
        assertEquals("here", tokens.get(2));
        //</editor-fold>
    }

    /**
     * Periods can split words.
     */
    @Test
    public void periodsCanSplitWords() {
        //<editor-fold desc="When">
        List<String> tokens = textParser.words("hello.world");
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(2, tokens.size());
        assertEquals("hello", tokens.get(0));
        assertEquals("world", tokens.get(1));
        //</editor-fold>
    }

    /**
     * Periods do not split numbers.
     */
    @Test
    public void periodsDoNotSplitNumbers() {
        //<editor-fold desc="When">
        List<String> tokens = textParser.words("hello.1.2.world.3.4 15.4 and. hello");
        //</editor-fold>

        //<editor-fold desc="Then">
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

