import java.io.File;

public class FileDeletionHandler {

    public void deleteFiles(String directoryPath) {
        File directory = new File(directoryPath);

        // Check if the provided path is a directory
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path. Please provide a valid directory.");
            return;
        }

        // Get a list of files in the directory
        File[] files = directory.listFiles();

        // Delete each file in the directory
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    deleteFile(file);
                }
            }
        }
    }

    private void deleteFile(File file) {
        if (file.delete()) {
            System.out.println("Deleted: " + file.getAbsolutePath());
        } else {
            System.out.println("Failed to delete: " + file.getAbsolutePath());
        }
    }
}