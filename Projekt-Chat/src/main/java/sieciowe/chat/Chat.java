package sieciowe.chat;


import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Chat {

    private Gui gui;
    private Sender sender;
    private Receiver receiver;
    private String nickname;
    private String ip = "230.0.0.0";
    private InetAddress group;
    private int port = 3333;
    private MulticastSocket socket;
    private boolean connected;

    public Chat(){


        gui = new Gui();
        addListenerToButton();

    }

    public void addListenerToButton(){

        gui.getJoinButton().addActionListener(e-> {

            try {

                group = InetAddress.getByName("230.0.0.0");
                socket = new MulticastSocket(3333);
                socket.joinGroup(group);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            sender = new Sender(socket, 3333, "230.0.0.0", gui.getMessageInput(), gui.getSendButton(),gui.getNickInput(),connected);
            receiver = new Receiver(socket, gui.getChatArea(),connected);
            sender.start();
            receiver.start();
        });
    }

    public void connectToChat(){



    }

    public void disconnectFromChat(){

    }


}
