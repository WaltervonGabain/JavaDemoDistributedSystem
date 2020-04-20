import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * A Java IO server Response Handler.
 * It receives messages from the Input Terminal which get to be passed on to the Actor.
 */


public class ResponseHandler {

    private ServerSocket serverSocket;

    public void start(int port) {
        try {

            serverSocket = new ServerSocket(port);
            while (true)
                new Handler(serverSocket.accept()).start();

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
                String message = "";
                int messageCount = 0;
                while ((inputLine = in.readLine()) != null) {
                    if ("Terminate".equals(inputLine)) {
                        out.println("Connection terminated");
                        break;
                    }
                    if ("message".equals(inputLine)) {
                        messageCount++;
                        System.out.println("Message received");
                        out.println(inputLine);
                    }
                    if ("request".equals(inputLine) && messageCount > 0) {
                        messageCount--;
                        System.out.println("Request received");
                        out.println("message");
                    }
                    //out.println(inputLine);
                }

                System.out.println("Server terminated");
                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ResponseHandler receiver = new ResponseHandler();
        receiver.start(5555);
    }
}