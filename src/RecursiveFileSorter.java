import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

public class RecursiveFileSorter {

    private List<String> allowedFileTypes;

    public RecursiveFileSorter(List<String> allowedFileTypes) {
        this.allowedFileTypes = allowedFileTypes;
    }

    public void sortFilesRecursive(String directoryPath) {
        File directory = new File(directoryPath);

        // Check if the provided path is a directory
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path. Please provide a valid directory.");
            return;
        }

        // Create an instance of the existing FileSorter and call its sortFiles method
        FileSorter fileSorter = new FileSorter();
        fileSorter.sortFiles(directoryPath);

        // Get a list of subdirectories
        File[] subdirectories = directory.listFiles(File::isDirectory);

        // Recursively sort files in each subdirectory
        if (subdirectories != null) {
            for (File subdirectory : subdirectories) {
                sortFilesRecursive(subdirectory.getPath());
            }
        }
    }

    public void filterFilesByTypeRecursive(String directoryPath, String destinationDirectory) {
        File directory = new File(directoryPath);
        File destinationDir = new File(destinationDirectory);

        // Check if the provided paths are directories
        if (!directory.isDirectory() || !destinationDir.isDirectory()) {
            System.out.println("Invalid source or destination directory paths. Please provide valid directories.");
            return;
        }

        // Create an instance of the existing FileSorter and call its sortFiles method
        FileSorter fileSorter = new FileSorter();
        fileSorter.sortFiles(directoryPath);

        File[] files = directory.listFiles();

        // Filter and move files based on allowed file types
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Recursively filter files in subdirectories
                    filterFilesByTypeRecursive(file.getPath(), destinationDirectory);
                } else {
                    filterAndMoveFile(file, destinationDir);
                }
            }
        }
    }

    private void filterAndMoveFile(File file, File destinationDir) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1) {
            String fileExtension = fileName.substring(dotIndex + 1).toLowerCase();
            if (allowedFileTypes.contains(fileExtension)) {
                try {
                    Files.move(file.toPath(), destinationDir.toPath().resolve(file.toPath()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        // Specify the directory path you want to sort recursively
        String directoryPath = "path/to/your/directory"; // Replace with your desired directory path

        // Create an instance of RecursiveFileSorter with allowed file types
        RecursiveFileSorter recursiveFileSorter = new RecursiveFileSorter(Arrays.asList("txt", "pdf", "jpg"));

        // Call the sortFilesRecursive method
        recursiveFileSorter.sortFilesRecursive(directoryPath);

        // Example: Filter files by type from one directory to another recursively
        recursiveFileSorter.filterFilesByTypeRecursive(directoryPath, "path/to/destination/directory");
    }
}
