package uk.co.blackpepper.bayes;

/**
 * A SampleSource provides examples that can be used with the {@link uk.co.blackpepper.bayes.Categoriser}.
 *
 * The Bayes filter needs to know two things about sample data: how many items there
 * are in the sample (e.g. how many text files) and a {@link uk.co.blackpepper.bayes.Concordance}
 * of word frequencies across the entire sample.
 *
 * Created by davidg on 12/04/2017.
 */
public interface SampleSource {
    int sampleCount();

    Concordance concordance();

    default SampleSource merge(final SampleSource other) {
        return new SimpleSampleSource(
                sampleCount() + other.sampleCount(),
                concordance().merge(other.concordance()));
    }
}
