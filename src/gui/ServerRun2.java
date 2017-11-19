package gui;

import conf.TicketStatus;
import controller.*;
import lib.Lib;
import bean.Ticket;
import conf.Configure;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by Administrator on 10/25/2017.
 */
public class ServerRun2 {
    public static void main(String[] args) throws RemoteException {
        //code connect to database server
        String IpDatabase = "192.168.8.1";
        InterfDatabase database = null;
        InterfServer server = null;
        LamportManager lamportManager = null;
        // code add server to database server
        try {
            // Connect to remote object
            System.setProperty("java.rmi.server.hostname", Lib.getMyIp());
            server = new RMIServer();
            Registry r = LocateRegistry.getRegistry(IpDatabase, Configure.PORT);
            database = (InterfDatabase) r.lookup(Configure.DATABASE_SERVICE_NAME);
             database.Register(server);
            ((RMIServer) server).setServers(database.getServerLists());
            System.out.println("Worker's ID: " + ((RMIServer) server).getIP());

            lamportManager = new LamportManager( server);

            boolean change = false;
            while(true)
            {
                System.out.println("run ...");
                Ticket ticket = new Ticket(2, (change ? TicketStatus.FREE:TicketStatus.BOUGHT));
                Thread thread = new Thread(new ThreadAccessData(database,lamportManager,ticket));
                thread.start();
                change = ! change;
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }

        System.out.println(database.getTicketLists().toString());

        //code disconnect to database
//        database.UnRegis(server);
        System.exit(0);
    }
}
