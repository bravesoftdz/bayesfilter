package uk.co.blackpepper.bayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * A {@link uk.co.blackpepper.bayes.SampleSource} created from a directory of files.
 * It will recursively include files from sub-directories.
 * <p>
 * Created by davidg on 12/04/2017.
 */
class DirectorySampleSource implements SampleSource {

    private final Concordance concordance;
    private final int count;

    /**
     * A sample source based on a directory specified by name. It will recurse sub-directories.
     *
     * @param dirName the name of the directory
     * @throws IOException if the directory is not accessible
     */
    public DirectorySampleSource(String dirName) throws IOException {
        this(new File(dirName));
    }

    /**
     * A sample source based on a directory. It will recurse subdirectories.
     *
     * @param dir the dir
     * @throws IOException the io exception
     */
    public DirectorySampleSource(File dir) throws IOException {
        SampleSource sampleSource = new SimpleSampleSource();

        for (File file : dir.listFiles()) {
            sampleSource = sampleSource.merge(file.isDirectory()
                    ? new DirectorySampleSource(file)
                    : new StringSampleSource(readFileAsString(file)));
        }

        this.concordance = sampleSource.concordance();
        this.count = sampleSource.sampleCount();
    }

    @Override
    public int sampleCount() {
        return count;
    }

    @Override
    public Concordance concordance() {
        return concordance;
    }

    private static String readFileAsString(File filePath)
            throws IOException{
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
}
