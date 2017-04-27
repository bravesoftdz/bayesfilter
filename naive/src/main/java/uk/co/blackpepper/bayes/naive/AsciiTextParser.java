package uk.co.blackpepper.bayes.naive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by davidg on 11/04/2017.
 */
public class AsciiTextParser implements TextParser {
    @Override
    public List<String> words(String text) {
        ArrayList<String> result = new ArrayList<>();
        if (text == null) {
            return result;
        }
        String t = text.replaceAll("[\\|\\-:/]", " ");
        result.addAll(tokenizeSpaces(t));
        ArrayList<String> result2 = new ArrayList<>();
        for (String s : result) {
            result2.addAll(tokenizePeriods(s));
        }
        ArrayList<String> result3 = new ArrayList<>();
        for (String s : result2) {
            result3.addAll(tokenizeExc(s));
        }
        return result3.stream().filter(i -> i.length() > 0).collect(Collectors.toList());
    }

    private List<String> tokenizeSpaces(String text) {
        String[] split = text.split("\\s");
        List<String> result = new ArrayList<>();
        result.addAll(Arrays.asList(split));
        return result;
    }

    private List<String> tokenizePeriods(String text) {
        List<String> result = new ArrayList<>();
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
        ArrayList<String> result = new ArrayList<>();
        if (text.indexOf('!') == -1) {
            result.add(text);
            return result;
        }
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            char next = 0;
            if (i + 1 < text.length()) {
                next = text.charAt(i + 1);
            }
            if (c != '!') {
                s.append(c);
            } else {
                if (next != '!') {
                    s.append(c);
                    result.add(s.toString());
                    s = new StringBuilder();
                } else {
                    s.append(c);
                }
            }
        }
        if (s.length() > 0) {
            result.add(s.toString());
        }
        return result;
    }
}
