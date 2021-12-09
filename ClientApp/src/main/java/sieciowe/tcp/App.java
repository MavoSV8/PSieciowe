package sieciowe.tcp;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String inputString;
        Client client = new Client();
        boolean exit = false;
        client.socketCreate();
        client.connect();

        while(!exit){

            inputString = scanner.next();
            scanner.nextLine();
            System.out.println(inputString);
            if(inputString.equals("exit")){
                exit = true;
            }
            else{
                client.send(inputString);
            }

        }
        client.closeSocket();


    }
}
