package uk.co.blackpepper.bayes.naive;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by davidg on 12/04/2017.
 */
public class ConcordanceTest {
    /**
     * Frequency is always zero if there are no words.
     */
    @Test
    public void frequencyIsAlwaysZeroIfThereAreNoWords() {
        //<editor-fold desc="Given">
        Concordance concordance = new Concordance(new ArrayList<String>());
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(0, concordance.count("word"));
        //</editor-fold>
    }

    /**
     * Can count single words.
     */
    @Test
    public void canCountSingleWords() {
        //<editor-fold desc="Given">
        Concordance concordance = new Concordance(Arrays.asList("hello", "world"));
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(1, concordance.count("hello"));
        assertEquals(1, concordance.count("world"));
        //</editor-fold>
    }

    /**
     * Can count multiple words.
     */
    @Test
    public void canCountMultipleWords() {
        //<editor-fold desc="Given">
        Concordance concordance = new Concordance(Arrays.asList("world", "hello", "world"));
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(1, concordance.count("hello"));
        assertEquals(2, concordance.count("world"));
        //</editor-fold>
    }

    /**
     * Can merge disjoint concordances.
     */
    @Test
    public void canMergeDisjointConcordances() {
        Concordance concordance1 = new Concordance(Arrays.asList("hello", "world"));
        Concordance concordance2 = new Concordance(Arrays.asList("goodbye"));

        Concordance merge = concordance1.merge(concordance2);

        assertEquals(1, merge.count("hello"));
        assertEquals(1, merge.count("world"));
        assertEquals(1, merge.count("goodbye"));

    }

    /**
     * Can merge concordances with duplicates.
     */
    @Test
    public void canMergeConcordancesWithDuplicates() {
        //<editor-fold desc="Given">
        Concordance concordance1 = new Concordance(Arrays.asList("hello", "world"));
        Concordance concordance2 = new Concordance(Arrays.asList("goodbye", "world"));
        //</editor-fold>

        //<editor-fold desc="When">
        Concordance merge = concordance1.merge(concordance2);
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(1, merge.count("hello"));
        assertEquals(2, merge.count("world"));
        assertEquals(1, merge.count("goodbye"));
        //</editor-fold>
    }

    /**
     * If you merge a null concordance it does nothing.
     */
    @Test
    public void ifYouMergeANullConcordanceItDoesNothing() {
        //<editor-fold desc="Given">
        Concordance concordance1 = new Concordance(Arrays.asList("hello", "world"));
        Concordance concordance2 = null;
        //</editor-fold>

        //<editor-fold desc="When">
        Concordance merge = concordance1.merge(concordance2);
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(1, merge.count("hello"));
        assertEquals(1, merge.count("world"));
        //</editor-fold>
    }

    /**
     * Can create a concordance from a text string.
     */
    @Test
    public void canCreateAConcordanceFromATextString() {
        //<editor-fold desc="Given">
        Concordance concordance1 = new Concordance("hello world");
        Concordance concordance2 = null;
        //</editor-fold>

        //<editor-fold desc="When">
        Concordance merge = concordance1.merge(concordance2);
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(1, merge.count("hello"));
        assertEquals(1, merge.count("world"));
        //</editor-fold>
    }


    /**
     * A concordance of one word is a single instance.
     */
    @Test
    public void aConcordanceOfOneWordIsASingleInstance() {
        Concordance concordance = new Concordance("one");

        assertEquals(1, concordance.count("one"));
    }

    /**
     * Can create a concordance of multiple words.
     */
    @Test
    public void canCreateAConcordanceOfMultipleWords() {
        Concordance concordance = new Concordance("seven words are better than six words");

        assertEquals(1, concordance.count("seven"));
        assertEquals(2, concordance.count("words"));
        assertEquals(1, concordance.count("are"));
        assertEquals(1, concordance.count("better"));
        assertEquals(1, concordance.count("than"));
        assertEquals(1, concordance.count("six"));
    }
}