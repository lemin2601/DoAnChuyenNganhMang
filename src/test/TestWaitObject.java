package test;

/**
 * Created by Administrator on 10/25/2017.
 */
public class TestWaitObject {
    public static void main(String[] args) throws InterruptedException {
        WaitObject waitObject = new WaitObject();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("befor wait A");
                waitObject.waitOK();
                System.out.println("continue");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("befor wait B");
                waitObject.signalOK();
                System.out.println("continue B");
            }
        }).start();

        Thread.sleep(3000);
        System.out.println("press ");
        waitObject.signalOK();

        System.out.println("done");
    }
}
