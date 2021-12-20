package sieciowe.Threads;


public class MyThread extends Thread{

    public MyThread(){

        this.suspend();

    }


    private void print() throws InterruptedException {
        String sb = "";
        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {

            sb = Character.toString(alphabet) + currentThread().getId();
            System.out.println(sb);
            sleep(500);
        }
    }

    public void run(){
        try {
            print();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
