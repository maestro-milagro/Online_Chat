import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    public static Log INSTANCE = null;
    public static File logFile = null;

    private Log() {}
    public static Log getInstance() {
        if (INSTANCE == null) {
            synchronized (Log.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Log();
                }
            }
        }
        return INSTANCE;
    }
    public void createLogFile() throws IOException {
        if(logFile == null) {
            logFile = new File("C:", "Log.txt");
            try {
                if (logFile.createNewFile());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void logging(String name, String msg) {
        LocalDateTime time = LocalDateTime.now();
        System.out.printf("[%s](%s): %s\n", name, time.format(DateTimeFormatter.ofPattern("HH:mm")), msg);

        String text = "[" + name + "](" + time.format(DateTimeFormatter.ofPattern("HH:mm")) + "): " + msg + "  \n";

        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
