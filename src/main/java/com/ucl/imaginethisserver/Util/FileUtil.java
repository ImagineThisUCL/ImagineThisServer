package com.ucl.imaginethisserver.Util;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUtil {

    private final Logger logger = LoggerFactory.getLogger(CodeGenerator.class);

    @Value("config.outputStorageFolder")
    private String outputStorageFolder;

    @Value("config.templateFilesFolder")
    private String templateFilesFolder;

    /**
     * Helper method for writing files
     * @param filePath - Path to the target file
     * @param text - Text to be written to the target file
     * @throws IOException
     */
    public void writeFile(String filePath, String text) throws IOException {
        logger.info("Writing file {}", filePath);
        File file = new File(filePath);
        FileUtils.writeStringToFile(file, text, "UTF-8");
    };

    public String readFile(String path) throws IOException {
        Path filePath = Paths.get(path).toAbsolutePath();
        logger.debug("Reading file {}", filePath);
        return new String(Files.readAllBytes(filePath));
    };

    /**
     * Helper method for creating directories
     * @param directoryPath
     */
    public void makeDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        directory.mkdir();
    }

    /**
     * Helper method for deleting directories
     * @param directoryPath
     */
    public void deleteDirectory(String directoryPath) throws IOException {
        FileUtils.deleteDirectory(new File(directoryPath));
    }

    /**
     * Helper method for checking whether file exists
     * @param filePath
     */
    public boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && !file.isDirectory();
    }

    /**
     * Helper method for checking whether file exists
     * @param directoryPath
     */
    public boolean directoryExists(String directoryPath) {
        File file = new File(directoryPath);
        return file.exists() && file.isDirectory();
    }

    public void zipDirectory(String directoryPath) {
        ZipUtil.zipDirectory(directoryPath);
    }

}
