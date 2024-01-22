import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogger {

    private String logFilePath;

    public FileLogger(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public void logAction(String action) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            // Append the current timestamp and action to the log file
            String logEntry = getCurrentTimestamp() + " - " + action;
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
}