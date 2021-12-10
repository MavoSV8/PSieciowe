package sieciowe.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

//ref: https://www.baeldung.com/a-guide-to-java-sockets

public class Server {
    private ServerSocket serverSocket;

    public void startServer(InetAddress address, int port, int backlog) {
        try {

            serverSocket = new ServerSocket(port, backlog, address);
            while (true)
                new EchoClientHandler(serverSocket.accept()).start();
        } catch (IOException e) {
            e.fillInStackTrace();
            System.out.println("Cannot connect!");
        }

    }

    public void stopServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.fillInStackTrace();
            System.out.println("Cannot close connection!");
        }
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            } catch (IOException e) {
                e.fillInStackTrace();
                System.out.println("Something went wrong when creating streams!");
            }


            String inputLine = "";
            try {
                if((inputLine = in.readLine()) != null) {
                    out.println(inputLine);
                }
            }
            catch (IOException e){

            }


            try {
                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
                e.fillInStackTrace();
                System.out.println("Cannot close connection with client!");
            }

        }
    }
}
