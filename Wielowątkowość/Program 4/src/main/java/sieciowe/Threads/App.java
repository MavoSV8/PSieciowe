package sieciowe.Threads;


import java.util.ArrayList;
import java.util.concurrent.*;

public class App {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ArrayList<CompletableFuture<Void>> completableFutures = new ArrayList<>();
        Executor executor = Executors.newFixedThreadPool(10);
        int i;
        Gui gui = new Gui();
        gui.getStartButton().addActionListener(e -> {
            int input;
            try {
                input = Integer.parseInt(gui.getInputNumberField().getText());
                Void result = null;
                completableFutures.get(input).cancel(true);
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

        for (i = 0; i < 10; i++) {
            completableFutures.add(new CompletableFuture<>());

        }
        int j = 0;
        for (CompletableFuture cf : completableFutures) {
            cf = CompletableFuture.runAsync(new MyThread(gui.getOutputField(),j));
            j++;
            cf.get();

        }






    }
}
