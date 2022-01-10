package sieciowe.Threads;


import javax.swing.*;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;


public class MyTask {

    private JTextArea textArea;
    private int id;
    private AtomicBoolean check;
    public MyTask(JTextArea textArea, int id, AtomicBoolean check) {

        this.textArea = textArea;
        this.id = id;
        this.check = check;

    }

    public Future<Void> getFuture() {
        return future;

    }

    public void setCheck(AtomicBoolean check) {
        this.check = check;
    }

    private Future<Void> future = Executors.newSingleThreadExecutor().submit(() -> {try {
        String sb = "";
        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            if(check.get()) {
                break;
            }
            sb = Character.toString(alphabet) + id;
            textArea.append(sb + '\n');
            textArea.setCaretPosition(textArea.getDocument().getLength());
            sleep(1000);
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
        return null;});

}



