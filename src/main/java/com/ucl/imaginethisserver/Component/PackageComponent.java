package com.ucl.imaginethisserver.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PackageComponent {

    static final String TEMPLATE_FOLDER_PATH = "src/main/resources/templates/";

    public static String generatePackageJson() throws IOException {
        return new String(Files.readAllBytes(Paths.get(TEMPLATE_FOLDER_PATH,"package.json")));
    }

    public static String generateAppJson() throws IOException {
        return new String(Files.readAllBytes(Paths.get(TEMPLATE_FOLDER_PATH,"app.config.js")));
    }
}
