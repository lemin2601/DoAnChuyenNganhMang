package controller;

import lib.*;
import bean.Message;
import bean.Task;
import bean.Ticket;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIServer extends UnicastRemoteObject implements InterfServer {

    private ArrayList<InterfServer> servers;    // ch?a danh sách các server
    private LamportManager lamportManager;      // qu?n lý thu?t toán lamport và x? lý các message
    private InterfServer interfServer;              // Interface RMI
    private String myIP;


    public RMIServer() throws RemoteException {
        super();
        servers = new ArrayList<>();
        lamportManager = new LamportManager(this);
        this.myIP = Lib.getMyIp();
    }


    @Override
    public <T> T execute(Task<T> t) throws RemoteException {
        return null;
    }

    @Override
    public String getIP() throws RemoteException {
        return this.myIP;
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
        System.out.println("Receive some request remove server");
        return servers.remove(server);
    }

    @Override
    public boolean PushMessage(Message message) throws RemoteException {
        //X? lý message nh?n ???c
        this.lamportManager.receiveMessage(message);
        // xu ly message nhaan dduowjc

        // neu la REPLY
        notifyAll();
        return false;


    }

    boolean RegisterTicket(Ticket ticket) {
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

        return false;
    }

    boolean SendMessage(InterfServer server, Message message) {
        try {
            return server.PushMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }


    // getter and setter

    public ArrayList<InterfServer> getServers() {
        return servers;
    }

    public void setServers(ArrayList<InterfServer> servers) {
        this.servers = servers;
    }


    public InterfServer getInterfServer() {
        return interfServer;
    }

    public void setInterfServer(InterfServer interfServer) {
        this.interfServer = interfServer;
    }
}
