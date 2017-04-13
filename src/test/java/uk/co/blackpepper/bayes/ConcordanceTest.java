package uk.co.blackpepper.bayes;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by davidg on 12/04/2017.
 */
public class ConcordanceTest {
    @Test
    public void frequencyIsAlwaysZeroIfThereAreNoWords() {
        //<editor-fold desc="Given">
        Concordance concordance = new Concordance(new ArrayList<String>());
        //</editor-fold>

        //<editor-fold desc="Then">
        assertEquals(0, concordance.count("word"));
        //</editor-fold>
    }

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

    @Test
    public void canMergeDisjointConcordances() {
        Concordance concordance1 = new Concordance(Arrays.asList("hello", "world"));
        Concordance concordance2 = new Concordance(Arrays.asList("goodbye"));

        Concordance merge = concordance1.merge(concordance2);

        assertEquals(1, merge.count("hello"));
        assertEquals(1, merge.count("world"));
        assertEquals(1, merge.count("goodbye"));

    }

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


    @Test
    public void aConcordanceOfOneWordIsASingleInstance() {
        Concordance concordance = new Concordance("one");

        assertEquals(1, concordance.count("one"));
    }

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