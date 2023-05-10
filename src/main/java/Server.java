import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Server {
    public static void main(String[] args) {
        System.out.println("Server started");
        int port = 8085;
        BlockingQueue<String> text = new ArrayBlockingQueue<>(5);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
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
                            while (true) {
                                if (text.size() > 0) {
                                    System.out.println(text);
                                    try {
                                        String takenElem = text.take();
                                        output.println(takenElem);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        });
                        Thread inThread = new Thread(() -> {
                            while (true) {
                                try {
                                    String inp = input.readLine();
                                    System.out.println(inp);
                                    if (inp != null) {
                                        text.put(inp);
                                    }
                                } catch (IOException | InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                        outThread.start();
                        inThread.start();

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