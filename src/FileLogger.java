import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogger {

    private static final long MAX_LOG_FILE_SIZE_BYTES = 1024 * 1024; // 1 MB

    private String logFilePath;

    public FileLogger(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public void logAction(String action) {
        logEntry("INFO", action);
    }

    public void logError(String errorMessage) {
        logEntry("ERROR", errorMessage);
    }

    public void clearLogFile() {
        try {
            // Clear the contents of the log file
            Files.write(Path.of(logFilePath), new byte[0]);
            System.out.println("Log file cleared.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logEntry(String logLevel, String message) {
        try {
            // Check log file size and create a new log file if it exceeds the maximum size
            checkAndRotateLogFile();

            // Append the current timestamp, log level, and action to the log file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
                String logEntry = getCurrentTimestamp() + " [" + logLevel + "] - " + message;
                writer.write(logEntry);
                writer.newLine();
                System.out.println("Logged: " + logEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkAndRotateLogFile() {
        try {
            // Check log file size
            File logFile = new File(logFilePath);
            if (logFile.exists() && logFile.length() > MAX_LOG_FILE_SIZE_BYTES) {
                // Create a new log file with a timestamp in the filename
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String rotatedLogFilePath = logFilePath.replace(".txt", "_" + timestamp + ".txt");

                Files.move(Path.of(logFilePath), Path.of(rotatedLogFilePath));
                System.out.println("Log file rotated. Created new log file: " + rotatedLogFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public static void main(String[] args) {
        // Example usage:
        String logFilePath = "path/to/your/log/file.txt"; // Replace with your desired log file path

        // Create an instance of FileLogger
        FileLogger fileLogger = new FileLogger(logFilePath);

        // Log an action
        fileLogger.logAction("Performed sorting operation");

        // Log an error
        fileLogger.logError("Failed to execute sorting operation");

        // Clear the log file
        fileLogger.clearLogFile();
    }
}
