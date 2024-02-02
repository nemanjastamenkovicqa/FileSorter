import java.io.File;
import java.util.Scanner;

public class FileDeletionHandler {

    private Scanner scanner;

    public FileDeletionHandler() {
        this.scanner = new Scanner(System.in);
    }

    public void deleteFilesAndEmptyDirectories(String directoryPath) {
        File directory = new File(directoryPath);

        // Check if the provided path is a directory
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path. Please provide a valid directory.");
            return;
        }

        // Delete files and empty directories in the directory with user confirmation
        deleteFilesAndEmptyDirectoriesRecursive(directory);
    }

    private void deleteFilesAndEmptyDirectoriesRecursive(File directory) {
        File[] files = directory.listFiles();

        // Base case: directory is empty or null
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                // Recursive call for subdirectories
                deleteFilesAndEmptyDirectoriesRecursive(file);
            } else {
                // Delete files with user confirmation
                if (getUserConfirmation(file)) {
                    deleteFile(file);
                }
            }
        }

        // Delete empty directory with user confirmation
        if (isEmptyDirectory(directory) && getUserConfirmation(directory)) {
            deleteDirectory(directory);
        }
    }

    private boolean getUserConfirmation(File file) {
        System.out.print("Do you want to delete the file/directory " + file.getName() + "? (y/n): ");
        String userChoice = scanner.nextLine().trim().toLowerCase();
        return userChoice.equals("y");
    }

    private void deleteFile(File file) {
        if (file.delete()) {
            System.out.println("Deleted: " + file.getAbsolutePath());
        } else {
            System.out.println("Failed to delete: " + file.getAbsolutePath());
        }
    }

    private boolean isEmptyDirectory(File directory) {
        return directory.listFiles() == null || directory.listFiles().length == 0;
    }

    private void deleteDirectory(File directory) {
        if (directory.delete()) {
            System.out.println("Deleted directory: " + directory.getAbsolutePath());
        } else {
            System.out.println("Failed to delete directory: " + directory.getAbsolutePath());
        }
    }
}
