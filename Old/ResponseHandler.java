import org.junit.Test;
import java.net.*;
import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/*
 * A Java IO client Actor.
 * It continuously requests messages from the Response Handler to know if there are messages to be received.
 * If messages are received they will be handed on to the Server which echoes them back to the Actor.
 */

public class ResponseHandler {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port){
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

    public void closeConnection() {
        try {

            out.close();
            in.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
//        ResponseHandler sender = new ResponseHandler();
//        sender.startConnection("127.0.0.1", 6666);
//
//        System.out.println("Actors initiated");
//        System.out.println("Actors listening for input");

        ResponseHandler receiver = new ResponseHandler();
        String received = "";
        String send;

        Scanner inputScan = new Scanner(System.in);
        while (true) {
            receiver.startConnection("127.0.0.1", 5555);
            int input = inputScan.nextInt();
            // ipv requesten, luisteren

            if (input == 1) {
                System.out.println(input);
                while (!(received.equals("End"))) {
                    received = receiver.sendMessage("request");
                    System.out.println(received);
                }
                receiver.stopConnection();
                input = 0;
            }
            //send = sender.sendMessage(received);
            //System.out.println(send);
        }
    }

    @Test
    public void receiveMessages() {
        String response = "";

        while (!(response.equals("End"))) {
            ResponseHandler testHandler = new ResponseHandler();
            testHandler.startConnection("127.0.0.1", 5555);
            response = testHandler.sendMessage("request");
            assertEquals("message", response);
        }
    }

    @Test
    public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() {
        ResponseHandler client = new ResponseHandler();
        client.startConnection("127.0.0.1", 5555);
        String response = client.sendMessage("hello server");
        assertEquals("hello client", response);
    }

    @Test
    public void givenClient_whenServerEchosMessage_thenCorrect() {
        ResponseHandler client = new ResponseHandler();
        client.startConnection("127.0.0.1", 5555);
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        String resp3 = client.sendMessage("!");
        String resp4 = client.sendMessage(".");

        assertEquals("hello", resp1);
        assertEquals("world", resp2);
        assertEquals("!", resp3);
        assertEquals("bye", resp4);
    }

    @Test
    public void givenClient1_whenServerResponds_thenCorrect() {
        for (int i = 0; i < 3; i++) {
            ResponseHandler client1 = new ResponseHandler();
            client1.startConnection("127.0.0.1", 6666);
            String msg1 = client1.sendMessage("hello");
            String msg2 = client1.sendMessage("world");
            String terminate = client1.sendMessage(".");

            assertEquals(msg1, "hello");
            assertEquals(msg2, "world");
            assertEquals(terminate, "bye");
        }
    }

    @Test
    public void givenClient2_whenServerResponds_thenCorrect() {
        ResponseHandler client2 = new ResponseHandler();
        client2.startConnection("127.0.0.1", 6666);
        String msg1 = client2.sendMessage("hello");
        String msg2 = client2.sendMessage("world");
        String terminate = client2.sendMessage(".");

        assertEquals(msg1, "hello");
        assertEquals(msg2, "world");
        assertEquals(terminate, "bye");
    }
}