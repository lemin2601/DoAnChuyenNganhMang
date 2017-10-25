package gui;

import controller.InterfServer;
import controller.RMIServer;
import controller.InterfDatabase;
import lib.Lib;
import conf.Configure;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * Created by Administrator on 10/25/2017.
 */
public class ServerRun {
    public static void main(String[] args) {

        String IpDatabase = "192.168.8.1";
        // code add server to database server
        while (true) {
            try {
                // Connect to remote object
                System.setProperty("java.rmi.server.hostname", Lib.getMyIp());

                InterfServer server = new RMIServer();

                Registry r = LocateRegistry.getRegistry(IpDatabase, Configure.PORT);

                InterfDatabase database = (InterfDatabase) r.lookup(Configure.DATABASE_SERVICE_NAME);
                database.Register(server);
                ((RMIServer) server).setServers(database.getServerLists());
                System.out.println("Worker's ID: " + ((RMIServer) server).getIP());

                //ki?m tra k?t n?i v?i server
                //code check connect to database and list<server> every second
                while (checkConnect(database)) {
                    try {
                        checkConnect(((RMIServer) server).getServers());
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }
            } catch (RemoteException | NotBoundException ex) {
            }
            //code reconnect to database server if disconnect
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

    //check connect to list<server>
    static  boolean checkConnect(ArrayList<InterfServer> serverList){
        try {
            for(InterfServer sv :serverList){
                System.out.print(sv.getIP() + " ");
            }
            System.out.println();
            return true;
        } catch (RemoteException e) {
            System.out.println("Disconnected by PC side");
        }
        return false;
    }
    //Check connect to database and list<server>
    static boolean checkConnect(InterfDatabase database) {
        try {
            database.CheckConnection();
            return true;
        } catch (RemoteException e) {
            System.out.println("\nDisconnected to Database");
        }
        return false;
    }
}
