import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8085;
        try(Socket serverSocket = new Socket(host, port);
            PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))) {
            final String qw = in.readLine();
            System.out.println(qw);
            Scanner scanner = new Scanner(System.in);
//            Thread outThread = new Thread(() -> {
//            });
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
            while (true){
                String fd = scanner.nextLine();
                if (fd.equals("/exit")){
                    serverSocket.close();
//                    inThread.interrupt();
                    return;
                }
                out.println(fd);
            }
//            outThread.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
