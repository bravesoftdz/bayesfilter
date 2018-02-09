package uk.co.blackpepper.bayes.catcmd;

import uk.co.blackpepper.bayes.naive.Categoriser;
import uk.co.blackpepper.bayes.naive.DirectorySampleSource;

import java.io.*;
import java.nio.file.Files;

/**
 * Created by davidg on 27/04/2017.
 */
public class Command {
    public static void main(String[] args) {
        try {
            String dataDirName = (args.length < 1) ? "./catcmd/data/" : args[0];

            Categoriser categoriser = new Categoriser()
                    .category("business", new DirectorySampleSource(dataDirName + "samples/business"))
                    .category("technology", new DirectorySampleSource(dataDirName + "samples/technology"))
                    .category("sport", new DirectorySampleSource(dataDirName + "samples/sport"))
                    .topWordsConsidered(200)
                    ;

            String analysisDirName = dataDirName + "analysis/";
            String outputDirName = dataDirName + "output/";
            File outputDir = new File(outputDirName);
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }
            File analysisDir = new File(analysisDirName);
            for (File file : analysisDir.listFiles()) {
                String name = file.getName();
                if (name.endsWith(".txt")) {
                    String analysisText = readFileAsString(file);
                    String category = categoriser.getProbableCategoryFor(analysisText);
                    File destDir = new File(outputDirName, category);
                    if (!destDir.isDirectory()) {
                        Files.createDirectory(destDir.toPath());
                    }
                    String html = HTMLRenderer.getInstance().render(analysisText, categoriser.interestingWords(analysisText, category));
                    FileWriter writer = new FileWriter(new File(destDir, name + ".html"));
                    writer.write(html);
                    writer.close();
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFileAsString(File filePath)
            throws java.io.IOException{
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
