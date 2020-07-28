package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JavaGrepLambdaImp extends JavaGrepImp {

    public static void main(String[] args) {

        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try {
            javaGrepLambdaImp.process();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Traverse a given directory and return all files
     *
     * @param rootDir input directory
     * @return files under the rootDir
     */
    @Override
    public List<File> listFiles(String rootDir) {
        List<File> fileList = new ArrayList<File>();
        try {
            Files.walk(Paths.get(rootDir)).filter(Files::isRegularFile).forEach(file -> fileList.add(file.toFile()));
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
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
        try {
            Files.lines(inputFile.toPath()).forEach(line -> lines.add(line));
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return lines;
    }
}
