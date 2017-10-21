package test;

import lib.*;

/**
 * Created by Administrator on 10/21/2017.
 */
public class TestThread {
    public static void main(String[] args) {

        Message message = new Message("\"process it");
        Thread a;
        a = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("THread A");
                synchronized (message)
                {
                    try {
                        message.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("THread A2");

                }

            }
        });
        a.start();

        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (a){
                    try {
                        a.wait();
                        message.notify();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                synchronized (a){
                    try {
                        a.notify();
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                    notifyAll();
                    System.out.println("THread B2");
                }

            }
        });
        B.start();





    }
}
