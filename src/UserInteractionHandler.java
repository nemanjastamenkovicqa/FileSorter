import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class UserInteractionHandler {

    private Scanner scanner;

    public UserInteractionHandler() {
        this.scanner = new Scanner(System.in);
    }

    public void sortFilesWithUserInteraction(String directoryPath) {
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

        // Recursively sort files in each subdirectory with user interaction
        if (subdirectories != null) {
            for (File subdirectory : subdirectories) {
                sortFilesWithUserInteractionRecursive(subdirectory.getPath());
            }
        }
    }

    private void sortFilesWithUserInteractionRecursive(String directoryPath) {
        File directory = new File(directoryPath);

        // Check if the provided path is a directory
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path. Please provide a valid directory.");
            return;
        }

        File[] files = directory.listFiles();

        // Sort files with user interaction
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Recursively sort files in subdirectories with user interaction
                    sortFilesWithUserInteractionRecursive(file.getPath());
                } else {
                    handleFileWithUserInteraction(file);
                }
            }
        }
    }

    private void handleFileWithUserInteraction(File file) {
        System.out.println("Processing file: " + file.getName());
        System.out.print("Do you want to keep this file? (y/n): ");
        String userChoice = scanner.nextLine().trim().toLowerCase();

        if (!userChoice.equals("y")) {
            // Implement logic to handle the file based on user decision (e.g., delete, move, skip)
            System.out.println("File skipped.");
        }
    }

}
