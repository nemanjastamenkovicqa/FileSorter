import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class DuplicateFileHandler {

    private Map<String, List<File>> fileHashes;

    public DuplicateFileHandler() {
        this.fileHashes = new HashMap<>();
    }

    public void handleDuplicates(String directoryPath) {
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

        // Recursively handle duplicates in each subdirectory
        if (subdirectories != null) {
            for (File subdirectory : subdirectories) {
                handleDuplicatesRecursive(subdirectory.getPath());
            }
        }
    }

    private void handleDuplicatesRecursive(String directoryPath) {
        File directory = new File(directoryPath);

        // Check if the provided path is a directory
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path. Please provide a valid directory.");
            return;
        }

        File[] files = directory.listFiles();

        // Handle duplicates based on file content hashes
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Recursively handle duplicates in subdirectories
                    handleDuplicatesRecursive(file.getPath());
                } else {
                    handleDuplicateFile(file);
                }
            }
        }
    }

    private void handleDuplicateFile(File file) {
        try {
            String fileContentHash = generateFileContentHash(file);

            if (!fileHashes.containsKey(fileContentHash)) {
                fileHashes.put(fileContentHash, new ArrayList<>());
            }

            List<File> filesWithSameHash = fileHashes.get(fileContentHash);
            filesWithSameHash.add(file);

            if (filesWithSameHash.size() > 1) {
                // Handle duplicate files (e.g., rename, move to a specific directory)
                handleDuplicate(filesWithSameHash);
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private String generateFileContentHash(File file) throws IOException, NoSuchAlgorithmException {
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(fileBytes);

        StringBuilder hashStringBuilder = new StringBuilder();
        for (byte hashByte : hashBytes) {
            hashStringBuilder.append(String.format("%02x", hashByte));
        }

        return hashStringBuilder.toString();
    }

    private void handleDuplicate(List<File> duplicateFiles) {
        // Implement the logic to handle duplicate files (e.g., rename, move)
        // For demonstration purposes, let's print a message
        System.out.println("Handling duplicate files:");
        for (File duplicateFile : duplicateFiles) {
            System.out.println("- " + duplicateFile.getAbsolutePath());
        }
        System.out.println();
    }


}
