package uk.co.blackpepper.bayes;

import com.sun.deploy.util.StringUtils;

import java.util.Arrays;

import static com.sun.deploy.util.StringUtils.join;
import static java.util.Arrays.asList;

/**
 * Created by davidg on 13/04/2017.
 */
public class StringSampleSource implements Sampleable {

    private final int samples;
    private final Concordance concordance;

    public StringSampleSource(String... sources) {
        samples = sources.length;
        concordance = new Concordance(join(asList(sources), " "));
    }

    @Override
    public int sampleCount() {
        return samples;
    }

    @Override
    public Concordance concordance() {
        return concordance;
    }
}
