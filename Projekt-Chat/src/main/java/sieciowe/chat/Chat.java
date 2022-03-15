package sieciowe.chat;



import com.github.cliftonlabs.json_simple.JsonObject;


import java.io.*;

import java.net.*;
import java.time.LocalTime;
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
    private BlockingQueue<JsonObject> queue = new LinkedBlockingQueue<>();
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

            private void sendMessage(JsonObject sentJSON) {
                try {
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream(65355);
                    ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
                    objectStream.writeObject(sentJSON);
                    byte[] buff = byteStream.toByteArray();
                    DatagramPacket pack = new DatagramPacket(buff, buff.length, InetAddress.getByName(ip), port);
                    socketSender.send(pack, TTL);
                    objectStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void run() {

                JsonObject jsonToSend = new JsonObject();
                jsonToSend.put("type","NICK");
                jsonToSend.put("nickname",nickname);
                sendMessage(jsonToSend);
                while (!connected) {
                    if (receiver.isInterrupted()) {
                        sender.interrupt();
                    }

                }

                gui.getSendButton().addActionListener(e -> {
                    JsonObject json = new JsonObject();
                    LocalTime localTime = LocalTime.now();
                    json.put("type","MSG");
                    json.put("content",gui.getMessageInput().getText());
                    if (localTime.getHour() < 10 && localTime.getMinute() < 10) {
                        json.put("time","0" + localTime.getHour() + ":" + "0" + localTime.getMinute());
                    } else if (localTime.getHour() >= 10 && localTime.getMinute() < 10) {
                        json.put("time",localTime.getHour() + ":" + "0" + localTime.getMinute());
                    } else if (localTime.getHour() < 10) {
                        json.put("time","0" + localTime.getHour() + ":" + localTime.getMinute());
                    } else {
                        json.put("time",localTime.getHour() + ":" + localTime.getMinute());
                    }
                    json.put("nickname",nickname);
                    try {
                        queue.put(json);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                });

                while (true) {
                    try {
                        JsonObject sentJSON = queue.take();
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

            private JsonObject receiveMessage() {
                JsonObject receivedJSON = null;
                try {
                    DatagramPacket packet = new DatagramPacket(buff, buff.length);
                    socketReceiver.receive(packet);
                    ByteArrayInputStream byteStream = new ByteArrayInputStream(buff);
                    ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(byteStream));
                    receivedJSON =(JsonObject) inputStream.readObject();
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
                    //JSON receivedJSON = receiveMessage();
                    JsonObject rJSON = receiveMessage();
                    if (rJSON.get("type").equals("NICKBUSY") && !connected) {
                        busy = true;
                    }
                    if (rJSON.get("type").equals("MSG") && connected) {
                        gui.getChatArea().append(rJSON.get("time") + " " + rJSON.get("nickname") + ": " + rJSON.get("content") + "\n");
                    }
                    if (rJSON.get("type").equals("NICK") && connected) {
                        if (nickname.equals(rJSON.get("nickname"))) {
                            JsonObject sentJSON = new JsonObject();
                            sentJSON.put("type","NICKBUSY");
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
