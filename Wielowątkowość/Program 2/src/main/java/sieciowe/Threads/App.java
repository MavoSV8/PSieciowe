package sieciowe.Threads;

import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        ArrayList<MyThread> myThreadArrayList = new ArrayList<>();

        for(int i=0;i< 10 ;i++){
            myThreadArrayList.add(new MyThread());
            System.out.println(myThreadArrayList.get(i).isAlive());
        }

        String inputString = "";
        Scanner input = new Scanner(System.in);
        inputString = input.nextLine();
        if(inputString.equals("run")){
            for (MyThread i: myThreadArrayList) {
                i.start();
            }
        }


    }
}
