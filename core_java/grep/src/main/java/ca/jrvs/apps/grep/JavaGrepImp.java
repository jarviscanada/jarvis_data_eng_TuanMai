package ca.jrvs.apps.grep;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    /**
     * Top level search
     * @throws IOException
     */
    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<String>();

        for (File file : listFiles(getRootPath())) {
            for (String line : readLines(file)) {
                if (containsPattern(line)) {
                    matchedLines.add(line);
                }
            }
        } try {
            writeToFile(matchedLines);
        } catch (IOException ex) {
            logger.error("Unable to write to file.", ex);
        }
    }

    /**
     * Traverse a given directory and return all files
     * @param rootDir input directory
     * @return files under the rootDir
     */
    @Override
    public List<File> listFiles(String rootDir) {
        File[] tree = new File(rootDir).listFiles();
        List<File> fileList = new ArrayList<File>();
        if (tree != null) {
            for (File file : tree) {
                if (file.isDirectory()) {
                    for (File dirFile : listFiles(file.getAbsolutePath())) {
                        fileList.add(dirFile);
                    }
                }
                else {
                    fileList.add(file);
                }
            }
        }

        return fileList;
    }

    /**
     * Read a file and return all the lines
     *
     * @param inputFile file to be read
     * @return lines
     * @throws IllegalArgumentException if a given input file is not a file
     */
    @Override
    public List<String> readLines(File inputFile) {

        if (!inputFile.isFile()) {
            throw new IllegalArgumentException("Error! The input file: " + inputFile.getName() + " is not a file. Please enter a valid file.");
        }

        List<String> lines = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ex) {
            logger.error("ERROR: Unable to read lines. Please ensure proper input file was added." , ex);
        }
        return lines;
    }

    /**
     * check if a line contains the regex pattern (passed by user)
     * @param line input string
     * @return true if there is a match
     */
    @Override
    public boolean containsPattern(String line) {
        return line.matches(getRegex());
    }

    /**
     * Write lines to a file
     * @param lines matched line
     * @throws IOException if write failed
     */
    @Override
    public void writeToFile(List<String> lines) throws IOException {

        File outputFile = new File(getOutFile());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException ex) {
            logger.error("ERROR: Unable to write to file. Please ensure the output file is correct.", ex);
        }
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        BasicConfigurator.configure(); // Create configuration for log4j

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            javaGrepImp.logger.error("ERROR: Cannot process this task. Please ensure all parameters are correct.", ex);
        }
    }
}
