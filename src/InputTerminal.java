import org.junit.Test;

import java.net.*;
import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/*
 * Simple Java socket client that continuously attempts to sends messages to the server.
 */

public class InputTerminal {

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
        InputTerminal inputTerminal = new InputTerminal();

        inputTerminal.startConnection("localhost", 5555);

        while (true) {
            if (inputTerminal.sendMessage("message").equals("response"))
                System.out.println("Message send");
        }
    }

    @Test
    public void givenClient2_whenServerResponds_thenCorrect() {
        InputTerminal testInput = new InputTerminal();
        testInput.startConnection("localhost", 5555);

        String msg1 = testInput.sendMessage("message");
        String msg2 = testInput.sendMessage("message");
        String terminate = testInput.sendMessage("Terminate");

        assertEquals(msg1, "response");
        assertEquals(msg2, "response");
        assertEquals(terminate, "Connection terminated");
    }
}