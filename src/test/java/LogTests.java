import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LogTests {
    @Test
    public void getInstanceTest() {
        Log unExpected = null;

        Log result = Log.getInstance();

        Assertions.assertNotEquals(unExpected, result);
    }

    @Test
    public void createLogFileTest() throws IOException {
        Log log = Log.getInstance();
        log.createLogFile();

        File result = Log.logFile;

        Assertions.assertNotNull(result);
    }

    @Test
    public void loggingTest() throws IOException {
        Log log = Log.getInstance();
        log.createLogFile();
        String name = "Mark";
        String msg = "Hello!";
        String expected = "Hello!";


        log.logging(name, msg);
        File file = new File(".", "Log.txt");
        String ans = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s;
            while ((s = br.readLine()) != null) {
                ans += s;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        String preResult = ans.split("  ")[ans.split("  ").length - 1];
        String result = preResult.split(" ")[1];

        Assertions.assertEquals(expected, result);

    }
}
