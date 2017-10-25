package tmp;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static void main(String[] args) {
        Matrix a = Lib.RandomMatrix(20, 20);
        Matrix b = Lib.RandomMatrix(20, 20);
        System.out.println(a.toString());
        System.out.println(b.toString());
        try {
            String host = "192.168.2.1";
            int port = 8808;
            // Connect to remote object
            System.setProperty("java.rmi.server.hostname", Lib.getMyIp());
            Registry r = LocateRegistry.getRegistry(host, port);
            InterfServer server = (InterfServer) r.lookup("SERVER_FOR_CLIENT");
            System.out.println(server.execute());
            System.out.println(server.execute(a,b,"+").toString());
        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }
    }
}
