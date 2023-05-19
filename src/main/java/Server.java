import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    public static void main(String[] args) throws IOException {
        Log log = Log.getInstance();
        log.createLogFile();
        System.out.println("Server started");
        int port = 8085;
        List<String> text = Collections.synchronizedList(new ArrayList<>());
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
//                new EchoTread(text, serverSocket).start();
//                if (EchoTread.interrupted()){
//                    return;
//                }
                new Thread(() -> {
                    try {
                        Socket socket = serverSocket.accept();
                        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        System.out.println("New connection accepted");
                        output.println("Please enter your name");
                        final String name = input.readLine();
                        output.println(String.format("Hi %s! ", name));
                        Thread outThread = new Thread(() -> {
                            int n = 0;
                            while (true) {
                                if (text.size() > n) {
                                    String takenElem = text.get(n);
                                    output.println(takenElem);
                                    n++;
                                }
                            }
                        });
                        outThread.start();
//                        Thread inThread = new Thread(() -> {
                            while (true) {
                                try {
                                    String inp = input.readLine();
                                    if (inp != null) {
                                        text.add(inp);
                                        log.logging(name, inp);
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
//                        });
//                        inThread.start();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }

                }).start();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}