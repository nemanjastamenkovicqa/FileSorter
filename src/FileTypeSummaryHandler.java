import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileTypeSummaryHandler {

    private Map<String, Integer> fileTypeCount;

    public FileTypeSummaryHandler() {
        this.fileTypeCount = new HashMap<>();
    }

    public void generateFileTypeSummary(String directoryPath) {
        File directory = new File(directoryPath);

        // Check if the provided path is a directory
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path. Please provide a valid directory.");
            return;
        }

        // Get a list of files in the directory
        File[] files = directory.listFiles();

        // Generate file type summary
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileType = getFileType(file);
                    fileTypeCount.put(fileType, fileTypeCount.getOrDefault(fileType, 0) + 1);
                }
            }

            // Display the file type summary
            System.out.println("File Type Summary:");
            for (Map.Entry<String, Integer> entry : fileTypeCount.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " files");
            }
        }
    }

    private String getFileType(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "Unknown" : fileName.substring(dotIndex + 1).toLowerCase();
    }
}