package sieciowe.chat;


import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

public class Sender extends Thread {


    private int groupPort;
    private String groupIP;
    private JTextField messageInput;
    private JTextField nickInput;
    private JButton button;
    private final byte TTL = 1;
    private MulticastSocket socket;
    private boolean connected;

    public Sender(MulticastSocket socket, int groupPort, String groupIP, JTextField messageInput, JButton button, JTextField nickInput, boolean connected) {

        this.socket = socket;
        this.groupIP = groupIP;
        this.groupPort = groupPort;
        this.messageInput = messageInput;
        this.button = button;
        this.nickInput = nickInput;
        this.connected = connected;
    }

    public void run() {

        sendJoinMessage();

        button.addActionListener(e -> {

            sendMessage(messageInput.getText());


        });

    }

    public void sendMessage(String message){
        try {
            byte buff[] = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket pack = new DatagramPacket(buff, buff.length, InetAddress.getByName(groupIP), groupPort);
            socket.send(pack, TTL);

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }


    public void sendJoinMessage(){
        sendMessage(nickInput.getText());

    }


}


