package sieciowe.Threads;

import javax.swing.*;
import java.awt.*;

public class Gui {

    private JFrame frame;
    private JPanel panel;
    private JTextArea outputField;
    private JScrollPane scrollPane;


    public Gui() {

        frame = new JFrame ("Program 3");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        outputField = new JTextArea (10, 5);
        scrollPane = new JScrollPane(outputField);



        panel.setPreferredSize(new Dimension(370, 390));
        panel.setLayout(null);


        panel.add(scrollPane);

        outputField.setEditable(false);
        outputField.setLineWrap(true);
        outputField.setAutoscrolls(true);

        scrollPane.setBounds(15, 65, 340, 315);


        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible (true);

    }





    public JTextArea getOutputField() {
        return outputField;
    }

    public void setOutputField(JTextArea outputField) {
        this.outputField = outputField;
    }



    public void setOutputField(String output) {
        outputField.setText(output);

    }

}
