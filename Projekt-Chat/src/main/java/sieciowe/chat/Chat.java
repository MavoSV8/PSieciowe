package sieciowe.chat;


import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.concurrent.*;

public class Chat {

    private Gui gui;
    private Thread sender;
    private Thread receiver;
    private String nickname;
    private String ip = "230.0.0.0";
    private InetAddress group;
    private int port = 3333;
    private MulticastSocket socket;
    private boolean connected = false;
    private byte TTL = 1;
    private BlockingQueue<JSON> queue = new LinkedBlockingQueue<>();
    private boolean busy = false;
    private Thread timer;


    public Chat() {


        gui = new Gui();


    }

    private void createTimer() {
        timer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(busy){
                    gui.getChatArea().append("Change nickname and try to reconnect" + "\n");
                    receiver.stop();
                    sender.stop();
                    try {
                        leaveGroupAndCloseSocket();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    busy = false;
                }else{
                    connected = true;
                    gui.getChatArea().append("Connected" + "\n");
                }
            }
        });

    }

    private void leaveGroupAndCloseSocket() throws IOException {
        socket.leaveGroup(group);
        socket.close();
    }

    private void createSenderThread() {
        sender = new Thread(new Runnable() {

            private MulticastSocket socketSender = socket;

            private void sendMessage(JSON sentJSON) {
                try {
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream(65355);
                    ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
                    objectStream.writeObject(sentJSON);
                    byte buff[] = byteStream.toByteArray();
                    DatagramPacket pack = new DatagramPacket(buff, buff.length, InetAddress.getByName(ip), port);
                    socketSender.send(pack, TTL);
                    objectStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void run() {

                JSON jsonToSend = new JSON();
                jsonToSend.setType("NICK");
                jsonToSend.setNickname(nickname);
                sendMessage(jsonToSend);
                while (!connected) {
                    if (receiver.isInterrupted()) {
                        sender.interrupt();
                    }

                }

                gui.getSendButton().addActionListener(e -> {
                    JSON json = new JSON();
                    LocalTime localTime = LocalTime.now();
                    json.setType("MSG");
                    json.setContent(gui.getMessageInput().getText());
                    if (localTime.getHour() < 10 && localTime.getMinute() < 10) {
                        json.setTime("0" + localTime.getHour() + ":" + "0" + localTime.getMinute());
                    } else if (localTime.getHour() >= 10 && localTime.getMinute() < 10) {
                        json.setTime(localTime.getHour() + ":" + "0" + localTime.getMinute());
                    } else if (localTime.getHour() < 10) {
                        json.setTime("0" + localTime.getHour() + ":" + localTime.getMinute());
                    } else {
                        json.setTime(localTime.getHour() + ":" + localTime.getMinute());
                    }
                    json.setNickname(nickname);
                    try {
                        queue.put(json);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                });

                while (true) {
                    try {
                        JSON sentJSON = queue.take();
                        sendMessage(sentJSON);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }

                }
                try {
                    leaveGroupAndCloseSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    private void createReceiverThread() {
        receiver = new Thread(new Runnable() {
            private MulticastSocket socketReceiver = socket;
            private byte[] buff = new byte[65535];

            private JSON receiveMessage() {
                JSON receivedJSON = null;
                try {
                    DatagramPacket packet = new DatagramPacket(buff, buff.length);
                    socketReceiver.receive(packet);
                    ByteArrayInputStream byteStream = new ByteArrayInputStream(buff);
                    ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(byteStream));
                    receivedJSON = (JSON) inputStream.readObject();
                    inputStream.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return receivedJSON;
            }

            @Override
            public void run() {
                timer.start();
                while (true) {
                    JSON receivedJSON = receiveMessage();
                    JSONObject rJSON = new JSONObject(receivedJSON);
                    if (rJSON.getString("type").equals("NICKBUSY") && !connected) {
                        busy = true;
                    }
                    if (rJSON.getString("type").equals("MSG") && connected) {
                        gui.getChatArea().append(rJSON.getString("time") + " " + rJSON.getString("nickname") + ": " + rJSON.getString("content") + "\n");
                    }
                    if (rJSON.getString("type").equals("NICK") && connected) {
                        if (nickname.equals(rJSON.getString("nickname"))) {
                            JSON sentJSON = new JSON();
                            sentJSON.setType("NICKBUSY");
                            try {
                                queue.put(sentJSON);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }


    private void addListenerToJoinButton() {

        gui.getJoinButton().addActionListener(e -> {

            if (getNickFromField()) {
                try {

                    group = InetAddress.getByName("230.0.0.0");
                    socket = new MulticastSocket(3333);
                    socket.joinGroup(group);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                createTimer();
                createSenderThread();
                createReceiverThread();
                sender.start();
                receiver.start();
            }
        });


    }

    public void startChat() {

        addListenerToJoinButton();
    }

    private boolean getNickFromField() {
        if (!gui.getNickInput().getText().equals("") && !gui.getNickInput().getText().equals(" ")) {
            nickname = gui.getNickInput().getText().trim().replace(" ", "_");
            return true;
        } else {
            gui.getChatArea().setText("Input correct nickname");
            gui.getChatArea().setCaretPosition(gui.getChatArea().getDocument().getLength());
            return false;
        }

    }

}
