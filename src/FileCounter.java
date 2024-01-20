import java.io.File;

public class FileCounter {

    public int countFiles(String directoryPath) {
        File directory = new File(directoryPath);

        // Check if the provided path is a directory
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path. Please provide a valid directory.");
            return 0;
        }

        // Count and return the number of all files, including subdirectories
        int totalFileCount = countFilesRecursive(directory);
        System.out.println("Total number of files in the directory and its subdirectories: " + totalFileCount);
        return totalFileCount;
    }

    public int countFilesByType(String directoryPath, String fileType) {
        File directory = new File(directoryPath);

        // Check if the provided path is a directory
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path. Please provide a valid directory.");
            return 0;
        }

        // Count and return the number of files with a specific type, including subdirectories
        int fileTypeCount = countFilesByTypeRecursive(directory, fileType);
        System.out.println("Total number of " + fileType + " files in the directory and its subdirectories: " + fileTypeCount);
        return fileTypeCount;
    }

    private int countFilesRecursive(File directory) {
        File[] files = directory.listFiles();

        // Base case: directory is empty or null
        if (files == null) {
            return 0;
        }

        int count = 0;

        for (File file : files) {
            if (file.isFile()) {
                count++;
            } else if (file.isDirectory()) {
                // Recursive call for subdirectories
                count += countFilesRecursive(file);
            }
        }

        return count;
    }

    private int countFilesByTypeRecursive(File directory, String fileType) {
        File[] files = directory.listFiles();

        // Base case: directory is empty or null
        if (files == null) {
            return 0;
        }

        int count = 0;

        for (File file : files) {
            if (file.isFile() && getFileType(file).equalsIgnoreCase(fileType)) {
                count++;
            } else if (file.isDirectory()) {
                // Recursive call for subdirectories
                count += countFilesByTypeRecursive(file, fileType);
            }
        }

        return count;
    }

    private String getFileType(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "Unknown" : fileName.substring(dotIndex + 1).toLowerCase();
    }
}