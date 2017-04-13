package uk.co.blackpepper.bayes;

/**
 * A sample source that is constructed from an explicit concordance and count.
 * This is mostly used in the construction of other types of sample source.
 * <p>
 * Created by davidg on 13/04/2017.
 */
public class SimpleSampleSource implements SampleSource {

    private final int sampleCount;
    private final Concordance concordance;

    /**
     * Instantiates a new Simple sample source with zero sample size and no word frequencies.
     */
    public SimpleSampleSource() {
        this(0, new Concordance(""));
    }

    /**
     * Instantiates a new Simple sample source given a source count and map of words ---> word frequencies.
     *
     * @param sampleCount the sample count
     * @param concordance the concordance
     */
    public SimpleSampleSource(int sampleCount, Concordance concordance) {
        this.sampleCount = sampleCount;
        this.concordance = concordance;
    }

    @Override
    public int sampleCount() {
        return sampleCount;
    }

    @Override
    public Concordance concordance() {
        return concordance;
    }
}
