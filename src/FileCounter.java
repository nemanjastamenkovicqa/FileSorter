import java.io.File;

public class FileCounter {

    public int countFiles(String directoryPath) {
        File directory = new File(directoryPath);

        // Check if the provided path is a directory
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path. Please provide a valid directory.");
            return 0;
        }

        // Count and return the number of files, including subdirectories
        int fileCount = countFilesRecursive(directory);
        System.out.println("Total number of files in the directory and its subdirectories: " + fileCount);
        return fileCount;
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
}