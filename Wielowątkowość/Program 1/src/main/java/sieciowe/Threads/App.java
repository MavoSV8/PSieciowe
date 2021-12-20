package sieciowe.Threads;

public class App {

    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.start();
        try {
            helloWorld.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Child thread ended!");
    }
}
