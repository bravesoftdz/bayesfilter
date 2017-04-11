package uk.co.blackpepper.bayes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by davidg on 11/04/2017.
 */
public class TextParser {
    public List<String> tokenise(String text) {
        ArrayList<String> result = new ArrayList<String>();
        result.addAll(tokenizeSpaces(text));
        ArrayList<String> result2 = new ArrayList<String>();
        for (String s : result) {
            result2.addAll(tokenizePeriods(s));
        }
        ArrayList<String> result3 = new ArrayList<String>();
        for (String s : result2) {
            result3.addAll(tokenizeExc(s));
        }
        return result3.stream().filter(i -> i.length() > 0).collect(Collectors.toList());
    }

    private List<String> tokenizeSpaces(String text) {
        String[] split = text.split("\\s");
        ArrayList<String> result = new ArrayList<String>();
        result.addAll(Arrays.asList(split));
        return result;
    }

    private List<String> tokenizePeriods(String text) {
        ArrayList<String> result = new ArrayList<String>();
        Pattern p = Pattern.compile("([0-9]+\\.[0-9]+)+");
        Matcher matcher = p.matcher(text);
        int i = 0;
        while(matcher.find()) {
            String group = matcher.group();
            String before = text.substring(i, matcher.start());
            if (before.endsWith(".")) {
                before = before.substring(0, before.length() - 1);
            }
            if (before.startsWith(".")) {
                before = before.substring(1);
            }
            i = matcher.end();
            result.add(before);
            result.add(group);
        }
        if (i == 0) {
            result.addAll(Arrays.asList(text.split("\\.")));
        }
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
