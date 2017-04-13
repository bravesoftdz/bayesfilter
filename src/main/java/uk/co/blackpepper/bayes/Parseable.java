package uk.co.blackpepper.bayes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by davidg on 13/04/2017.
 */
public interface Parseable {
    List<String> tokenise(String text);
}
