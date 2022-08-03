package coms.kw.ac.kr.server.service.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Collection;

public class FileIOManager {
    private final String LOCAL_STORAGE_ROOT;

    public FileIOManager(String LOCAL_STORAGE_ROOT) {
        this.LOCAL_STORAGE_ROOT = LOCAL_STORAGE_ROOT;
    }

    public String getLocalStorageRootDirectory() {
        return LOCAL_STORAGE_ROOT;
    }

    public boolean isDirectoryExists(String directory) {
        String fullPath = getLocalDirectory(directory);
        File dir = new File(fullPath);
        return dir.exists();
    }

    public boolean createDirectory(String directory) {
        if (isDirectoryExists(directory))
            return false;

        String fullPath = getLocalDirectory(directory);
        File dir = new File(fullPath);
        dir.mkdirs();

        return true;
    }

    public File createFile(String directory, String filename) {
        if (!isDirectoryExists(directory))
            createDirectory(directory);

        File file = new File(getLocalDirectory(directory), filename);
        try {
            file.createNewFile();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
        return file;
    }

    public void clearDirectory(String directory) {
        File targetDir = new File(getLocalDirectory(directory));
        if (targetDir.exists()) {
            File[] files = targetDir.listFiles();
            for (File f : files) {
                f.delete();
            }
        }
    }

    public void clearDirectory(String directory, Collection<String> exclude) {
        File targetDir = new File(getLocalDirectory(directory));
        if (targetDir.exists()) {
            File[] files = targetDir.listFiles();
            Arrays.stream(files).parallel().forEach(f -> {
                if (exclude.stream().noneMatch(e -> e.equals(f.getName()))) {
                    f.delete();
                }
            });
        }
    }

    public void writeDataToFile(File file, byte[] data) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data);
            outputStream.close();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    public boolean isFileAlreadyExists(String directory, String filename) {
        File file = new File(getLocalDirectory(directory), filename);

        return file.exists();
    }

    private String getLocalDirectory(String directory) {
        if (directory.startsWith("/"))
            directory = directory.substring(1);

        if (!directory.endsWith("/"))
            directory = directory.concat("/");

        return LOCAL_STORAGE_ROOT + directory;
    }
}