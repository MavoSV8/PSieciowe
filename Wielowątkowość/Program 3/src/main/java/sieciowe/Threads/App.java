package sieciowe.Threads;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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


    }

}
