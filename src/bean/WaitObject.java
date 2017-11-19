package bean;

class WaitObject {
    public synchronized void waitOK()   {
        try { wait(); } catch (InterruptedException e) {};
    }
    public synchronized void signalOK() {
        notify();
    }
}
