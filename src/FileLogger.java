import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogger {

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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            // Append the current timestamp, log level, and action to the log file
            String logEntry = getCurrentTimestamp() + " [" + logLevel + "] - " + message;
            writer.write(logEntry);
            writer.newLine();
            System.out.println("Logged: " + logEntry);
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
    }
}
