package uk.co.blackpepper.bayes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by davidg on 11/04/2017.
 */
public class TextParser {
    public List<String> tokenise(String text) {
        ArrayList<String> result = new ArrayList<String>();
        result.addAll(tokenizeSpaces(text));
        ArrayList<String> result2 = new ArrayList<String>();
        for (String s : result) {
            result2.addAll(tokenizeExc(s));
        }
        return result2;
    }

    private List<String> tokenizeSpaces(String text) {
        String[] split = text.split("\\s");
        ArrayList<String> result = new ArrayList<String>();
        result.addAll(Arrays.asList(split));
        return result;
    }

    private List<String> tokenizeExc(String text) {
        ArrayList<String> result = new ArrayList<String>();
        if (text.indexOf('!') == -1) {
            result.add(text);
            return result;
        }
        String s = "";
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            char next = 0;
            if (i + 1 < text.length()) {
                next = text.charAt(i + 1);
            }
            if (c != '!') {
                s += c;
            } else {
                if (next != '!') {
                    s += c;
                    result.add(s);
                    s = "";
                } else {
                    s += c;
                }
            }
        }
        if (s.length() > 0) {
            result.add(s);
        }
        return result;
    }
}
