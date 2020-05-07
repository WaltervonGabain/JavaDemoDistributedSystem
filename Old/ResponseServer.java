import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ResponseServer {

    private ServerSocket serverSocket;

    public void start(int port) {
        try {

            serverSocket = new ServerSocket(port);
            while (true)
                new Server(serverSocket.accept()).start();

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

    private static class Server extends Thread {

        private final Socket clientSocket;

        public Server(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (".".equals(inputLine)) {
                        out.println("bye");
                        break;
                    }
                    if ("hello server".equals(inputLine)) {
                        out.println("hello client");
                    }
                    out.println(inputLine);
                }

                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ResponseServer responseServer = new ResponseServer();
        responseServer.start(6666);
    }
}