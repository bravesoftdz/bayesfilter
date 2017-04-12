package uk.co.blackpepper.bayes;

/**
 * Created by davidg on 12/04/2017.
 */
public interface SampleSource {
    int sampleCount();

    Concordance concordance();
}
