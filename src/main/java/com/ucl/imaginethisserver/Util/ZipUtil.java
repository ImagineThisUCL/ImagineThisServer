package com.ucl.imaginethisserver.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil extends SimpleFileVisitor<Path> {
    private static ZipOutputStream zos;

    private Path directory;

    public ZipUtil(Path directory){
        this.directory = directory;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        try{
            Path targetFile = directory.relativize(file);
            zos.putNextEntry(new ZipEntry(targetFile.toString()));

            byte[] bytes = Files.readAllBytes(file);
            zos.write(bytes, 0, bytes.length);
            zos.closeEntry();
        }
        catch (IOException ex) {
            System.err.println(ex);
        }

        return FileVisitResult.CONTINUE;
    }

    public static void zipFile(String directoryName){
        Path directory = Paths.get(directoryName);
        try{
            String zipFileName = directoryName.concat(".zip");
            zos = new ZipOutputStream(new FileOutputStream(zipFileName));
            Files.walkFileTree(directory, new ZipUtil(directory));
            zos.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }

}
