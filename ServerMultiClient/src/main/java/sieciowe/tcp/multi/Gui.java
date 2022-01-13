package sieciowe.tcp.multi;

import java.awt.*;
import javax.swing.*;

public class Gui {

    private JFrame frame;
    private JPanel panel;
    private JButton startButton;
    private JButton stopButton;
    private JTextArea outputField;
    private JTextField inputIP;
    private JTextField inputPORT;
    private JScrollPane scrollPane;
    private JLabel ipLabel;
    private JLabel portLabel;


    public Gui() {

        frame = new JFrame ("Server Multi Client");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        outputField = new JTextArea(10, 10);
        ipLabel = new JLabel("IP");
        portLabel = new JLabel("PORT");
        inputIP = new JTextField(10);
        inputPORT = new JTextField(10);
        scrollPane = new JScrollPane(outputField);



        panel.setPreferredSize(new Dimension(500, 600));
        panel.setLayout(null);


        panel.add(startButton);
        panel.add(stopButton);
        panel.add(scrollPane);
        panel.add(ipLabel);
        panel.add(portLabel);
        panel.add(inputIP);
        panel.add(inputPORT);

        outputField.setEditable(false);
        outputField.setLineWrap(true);
        outputField.setAutoscrolls(true);

        startButton.setBounds(300, 20, 70, 30);
        stopButton.setBounds(400, 20, 70, 30);
        scrollPane.setBounds(15, 65, 465, 500);
        inputIP.setBounds(60, 20, 100, 30);
        inputPORT.setBounds(210, 20, 60, 30);
        ipLabel.setBounds(30, 20, 30, 30);
        portLabel.setBounds(170, 20, 50, 30);



        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible (true);

    }

    public JButton getStartButton() {
        return startButton;
    }

    public void setStartButton(JButton startButton) {
        this.startButton = startButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public void setStopButton(JButton stopButton) {
        this.stopButton = stopButton;
    }

    public JTextArea getOutputField() {
        return outputField;
    }

    public void setOutputField(JTextArea outputField) {
        this.outputField = outputField;
    }

    public JTextField getInputIP() {
        return inputIP;
    }

    public void setInputNumberField(JTextField inputIP) {
        this.inputIP = inputIP;
    }

    public void setOutputField(String output) {
        outputField.setText(output);

    }

}
