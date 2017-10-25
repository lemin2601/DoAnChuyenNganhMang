package gui;

import controller.InterfServer;
import controller.RMIServer;
import controller.InterfDatabase;
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
        } catch (RemoteException | NotBoundException ex) {
        }

        //code call getListTicket();
        for(Ticket ticket : database.getTicketLists()){
            System.out.println(ticket.toString());
        }

        //code disconnect to database
        database.UnRegis(server);
        System.exit(0);
    }
}
