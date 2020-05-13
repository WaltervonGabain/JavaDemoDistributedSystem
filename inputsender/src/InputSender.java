import java.net.*;
import java.io.*;

/*
 * Simple Java socket client that continuously attempts to sends messages to the server.
 */

public class InputSender {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
        try {

            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeConnection() {
        try {

            out.close();
            in.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String msg) {
        try {
            out.println(msg);
            return in.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        InputSender inputSender = new InputSender();

        inputSender.startConnection("10.102.175.102", 8010);

        while (true) {
            if (inputSender.sendMessage("message").equals("response"))
                System.out.println("Message send successfully");
        }
    }
}