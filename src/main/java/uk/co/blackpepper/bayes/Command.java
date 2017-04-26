package uk.co.blackpepper.bayes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Created by davidg on 15/04/2017.
 */
public class Command {
    public static void main(String[] args) {
        try {
            String dataDirName = "./";

            Categoriser categoriser = new Categoriser()
                    .category("interesting", new DirectorySampleSource(dataDirName + "samples/interesting"))
                    .category("uninteresting", new DirectorySampleSource(dataDirName + "./samples/uninteresting"))
                    .topWordsConsidered(200)
                    ;

            String analysisDirName = dataDirName + "analysis/";
            String outputDirName = dataDirName + "output/";
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
                    Files.copy(file.toPath(), new File(destDir, name).toPath(), StandardCopyOption.REPLACE_EXISTING);
                    String title = name.replace(".txt", ".title");
                    Files.copy((new File(analysisDir, title)).toPath(), new File(destDir, title).toPath(), StandardCopyOption.REPLACE_EXISTING);
                    String url = name.replace(".txt", ".url");
                    Files.copy((new File(analysisDir, url)).toPath(), new File(destDir, url).toPath(), StandardCopyOption.REPLACE_EXISTING);
                    String words = name.replace(".txt", ".words");
                    File wordsFile = new File(destDir, words);
                    FileWriter fileWriter = new FileWriter(wordsFile);
                    fileWriter.write("" + categoriser.interestingWords(analysisText, category));
                    fileWriter.close();
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private String getLikelyCategory(String text) throws IOException {
        return new Categoriser()
                .category("sport", new DirectorySampleSource("./samples/sport"))
                .category("android", new DirectorySampleSource("./samples/android"))
                .category("science", new DirectorySampleSource("./samples/science"))
                .getProbableCategoryFor(text);
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
