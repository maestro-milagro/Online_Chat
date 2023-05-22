import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {
        Client2 client = new Client2();
        String host = "127.0.0.1";
        int port = client.getPort();
        try (Socket serverSocket = new Socket(host, port);
             PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))) {
            final String qw = in.readLine();
            System.out.println(qw);
            Scanner scanner = new Scanner(System.in);
            Thread inThread = new Thread(() -> {
                while (true) {
                    String ans = null;
                    try {
                        ans = in.readLine();
                        if (ans != null) {
                            System.out.println(ans);
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                }
            });
            inThread.start();
            while (true) {
                String fd = scanner.nextLine();
                if (fd.equals("/exit")) {
                    serverSocket.close();
                    return;
                }
                if (fd.equals("/port")) {
                    System.out.println("port - " + client.getPort());
                    continue;
                }
                out.println(fd);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setPort(int newValue) {
        File file = new File("C:", "port.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            String text = "port - " + newValue;
            bw.write(text);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getPort() {
        File file = new File("C:", "port.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String ans = "";
            String s;
            while ((s = br.readLine()) != null) {
                ans += s;
            }
            String[] useless = ans.split(" ");
            int port = Integer.parseInt(useless[useless.length - 1]);
            return port;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }
}
