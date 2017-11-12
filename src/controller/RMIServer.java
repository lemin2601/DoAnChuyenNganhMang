package controller;

import conf.LamportStatus;
import lib.*;
import bean.Message;
import bean.Task;
import bean.Ticket;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIServer extends UnicastRemoteObject implements InterfServer {


    private LamportManager lamportManager;      // qu?n lý thu?t toán lamport và x? lý các message
    private DataManager dataManager;
    private InterfServer interfServer;              // Interface RMI
    private String myIP;


    private int id;
    public RMIServer() throws RemoteException {
        super();
        lamportManager = new LamportManager(this);
        this.dataManager = new DataManager();
        this.myIP = Lib.getMyIp();
    }

    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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
        return lamportManager.addNewServer(server);

    }

    @Override
    public boolean RemoveServer(InterfServer server) throws RemoteException {
        System.out.println("Receive some request remove server");
        return lamportManager.removeServer(server);
    }

    @Override
    public int getID() throws RemoteException {
        return this.id;
    }

    @Override
    public boolean PushMessage(Message message) throws RemoteException {
        ///Xu ly mesage nhan duoc
        switch (message.getType()) {
            case REQ:
                //add vao queue
                this.lamportManager.addRequestAccess(message);
                break;
            case REP:
                this.lamportManager.addReplyAccess(message);
                break;
            case REL:
                this.lamportManager.addReleaseAccess(message);
                break;
        }
        return true;
    }

    // getter and setter
    public ArrayList<InterfServer> getServers() {
        return lamportManager.getListServers();
    }

    public void setServers(ArrayList<InterfServer> servers) {
        this.lamportManager.setListServers(servers);
    }

    public InterfServer getInterfServer() {
        return interfServer;
    }

    public void setInterfServer(InterfServer interfServer) {
        this.interfServer = interfServer;
    }
}
