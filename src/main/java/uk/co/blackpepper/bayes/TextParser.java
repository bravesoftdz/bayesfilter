package uk.co.blackpepper.bayes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by davidg on 11/04/2017.
 */
public class TextParser {
    public List<String> tokenise(String text) {
        String[] split = text.split("\\s");
        List<String> splitList = Arrays.asList(split);
        ArrayList<String> result = new ArrayList<String>();
        for (String token : splitList) {
            if (token.contains("!") && !token.endsWith("!")) {
                int pos = token.indexOf('!');
                String part0 = token.substring(0, pos + 1);
                String part1 = token.substring(pos + 1);
                result.add(part0);
                result.add(part1);
            } else {
                result.add(token);
            }
        }

        return result;
    }
}
