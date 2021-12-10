package sieciowe.tcp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        InetAddress address;
        String ip;
        String port;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter server IP");
        ip = scanner.nextLine();
        System.out.println("Enter server port");
        port = scanner.nextLine();
        Server server = new Server();

        try {
            address = InetAddress.getByName(ip);
            server.startServer(address, Integer.parseInt(port), 30);
            server.clientConnect();
            server.run();
            server.stopServer();

        } catch (Exception e) {
            e.fillInStackTrace();
            System.out.println("There is some problem with server!");
        }

    }
}
