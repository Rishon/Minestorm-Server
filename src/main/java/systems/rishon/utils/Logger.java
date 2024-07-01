package systems.rishon.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Logger {

    private static Logger logger;

    private PrintWriter writer;
    private LogLevel logLevel;

    public Logger() {
        logger = this;

        try {
            File logsDirectory = new File("logs");
            if (!logsDirectory.exists()) logsDirectory.mkdir();

            FileWriter fileWriter = new FileWriter(logsDirectory + "/latest.log", true);
            this.writer = new PrintWriter(fileWriter, true);
            this.logLevel = LogLevel.INFO;
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public void end() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        File latestLog = new File("logs/latest.log");
        File newLog = new File("logs/" + timestamp + ".log");

        if (latestLog.renameTo(newLog)) {
            System.out.println("Log file saved as " + newLog.getName());
        } else {
            System.err.println("Failed to save log file.");
        }

        this.writer.close();
    }

    public void log(LogLevel level, String message) {
        if (shouldLog(level)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String formattedMessage = String.format("[%s] [%s] %s", timestamp, level, message);
            writer.println(formattedMessage);
            System.out.println(formattedMessage);
        }
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void warn(String message) {
        log(LogLevel.WARNING, message);
    }

    private boolean shouldLog(LogLevel level) {
        return level.ordinal() >= this.logLevel.ordinal();
    }

    public enum LogLevel {
        INFO, DEBUG, ERROR, WARNING
    }
}
