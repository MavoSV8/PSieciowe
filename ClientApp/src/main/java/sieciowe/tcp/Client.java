package sieciowe.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

//ref: https://docs.oracle.com/javase/7/docs/api/java/net/Socket.html
//ref: https://www.baeldung.com/a-guide-to-java-sockets


public class Client {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;


    public boolean connect(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.fillInStackTrace();
            System.out.println("Cannot connect!");
            return false;
        }
        return true;
    }

    public boolean send(String message) {
        String received;
        out.println(message);
        try {
            if ((received = in.readLine()) != null) {
                System.out.println("Received: " + received.getBytes().length + " bytes");
                System.out.println(received);
            }
        } catch (IOException e) {
            e.fillInStackTrace();
            System.out.println("Cannot send message!");
            return false;
        }
        return true;
    }


    public void closeSocket() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.fillInStackTrace();
            System.out.println("Cannot close socket!");
        }
    }
}
