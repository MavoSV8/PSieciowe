package sieciowe.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Client {

    private Socket socket;
    private InetAddress ipAddress;
    private int port;
    private InetSocketAddress inetSocketAddress;
    private PrintWriter out;
    private BufferedReader in;
    private String received;

    public void socketCreate(){
    socket = new Socket();

    }

    public void connect(InetAddress address, int port){
        try {
            socket.connect(inetSocketAddress = new InetSocketAddress(address, port));
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void connect(InetAddress address) {
        ipAddress = address;
        port = 7;
        inetSocketAddress = new InetSocketAddress(address,port);
        try {
            socket.connect(inetSocketAddress);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void connect(){
        byte[] addr = {127,0,0,1};
        port = 7;
        try {
            ipAddress = InetAddress.getByAddress(addr);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        inetSocketAddress = new InetSocketAddress(ipAddress,7);
        try {
            socket.connect(inetSocketAddress);
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void send(String message){
        out.println(message);
        try {
             received = in.readLine();
             System.out.println(received);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void closeSocket(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
