import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
public class FileSorter {



    public static void sortFiles(String directoryPath) {
        File directory = new File(directoryPath);

        // Check if the provided path is a directory
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path. Please provide a valid directory.");
            return;
        }

        // Get a list of all files in the specified directory
        File[] files = directory.listFiles();

        // Create a map to store files based on their extensions
        Map<String, File> fileMap = new HashMap<>();

        // Iterate through the files and categorize them by extension
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                int dotIndex = fileName.lastIndexOf('.');

                // Check if the file has an extension
                if (dotIndex != -1) {
                    String fileExtension = fileName.substring(dotIndex + 1).toLowerCase();

                    fileMap.putIfAbsent(fileExtension, file);
                }
            }
        }

        // Create directories for each file extension if they don't exist
        for (String extension : fileMap.keySet()) {
            File extensionDirectory = new File(directory, extension);
            extensionDirectory.mkdir();
        }

        // Move files to their respective directories
        for (Map.Entry<String, File> entry : fileMap.entrySet()) {
            String extension = entry.getKey();
            File file = entry.getValue();

            File destinationDirectory = new File(directory, extension);
            File destinationFile = new File(destinationDirectory, file.getName());

            try {
                Files.move(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Files sorted successfully!");
    }
// command for storing data
    public static void main(String[] args) {
        // Specify the directory path you want to sort
        String directoryPath = "path/to/your/directory"; // Replace with your desired directory path

        // Call the sortFiles method
        sortFiles(directoryPath);

        // Create an instance of FileSorter or DuplicateFileHandler and perform necessary operations
        FileSorter fileSorter = new FileSorter();
        fileSorter.sortFiles(directoryPath);

        // Create an instance of UserInteractionHandler
        UserInteractionHandler userInteractionHandler = new UserInteractionHandler();

        // Call the sortFilesWithUserInteraction method
        userInteractionHandler.sortFilesWithUserInteraction(directoryPath);

        // Optionally, add the following lines to perform recursive sorting
        RecursiveFileSorter recursiveFileSorter = new RecursiveFileSorter(Arrays.asList("txt", "pdf", "jpg"));

        recursiveFileSorter.sortFilesRecursive(directoryPath);
    }
}