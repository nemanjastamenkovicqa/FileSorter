import java.io.File;

public class FileCounter {

    public int countFiles(String directoryPath) {
        File directory = new File(directoryPath);

        // Check if the provided path is a directory
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path. Please provide a valid directory.");
            return 0;
        }

        // Get a list of files in the directory
        File[] files = directory.listFiles();

        // Count and return the number of files
        int fileCount = (files != null) ? files.length : 0;
        System.out.println("Total number of files in the directory: " + fileCount);
        return fileCount;
    }
}