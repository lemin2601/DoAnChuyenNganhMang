package gui;

import controller.InterfDatabase;
import controller.RMIDatabase;
import lib.Lib;
import conf.Configure;
import controller.InterfServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Created by Administrator on 10/25/2017.
 */
public class DatabaseRun {
    public static void main(String[] args) {
        try {

            String ip = Lib.getMyIp();
            if (ip == null) System.exit(0);
            System.setProperty("java.rmi.server.hostname", Lib.getMyIp());
            //??ng ký d?ch v?
            Registry r = LocateRegistry.createRegistry(Configure.PORT);
            InterfDatabase database;
            database = new RMIDatabase();
            r.rebind(Configure.DATABASE_SERVICE_NAME, database);

            System.out.println("serverWork at:" + Lib.getMyIp() + " port:" + Configure.PORT + " with name: " + Configure.DATABASE_SERVICE_NAME);


//            Registry r_forserver = LocateRegistry.createRegistry(8808);
//            InterfServer server_forserver;
//            server_forserver = new RMIServer();
//            r_forserver.rebind("SERVER_FOR_server", server_forserver);
//            System.out.println("serverCalculator at:" + Lib.getMyIp() + " port: 8808");

            //ki?m tra k?t n?i v?i server
            checkConnection(database.getServerLists());
        } catch (RemoteException ex) {
            System.out.println("system encrupt");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private static void checkConnection(ArrayList<InterfServer> servers) {

        while (true)
        {
            System.out.printf("\r%60s\r", "");
            System.out.print("Connected IP: ");
            try {
                for (InterfServer server : servers) {
                    boolean isConnected = true;
                    try {
                        System.out.print(server.getIP() + " | ");
                    } catch (RemoteException ex) {
                        isConnected = false;
                    }
                    if (!isConnected) {
                        System.out.println("\nRemove server some server");
                        for (InterfServer sv : servers) {
                            if (sv != server) {
                                try {
                                    sv.RemoveServer(server);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        servers.remove(server);
                    }
                }
            } catch (ConcurrentModificationException ex) {


            }
            for (int i = 0; i < System.currentTimeMillis() / 1000 % 5; i++) {
                System.out.print(".");
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
