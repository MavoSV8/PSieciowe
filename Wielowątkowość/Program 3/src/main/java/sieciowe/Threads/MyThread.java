package sieciowe.Threads;

import javax.swing.*;

public class MyThread extends Thread {

    private JTextArea textArea;
    private int id;

    public MyThread(JTextArea textArea, int id) {

        this.textArea = textArea;
        this.id = id;

    }


    private void print() throws InterruptedException {
        String sb = "";

        while (true) {
            for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
                synchronized (textArea) {
                    sb = Character.toString(alphabet) + id;
                    textArea.append(sb + '\n');
                    textArea.setCaretPosition(textArea.getDocument().getLength());
                    sleep(1000);
                }
            }
        }

    }

    public void run() {

        try {
            print();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
