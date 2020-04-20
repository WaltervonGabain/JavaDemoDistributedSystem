import org.junit.Test;

import java.net.*;
import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

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
        inputTerminal.startConnection("127.0.0.1", 5555);

        int input;
        Scanner inputScan = new Scanner(System.in);
        System.out.println("Enter amount of messages:");

        while (true) {
            input = inputScan.nextInt();
            for (int i = input; i > 0; i--) {

                if (inputTerminal.sendMessage("message").equals("message"))
                    System.out.println("Message send successfully");

                input--;
            }
        }
    }

    @Test
    public void givenClient2_whenServerResponds_thenCorrect() {
        Actor test3Actor = new Actor();
        test3Actor.startConnection("127.0.0.1", 5555);
        String msg1 = test3Actor.sendMessage("hello");
        String msg2 = test3Actor.sendMessage("world");
        String terminate = test3Actor.sendMessage(".");

        assertEquals(msg1, "Input received");
        assertEquals(msg2, "Input received");
        assertEquals(terminate, "Terminated");
    }
}