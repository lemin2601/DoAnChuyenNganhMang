package tmp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements InterfClient {

    public RMIClient() throws RemoteException {
        super();
    }

    @Override
    public <T> T execute(Task<T> t) throws RemoteException {
        System.out.println("\n Have request ....");
        return t.execute();
    }

    @Override
    public String getIP() throws RemoteException {
        return Lib.getMyIp();
    }

    static boolean checkConnect(InterfServer server) {
        try {
            server.checkConnect();
            System.out.printf("\r%60s\r", "");
            System.out.print("Stilling connected");
            for (int i = 0; i < System.currentTimeMillis() / 1000 % 10; i++) {
                System.out.print(".");
            }
            return true;
        } catch (RemoteException e) {
            System.out.println("\nDisconnected by PC side");
        }
        return false;
    }

    public static void main(String[] args) {

        int port = 8850;
        String host = args[0];
//        String host ="192.168.2.1";
        while (true) {
            try {
                // Connect to remote object.
                String ip = lib.Lib.getMyIp();
                if(ip ==null) return;
                System.setProperty("java.rmi.server.hostname", Lib.getMyIp());
                InterfClient client = new RMIClient();
                Registry r = LocateRegistry.getRegistry(host, port);
                InterfServer server = (InterfServer) r.lookup("SERVER");
                server.register(client);
                System.out.println("Worker's ID: " + ((RMIClient) client).getIP());

                //kiểm tra kết nối với server           
                while (checkConnect(server)) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }
            } catch (RemoteException | NotBoundException ex) {
            }
            System.out.printf("\r%60s\r", "");
            System.out.print("Trying connect to server");
            for (int i = 0; i < System.currentTimeMillis() / 1000 % 10; i++) {
                System.out.print(".");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }

    }

}
