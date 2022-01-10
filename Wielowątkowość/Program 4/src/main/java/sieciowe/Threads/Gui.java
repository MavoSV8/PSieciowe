package sieciowe.Threads;

import java.awt.*;
import javax.swing.*;

public class Gui {

    private JFrame frame;
    private JPanel panel;
    private JButton startButton;
    private JTextArea outputField;
    private JTextField inputNumberField;
    private JScrollPane scrollPane;


    public Gui() {

        frame = new JFrame ("Program 2");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        startButton = new JButton ("Remove");
        outputField = new JTextArea (10, 5);
        inputNumberField = new JTextField (1);
        scrollPane = new JScrollPane(outputField);



        panel.setPreferredSize(new Dimension(370, 390));
        panel.setLayout(null);


        panel.add(startButton);
        panel.add(scrollPane);
        panel.add(inputNumberField);

        outputField.setEditable(false);
        outputField.setLineWrap(true);
        outputField.setAutoscrolls(true);

        startButton.setBounds(70, 20, 120, 30);
        scrollPane.setBounds(15, 65, 340, 315);
        inputNumberField.setBounds(30, 20, 30, 30);

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


    public JTextArea getOutputField() {
        return outputField;
    }

    public void setOutputField(JTextArea outputField) {
        this.outputField = outputField;
    }

    public JTextField getInputNumberField() {
        return inputNumberField;
    }

    public void setInputNumberField(JTextField inputNumberField) {
        this.inputNumberField = inputNumberField;
    }

    public void setOutputField(String output) {
        outputField.setText(output);

    }

}
