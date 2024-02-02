import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class FileTypeSummaryHandler {

    private Map<String, Integer> fileTypeCount;

    public FileTypeSummaryHandler() {
        // Use TreeMap for automatic sorting by keys (file types)
        this.fileTypeCount = new TreeMap<>();
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

            // Display the file type summary in alphabetical order
            System.out.println("File Type Summary:");
            for (Map.Entry<String, Integer> entry : fileTypeCount.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " files");
            }

            // Display the most common file type
            System.out.println("Most Common File Type: " + getMostCommonFileType());
        }
    }

    private String getFileType(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "Unknown" : fileName.substring(dotIndex + 1).toLowerCase();
    }

    private String getMostCommonFileType() {
        String mostCommonFileType = "Unknown";
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : fileTypeCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommonFileType = entry.getKey();
            }
        }

        return mostCommonFileType;
    }

    public static void main(String[] args) {
        // Example usage:
        String directoryPath = "path/to/your/directory"; // Replace with your desired directory path

        // Create an instance of FileTypeSummaryHandler
        FileTypeSummaryHandler fileTypeSummaryHandler = new FileTypeSummaryHandler();

        // Generate and display the file type summary
        fileTypeSummaryHandler.generateFileTypeSummary(directoryPath);
    }
}
