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
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startServer(InetAddress address, int port, int backlog) {
        try {

            serverSocket = new ServerSocket(port, backlog, address);

        } catch (IOException e) {
            e.fillInStackTrace();
            System.out.println("Cannot connect!");
        }

    }

    public void clientConnect(){
        try {
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.fillInStackTrace();
            System.out.println("Cannot connect client!");
        }

    }

    public void run(){
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.fillInStackTrace();
            System.out.println("Cannot create streams");
        }
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                out.println(inputLine);
            }
        }
        catch (IOException e){
            e.fillInStackTrace();
            System.out.println("Something wrong with client message");
            try {
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            clientSocket.close();
            in.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
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
}
