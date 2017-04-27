package uk.co.blackpepper.bayes.catcmd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by davidg on 27/04/2017.
 */
public class HTMLRendererTest {

    public static final String START = "<span style='color: rgba(0, 0, 0, 0.10);'>";
    public static final String END = "</span>";
    private HTMLRenderer renderer;

    @Before
    public void setUp() {
        renderer = HTMLRenderer.getInstance();
    }

    @Test
    public void theTextIsAlwaysWrappedInTransparentStyle() {
        //<editor-fold desc="When">
        String render = renderer.render("Some text", new HashMap<>());
        //</editor-fold>

        //<editor-fold desc="Then">
        assert(render.startsWith(START));
        Assert.assertTrue("text: '" + render + "'", render.endsWith(END));
        //</editor-fold>
    }

    @Test
    public void uknownWordsAreLeftAlone() {
        //<editor-fold desc="When">
        String render = renderer.render("Hello World!", new HashMap<>());
        //</editor-fold>

        //<editor-fold desc="Then">
        Assert.assertEquals(START + "Hello World!" + END, render);
        //</editor-fold>
    }

    @Test
    public void wordsWithProbLessThan0point1AreSolidRed() {
        //<editor-fold desc="Given">
        HashMap<String, Double> wordProbMap = new HashMap<>();
        wordProbMap.put("Hello", 0.0);
        //</editor-fold>

        //<editor-fold desc="When">
        String render = renderer.render("Hello World!", wordProbMap);
        //</editor-fold>

        //<editor-fold desc="Then">
        Assert.assertEquals(START + "<span style='color: rgba(255, 0, 0, 1.00);'>Hello</span> World!" + END, render);
        //</editor-fold>
    }

    @Test
    public void wordsWithProbGreaterThan0point9AreSolidBlue() {
        //<editor-fold desc="Given">
        HashMap<String, Double> wordProbMap = new HashMap<>();
        wordProbMap.put("World!", 1.0);
        //</editor-fold>

        //<editor-fold desc="When">
        String render = renderer.render("Hello World!", wordProbMap);
        //</editor-fold>

        //<editor-fold desc="Then">
        Assert.assertEquals(START + "Hello <span style='color: rgba(0, 0, 255, 1.00);'>World!</span>" + END, render);
        //</editor-fold>
    }

    @Test
    public void wordsWithProbOf0point5AreTransparent() {
        //<editor-fold desc="Given">
        HashMap<String, Double> wordProbMap = new HashMap<>();
        wordProbMap.put("World!", 0.5);
        //</editor-fold>

        //<editor-fold desc="When">
        String render = renderer.render("Hello World!", wordProbMap);
        //</editor-fold>

        //<editor-fold desc="Then">
        Assert.assertEquals(START + "Hello <span style='color: rgba(0, 0, 255, 0.10);'>World!</span>" + END, render);
        //</editor-fold>
    }

    @Test
    public void doesNotMixUpWordsWithTheSameBeginning() {
        //<editor-fold desc="Given">
        HashMap<String, Double> wordProbMap = new HashMap<>();
        wordProbMap.put("Hello", 1.0);
        wordProbMap.put("Hell", 0.3);
        //</editor-fold>

        //<editor-fold desc="When">
        String render = renderer.render("Hello Hell", wordProbMap);
        //</editor-fold>

        //<editor-fold desc="Then">
        Assert.assertEquals(START + "<span style='color: rgba(0, 0, 255, 1.00);'>Hello</span> " +
                "<span style='color: rgba(255, 0, 0, 0.46);'>Hell</span>" + END, render);
        //</editor-fold>
    }
}