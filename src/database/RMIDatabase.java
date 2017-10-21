package database;

import lib.Configure;
import lib.Task;
import lib.Lib;

import lib.Ticket;
import server.InterfServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Created by Administrator on 10/21/2017.
 */
public class RMIDatabase extends UnicastRemoteObject implements InterfDatabase {

    static ArrayList<InterfServer> servers;

    public RMIDatabase() throws RemoteException {
        super();
    }
    @Override
    public <T> T execute(Task<T> t) throws RemoteException {
        return null;
    }

    @Override
    public String getIP() throws RemoteException {
        return Lib.getMyIp();
    }

    @Override
    public int BookingTicket(Ticket ticket) throws RemoteException {
        // ??t vé;
        return Configure.BOOKING_FAILED;
    }

    @Override
    public ArrayList<Ticket> getTicketLists() throws RemoteException {
        //tr? v? danh sách ch? tr?ng trong database
        return null;
    }

    @Override
    public ArrayList<InterfServer> getServerLists() throws RemoteException {
        //tr? v? dánh sách server có k?t nôi
        return null;
    }

    @Override
    public boolean Register(InterfServer server) throws RemoteException {
        return false;
    }

    @Override
    public boolean UnRegis(InterfServer server) throws RemoteException {
        return false;
    }

    @Override
    public void CheckConnection() throws RemoteException {

    }


    public static void main(String[] args) {
        try {
            servers = new ArrayList<>();
            System.setProperty("java.rmi.server.hostname", Lib.getMyIp());
            //??ng ký d?ch v?
            Registry r = LocateRegistry.createRegistry(Configure.PORT);
            InterfDatabase database;
            database = new RMIDatabase();
            r.rebind("SERVER", database);
            System.out.println("serverWork at:" + Lib.getMyIp() + " port:" + Configure.PORT);

//            Registry r_forserver = LocateRegistry.createRegistry(8808);
//            InterfServer server_forserver;
//            server_forserver = new RMIServer();
//            r_forserver.rebind("SERVER_FOR_server", server_forserver);
//            System.out.println("serverCalculator at:" + Lib.getMyIp() + " port: 8808");

            //ki?m tra k?t n?i v?i server 
            checkConnection();
        } catch (RemoteException ex) {
            System.out.println("system encrupt");
            ex.printStackTrace();
            System.exit(1);
        }
    }
    private static void checkConnection() {
        while (true) {
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
                        servers.remove(server);
                    }
                }
            } catch (ConcurrentModificationException ex) {

            }
            for (int i = 0; i < System.currentTimeMillis() / 1000 % 5; i++) {
                System.out.print(".");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {

            }
        }
    }

}
