package sieciowe.Threads;


import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class App {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ArrayList<MyTask> tasks = new ArrayList<>();
        int i;
        Gui gui = new Gui();

        for(i = 0; i < 10; i++) {
            tasks.add(new MyTask(gui.getOutputField(),i,new AtomicBoolean(false)));
        }

        gui.getStartButton().addActionListener(e -> {
            int input;
            try {
                input = Integer.parseInt(gui.getInputNumberField().getText());
                tasks.get(input).setCheck(new AtomicBoolean(true));

            }
            catch(NumberFormatException | IndexOutOfBoundsException exception){
                exception.fillInStackTrace();
                gui.getOutputField().append("Value in input is not a number or is greater than 9 or less than 0. \n");

            }
            catch (CancellationException ce){
                ce.fillInStackTrace();
                gui.getOutputField().append("Tried to cancel. \n");
            }

        });




    }
}
