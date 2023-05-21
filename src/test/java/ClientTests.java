import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ClientTests {
    @Test
    public void setPortTest() {
        Client client = new Client();
        int newPort = 8086;
        String expected = "port - 8086";
        String result = "";

        client.setPort(newPort);
        File file = new File("C:", "port.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s;
            while ((s = br.readLine()) != null) {
                result += s;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void getPortTest() {
        Client client = new Client();
        int expected = 8086;
        client.setPort(8086);

        int result = client.getPort();

        Assertions.assertEquals(expected, result);
    }
}
