package sieciowe.chat;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;




public class App {





    public static void main(String[] args) {

        Chat chat = new Chat();
        chat.startChat();



    }

}
