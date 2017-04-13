package uk.co.blackpepper.bayes;

import static java.lang.String.join;
import static java.util.Arrays.asList;

/**
 * A {@link uk.co.blackpepper.bayes.SampleSource} created from a list of strings.
 *
 * Created by davidg on 13/04/2017.
 */
public class StringSampleSource implements SampleSource {

    private final int samples;
    private final Concordance concordance;

    public StringSampleSource(String... sources) {
        samples = sources.length;
        concordance = new Concordance(join(" ", asList(sources)));
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
