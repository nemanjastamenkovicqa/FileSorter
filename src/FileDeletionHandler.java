import java.io.File;
import java.util.Scanner;

public class FileDeletionHandler {

    private Scanner scanner;

    public FileDeletionHandler() {
        this.scanner = new Scanner(System.in);
    }

    public void deleteFiles(String directoryPath) {
        File directory = new File(directoryPath);

        // Check if the provided path is a directory
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path. Please provide a valid directory.");
            return;
        }

        // Get a list of files in the directory
        File[] files = directory.listFiles();

        // Delete each file in the directory with user confirmation
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (getUserConfirmation(file)) {
                        deleteFile(file);
                    }
                }
            }
        }
    }

    private boolean getUserConfirmation(File file) {
        System.out.print("Do you want to delete the file " + file.getName() + "? (y/n): ");
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

    public static void main(String[] args) {
        // Example usage:
        String directoryPath = "path/to/your/directory"; // Replace with your desired directory path

        // Create an instance of FileDeletionHandler
        FileDeletionHandler fileDeletionHandler = new FileDeletionHandler();

        // Delete files in the specified directory with user confirmation
        fileDeletionHandler.deleteFiles(directoryPath);
    }
}
