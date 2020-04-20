import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public void start(int port) {
        try {

            serverSocket = new ServerSocket(port);
            while (true)
                new EchoClientHandler(serverSocket.accept()).start();

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

    private static class EchoClientHandler extends Thread {

        private final Socket clientSocket;

        public EchoClientHandler(Socket socket) {
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
                        System.out.println("yeet");
                        break;
                    }
                    if ("hello server".equals(inputLine)) {
                        out.println("hello client");
                    }
                    out.println(inputLine);
                    System.out.println("woot");
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
        Server server = new Server();
        server.start(6666);
    }
}


//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//
///*
//* A multi-threaded server application that listens for connections
//* and echos back the same string it receives until a . (dot) is send which will terminate the connection with the Actor.
//*/
//
//public class Server {
//
//    private ServerSocket serverSocket;
//
//    public void start(int port) {
//        try {
//
//            serverSocket = new ServerSocket(port);
//            while (true)
//                new ActorHandler(serverSocket.accept()).start();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            stop();
//        }
//
//    }
//
//    public void stop() {
//        try {
//
//            serverSocket.close();
//            System.out.println("Server Terminated");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class ActorHandler extends Thread {
//
//        private final Socket clientSocket;
//
//        public ActorHandler(Socket socket) {
//            this.clientSocket = socket;
//        }
//
//        public void run() {
//            try {
//
//                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//
//                System.out.println("Connection established, server listening for input");
//                String inputLine;
//                while ((inputLine = in.readLine()) != null) {
//                    if (".".equals(inputLine)) {
//                        out.println("Terminated");
//                        System.out.println("Connection terminated");
//                        break;
//                    }
//                    out.println(inputLine);
//                    System.out.println("Input received and send back");
//                }
//
//                in.close();
//                out.close();
//                clientSocket.close();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        System.out.println("Server initiated");
//        Server server = new Server();
//        server.start(6666);
//    }
//}
