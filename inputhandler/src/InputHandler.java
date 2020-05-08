import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * A simple Java socket server that receives messages and responds every second.
 */


public class InputHandler {

    private ServerSocket serverSocket;

    public void start(int port) {
        try {

            serverSocket = new ServerSocket(port);
            System.out.println("Server listening for messages");

            while (true)
                new Handler(serverSocket.accept()).run();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }

    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Handler extends Thread {

        private final Socket clientSocket;

        public Handler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                int messageCount = 0;

                while ((inputLine = in.readLine()) != null) {
                    if ("Terminate".equals(inputLine)) {
                        out.println("Connection terminated");
                        break;
                    }
                    if ("message".equals(inputLine)) {
                        System.out.println("Amount of messages received: " + messageCount);
                        Thread.sleep(1000);
                        messageCount++;
                        out.println("response");
                    }
                }

                System.out.println("Server terminated");
                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        InputHandler handler = new InputHandler();
        handler.start(8010);
    }
}
