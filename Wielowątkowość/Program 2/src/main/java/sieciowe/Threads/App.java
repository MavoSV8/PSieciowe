package sieciowe.Threads;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        ArrayList<MyThread> myThreadArrayList = new ArrayList<>();
        int i;
        Gui gui = new Gui();

        for (i = 0; i < 10; i++) {
            myThreadArrayList.add(new MyThread(gui.getOutputField(),i));
        }

        for (MyThread thread : myThreadArrayList) {
            thread.start();
        }

        gui.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input;
                try {
                    input = Integer.parseInt(gui.getInputNumberField().getText());
                    myThreadArrayList.get(input).resume();
                }
                catch(NumberFormatException | IndexOutOfBoundsException exception){
                    exception.fillInStackTrace();
                    gui.getOutputField().append("Value in input is not a number or is greater than 9 or less than 0. \n");

                }

            }
        });

        gui.getStopButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input;
                try {
                    input = Integer.parseInt(gui.getInputNumberField().getText());
                    myThreadArrayList.get(input).suspend();
                }
                catch(NumberFormatException | IndexOutOfBoundsException exception){
                    exception.fillInStackTrace();
                    gui.getOutputField().append("Value in input is not a number or is greater than 9 or less than 0. \n");

                }


            }
        });

    }
}
