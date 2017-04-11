package uk.co.blackpepper.bayes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by davidg on 11/04/2017.
 */
public class TextParser {
    public List<String> tokenise(String text) {
        String[] split = text.split(" ");
        ArrayList<String> result = new ArrayList<String>();
        result.addAll(Arrays.asList(split));
        return result;
    }
}
