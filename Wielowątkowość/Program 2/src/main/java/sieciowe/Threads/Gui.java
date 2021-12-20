package sieciowe.Threads;

import javax.swing.*;

public class Gui {
    private JPanel panel = new JPanel();
    private JFrame f = new JFrame();
    private JButton b = new JButton("click");
    private JTextField textField = new JTextField();
    private void initialize(){
        b.setBounds(130,100,100, 40);
        f.add(b);
        f.setSize(400,500);
        f.setLayout(null);
        f.setVisible(true);
        f.add(textField);
    }

}
