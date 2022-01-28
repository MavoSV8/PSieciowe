package sieciowe.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui {

    private JFrame frame;
    private JPanel panel;
    private JButton joinButton;
    private JTextArea chatArea;
    private JTextField messageInput;
    private JButton sendButton;
    private JTextField nickInput;
    private JLabel nickLabel;
    private JScrollPane scrollPane;




    public JButton getJoinButton() {
        return joinButton;
    }

    public void setJoinButton(JButton joinButton) {
        this.joinButton = joinButton;
    }

    public JTextArea getChatArea() {
        return chatArea;
    }

    public void setChatArea(JTextArea chatArea) {
        this.chatArea = chatArea;
    }

    public JTextField getMessageInput() {
        return messageInput;
    }

    public void setMessageInput(JTextField messageInput) {
        this.messageInput = messageInput;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public void setSendButton(JButton sendButton) {
        this.sendButton = sendButton;
    }

    public JTextField getNickInput() {
        return nickInput;
    }

    public void setNickInput(JTextField nickInput) {
        this.nickInput = nickInput;
    }

    public JLabel getNickLabel() {
        return nickLabel;
    }

    public void setNickLabel(JLabel nickLabel) {
        this.nickLabel = nickLabel;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public Gui() {

        frame = new JFrame("Chat");
        panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        joinButton = new JButton ("Join");
        chatArea = new JTextArea (5, 5);
        messageInput = new JTextField (5);
        sendButton = new JButton ("Send");
        nickInput = new JTextField (5);
        nickLabel = new JLabel("Nick:");
        scrollPane = new JScrollPane(chatArea);

        panel.setPreferredSize(new Dimension(400, 573));
        panel.setLayout(null);


        panel.add(joinButton);
        panel.add(scrollPane);
        panel.add(messageInput);
        panel.add(sendButton);
        panel.add(nickInput);
        panel.add(nickLabel);


        joinButton.setBounds (225, 15, 100, 30);
        scrollPane.setBounds (10, 55, 380, 445);
        messageInput.setBounds (10, 515, 280, 25);
        sendButton.setBounds (305, 515, 85, 25);
        nickInput.setBounds (75, 15, 105, 30);
        nickLabel.setBounds (35, 15, 30, 25);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }


}
