import org.junit.Test;
import java.net.*;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class Actor {
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
        Actor receiver = new Actor();
        receiver.startConnection("127.0.0.1", 5555);

        Actor sender = new Actor();
        sender.startConnection("127.0.0.1", 6666);

        System.out.println("Actors initiated");
        System.out.println("Actors listening for input");

        String received;
        String send;

        Scanner inputScan = new Scanner(System.in);
        while (true) {
            System.out.println("?");
            int input = inputScan.nextInt();
            // ipv requesten, luisteren

            if (input == 1) {
                received = receiver.sendMessage("request");
                System.out.println(received);
            }
            //send = sender.sendMessage(received);
            //System.out.println(send);
        }
    }

    @Test
    public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() {
        Actor client = new Actor();
        client.startConnection("127.0.0.1", 5555);
        String response = client.sendMessage("hello server");
        assertEquals("hello client", response);
    }

    @Test
    public void givenClient_whenServerEchosMessage_thenCorrect() {
        Actor client = new Actor();
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
            Actor client1 = new Actor();
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
        Actor client2 = new Actor();
        client2.startConnection("127.0.0.1", 6666);
        String msg1 = client2.sendMessage("hello");
        String msg2 = client2.sendMessage("world");
        String terminate = client2.sendMessage(".");

        assertEquals(msg1, "hello");
        assertEquals(msg2, "world");
        assertEquals(terminate, "bye");
    }
}

//import org.junit.Test;
//import java.net.*;
//import java.io.*;
//
//import static org.junit.Assert.assertEquals;
//
//public class Actor {
//
//    private static Actor receiver;
//    private static Actor sender;
//    private Socket clientSocket;
//    private PrintWriter out;
//    private BufferedReader in;
//
//    public void startConnection(String ip, int port){
//        try {
//
//            System.out.println("Actor attempting to establish connection");
//            clientSocket = new Socket(ip, port);
//            out = new PrintWriter(clientSocket.getOutputStream(), true);
//            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public String sendMessage(String msg) {
//        try {
//            out.println(msg);
//            return in.readLine();
//
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
//
////    public void stopConnection() {
////        try {
////            in.close();
////            out.close();
////            clientSocket.close();
////        } catch (IOException e) {
////            System.out.println(e.getMessage());
////        }
////    }
//
//    public void requestInput() {
//
//        while (true) {
//            try {
//
//                String inputLine;
//                if ((inputLine = receiver.in.readLine()) != null) {
//                    System.out.println(receiver.in.readLine());
//                    sender.sendMessage(inputLine);
//                }
//
//                receiver.sendMessage("request");
//
//            } catch ( IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    public static void main(String[] args) {
//        receiver = new Actor();
//        receiver.startConnection("127.0.0.1", 5555);
//        sender = new Actor();
//        sender.startConnection("127.0.0.1", 6666);
//        System.out.println("Actors initiated");
//
//        receiver.requestInput();
//        //sender.listenForInput();
//        System.out.println("Actors listening for input");
//    }
//
//
//    @Test
//    public void givenClient_whenServerEchosMessage_thenCorrect() {
//        Actor testActor = new Actor();
//        testActor.startConnection("127.0.0.1", 6666);
//
//        String resp1 = testActor.sendMessage("hello");
//        String resp2 = testActor.sendMessage("world");
//        String resp3 = testActor.sendMessage("!");
//        String resp4 = testActor.sendMessage(".");
//
//        assertEquals("hello", resp1);
//        assertEquals("world", resp2);
//        assertEquals("!", resp3);
//        assertEquals("Terminated", resp4);
//    }
//
//    @Test
//    public void givenClient1_whenServerResponds_thenCorrect() {
//        int test = 10;
//        Actor test2Actor = new Actor();
//        test2Actor.startConnection("127.0.0.1", 5555);
//
//        while (test > 0) {
//            String msg1 = test2Actor.sendMessage("hello");
//            String msg2 = test2Actor.sendMessage("world");
//            String terminate = test2Actor.sendMessage(".");
//
//            assertEquals(msg1, "hello");
//            assertEquals(msg2, "world");
//            assertEquals(terminate, "Terminated");
//
//            test--;
//        }
//    }
//
//    @Test
//    public void givenClient2_whenServerResponds_thenCorrect() {
//        Actor test3Actor = new Actor();
//        test3Actor.startConnection("127.0.0.1", 5555);
//        String msg1 = test3Actor.sendMessage("hello");
//        String msg2 = test3Actor.sendMessage("world");
//        String terminate = test3Actor.sendMessage(".");
//
//        assertEquals(msg1, "hello");
//        assertEquals(msg2, "world");
//        assertEquals(terminate, "Terminated");
//    }
//}