package uk.co.blackpepper.bayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by davidg on 12/04/2017.
 */
class DirectorySampleSource implements SampleSource {

    private final File dir;
    private final Concordance concordance;

    public DirectorySampleSource(String dirName) throws IOException {
        this(new File(dirName));
    }

    public DirectorySampleSource(File dir) throws IOException {
        this.dir = dir;
        String words = "";
        for (File file : dir.listFiles()) {
            words += " " + readFileAsString(file);
        }
        concordance = new Concordance(words);
    }

    @Override
    public int sampleCount() {
        return dir.listFiles().length;
    }

    @Override
    public Concordance concordance() {
        return concordance;
    }

    private static String readFileAsString(File filePath)
            throws IOException{
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
}
