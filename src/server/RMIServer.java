package server;

import database.InterfDatabase;
import lib.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Queue;

public class RMIServer extends UnicastRemoteObject implements InterfServer {

    ArrayList<InterfServer> servers;
//    Queue<Message> messageQueue ;
    public RMIServer() throws RemoteException {
        super();
        servers = new ArrayList<>();
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
    public void CheckConnection() throws RemoteException {

    }

    @Override
    public boolean AddServer(InterfServer server) throws RemoteException {
        return servers.add(server);
    }

    @Override
    public boolean RemoveServer(InterfServer server) throws RemoteException {
        return servers.remove(server);
    }

    @Override
    public boolean PushMessage(Message message) throws RemoteException {
        //??y message nh?n ???c vào queue;

        // xu ly message nhaan dduowjc

        // neu la REPLY
        notifyAll();
        return false;


    }

    boolean RegisterTicket(Ticket ticket){
        //??ng ký vé m?i

        //1. g?i mess REQ ??n t?t c? server còn l?i



        //2. g?i message


        //3. ch? nh?n h?t reply
//        while (true){
//            // khi chuwa nhanaj xong
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }

        return  false;
    }

    boolean SendMessage(InterfServer server, Message message){
        try {
            return server.PushMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {

        String IpDatabase = "192.168.8.1";

        while (true) {
            try {
                // Connect to remote object
                System.setProperty("java.rmi.server.hostname", Lib.getMyIp());

                InterfServer server = new RMIServer();

                Registry r = LocateRegistry.getRegistry(IpDatabase, Configure.PORT);

                InterfDatabase database = (InterfDatabase) r.lookup(Configure.DATABASE_SERVICE_NAME);
                database.Register(server);

                System.out.println("Worker's ID: " + ((RMIServer) server).getIP());

                //ki?m tra k?t n?i v?i server
                while (checkConnect(database)) {
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

    static boolean checkConnect(InterfDatabase database) {
        try {
            database.CheckConnection();
            System.out.printf("\r%60s\r", "");
            System.out.print("Stilling connected");
            for (int i = 0; i < System.currentTimeMillis() / 1000 % 10; i++) {
                System.out.print(".");
            }
            ArrayList<InterfServer> serverLists = database.getServerLists();
            for(InterfServer sv :serverLists){
                System.out.print(sv.getIP() + " ");
            }
            return true;
        } catch (RemoteException e) {
            System.out.println("\nDisconnected by PC side");
        }
        return false;
    }
}
