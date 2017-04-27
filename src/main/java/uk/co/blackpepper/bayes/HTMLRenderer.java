package uk.co.blackpepper.bayes;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Creates a highlighted version of an analysed result
 * <p>
 * Created by davidg on 27/04/2017.
 */
public class HTMLRenderer {
    private static final HTMLRenderer instance = new HTMLRenderer();
    public static final String DIV_WITH_STYLE = "<span style='color: rgba(%s, %.2f);'>%s</span>";

    private HTMLRenderer() {
        // Do nothing
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HTMLRenderer getInstance() {
        return instance;
    }

    /**
     * Render a piece of text as highlighted HTML.
     *
     * Accepts a map of probability for each of the words. The words that appear in the map with very low probabilities
     * will appear as more opaque red, those with high probabilities will be more opaque blue, and those with
     * probabilities close to 0.5 will transparent.
     *
     * @param text    the piece of text to render as a HTML fragment
     * @param wordProbMap the prob map for words in the text
     * @return the string
     */
    public String render(String text, Map<String,Double> wordProbMap) {
        String result = "";

        AsciiTextParser parser = new AsciiTextParser();
        List<String> words = parser.words(text);

        for (String word : words) {
            if (wordProbMap.containsKey(word)) {
                double prob = wordProbMap.get(word);
                String colour = (prob < 0.5) ? "255, 0, 0" : "0, 0, 255";
                double alpha = Math.abs(prob - 0.5) * 1.8 + 0.1;
                result += " " + formatDiv(word, colour, alpha);
            } else {
                result += " " + word;
            }
        }

        result = result.trim();

        return formatDiv(result, "0, 0, 0", 0.1);
    }

    private String formatDiv(String word, String colour, double alpha) {
        return String.format(Locale.ENGLISH,
                        DIV_WITH_STYLE, colour, alpha, word);
    }
}
