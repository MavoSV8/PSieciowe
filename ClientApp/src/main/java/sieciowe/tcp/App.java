package sieciowe.tcp;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String inputString;
        String ip;
        String port;
        boolean isConnected;
        boolean isReceived;

        boolean disconnect = false;;
        Client client = new Client();

        System.out.println("Hello!");
        System.out.println("Enter IP");
        ip = scanner.nextLine();
        System.out.println("Enter port");
        port = scanner.nextLine();
        isConnected = client.connect(ip, Integer.parseInt(port));
        if(isConnected) {
            while (!disconnect) {
                System.out.println("Write message to server. If you want to disconnect type 'disconnect'.");
                inputString = scanner.nextLine();
                if (inputString.equals("disconnect")) {
                    disconnect = true;
                    client.closeSocket();
                } else {
                    System.out.println("Sent: " + inputString.getBytes().length + " bytes");
                    client.send(inputString);
                    isReceived = client.receive();
                    if(!isReceived){
                        disconnect = true;
                        client.closeSocket();

                    }
                }
            }
        }

    }
}
