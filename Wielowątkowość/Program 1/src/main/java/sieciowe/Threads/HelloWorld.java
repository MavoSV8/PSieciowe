package sieciowe.Threads;

public class HelloWorld extends Thread{


    public void run(){
        System.out.println("Hello World");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
