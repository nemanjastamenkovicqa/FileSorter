import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileSummaryGenerator {

    public void generateFileSummary(String directoryPath) {
        File directory = new File(directoryPath);

        // Check if the provided path is a directory
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory path. Please provide a valid directory.");
            return;
        }

        // Get a list of files in the directory
        File[] files = directory.listFiles();

        // Generate and display file summary
        if (files != null && files.length > 0) {
            int totalFiles = files.length;
            long totalSize = calculateTotalSize(files);
            Map<String, Integer> fileTypeDistribution = calculateFileTypeDistribution(files);

            System.out.println("File Summary:");
            System.out.println("Total Files: " + totalFiles);
            System.out.println("Total Size: " + formatFileSize(totalSize));
            System.out.println("File Type Distribution:");
            for (Map.Entry<String, Integer> entry : fileTypeDistribution.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " files");
            }
        } else {
            System.out.println("No files available in the directory.");
        }
    }

    private long calculateTotalSize(File[] files) {
        long totalSize = 0;
        for (File file : files) {
            if (file.isFile()) {
                totalSize += file.length();
            }
        }
        return totalSize;
    }

    private Map<String, Integer> calculateFileTypeDistribution(File[] files) {
        Map<String, Integer> fileTypeDistribution = new HashMap<>();
        for (File file : files) {
            if (file.isFile()) {
                String fileType = getFileType(file);
                fileTypeDistribution.put(fileType, fileTypeDistribution.getOrDefault(fileType, 0) + 1);
            }
        }
        return fileTypeDistribution;
    }

    private String getFileType(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "Unknown" : fileName.substring(dotIndex + 1).toLowerCase();
    }

    private String formatFileSize(long sizeInBytes) {
        if (sizeInBytes < 1024) {
            return sizeInBytes + " B";
        } else if (sizeInBytes < 1024 * 1024) {
            return sizeInBytes / 1024 + " KB";
        } else {
            return sizeInBytes / (1024 * 1024) + " MB";
        }
    }
}
