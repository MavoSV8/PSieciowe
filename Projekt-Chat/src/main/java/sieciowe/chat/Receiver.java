package sieciowe.chat;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.concurrent.atomic.AtomicBoolean;


public class Receiver extends Thread {



    private MulticastSocket socket;
    private JTextArea output;
    private boolean connected;

    public Receiver(MulticastSocket socket, JTextArea output, boolean connected){

        this.socket = socket;
        this.output = output;
        this.connected = connected;
    }

    public void receiveJoiningMessage(){
        byte[] buff = new byte[65535];
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String received = new String(packet.getData(), 0, packet.getLength());

        if(!received.equals("BUSY") && received.length() != 0){
            connected = true;
        }


    }

    public void receiveMessage(){

    }

    public void run() {
    //TODO Dodać kolejkę z której reciever będzie odczytywać, a sender będzie dodawać
        receiveJoiningMessage();

        while (connected) {
            try {
                byte[] buff = new byte[65535];
                DatagramPacket packet = new DatagramPacket(buff, buff.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(received);
                output.append(received + "\n");
                output.setCaretPosition(output.getDocument().getLength());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
