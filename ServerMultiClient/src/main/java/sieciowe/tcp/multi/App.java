package sieciowe.tcp.multi;


import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Gui gui = new Gui();
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
            server.startServer(address, Integer.parseInt(port), 3);
            while(true){
                server.clientConnect();
                server.run();
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            System.out.println("There is some problem with server!");
            server.stopServer();
        }

    }




}