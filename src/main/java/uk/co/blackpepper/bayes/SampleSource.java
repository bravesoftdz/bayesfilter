package uk.co.blackpepper.bayes;

/**
 * A SampleSource provides examples that can be used with the {@link uk.co.blackpepper.bayes.Categoriser}.
 * <p>
 * The Bayes filter needs to know two things about sample data: how many items there
 * are in the sample (e.g. how many text files) and a {@link uk.co.blackpepper.bayes.Concordance}
 * of word frequencies across the entire sample.
 * <p>
 * Created by davidg on 12/04/2017.
 */
public interface SampleSource {
    /**
     * The number of items in the sample. For example, if it's a directory of example files, how many files are there?
     *
     * @return the int
     */
    int sampleCount();

    /**
     * A breakdown of the word frequencies in the source.
     *
     * @return the concordance
     */
    Concordance concordance();

    /**
     * Construct a new sample source from this one and another.
     *
     * @param other the other
     * @return a new combined sample source
     */
    default SampleSource merge(final SampleSource other) {
        return new SimpleSampleSource(
                sampleCount() + other.sampleCount(),
                concordance().merge(other.concordance()));
    }
}
