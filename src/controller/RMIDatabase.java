package controller;

import bean.Task;
import lib.Lib;

import bean.Ticket;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIDatabase extends UnicastRemoteObject implements InterfDatabase {

    static ArrayList<InterfServer> servers;
    DataManager dataManager;
    int numOfClient = 0;
    public RMIDatabase() throws RemoteException {
        super();
        servers = new ArrayList<>();
        this.dataManager = new DataManager();

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
    public int UpdateTicket(Ticket ticket) throws RemoteException {
        return dataManager.updateTicket(ticket);
    }

    @Override
    public ArrayList<Ticket> getTicketLists() throws RemoteException {
        //tr? v? danh sách ch? tr?ng trong database
        return dataManager.getList();
    }

    @Override
    public ArrayList<InterfServer> getServerLists() throws RemoteException {
        //tr? v? dánh sách server có k?t nôi
        return servers;
    }

    @Override
    public boolean Register(InterfServer server) throws RemoteException {
        // try to add with all client
        for(InterfServer sv:servers){
            if(server == sv){
                return false;
            }
        }
        boolean checkAdd = true;
        for (InterfServer sv : servers) {
            checkAdd = sv.AddServer(server);
            if (!checkAdd) break;
        }
        //if add success to all client --> return
        return checkAdd && servers.add(server);

    }

    @Override
    public boolean UnRegis(InterfServer server) throws RemoteException {
        // try to remove with all client
        boolean checkRemove = true;
        for (InterfServer sv : servers) {
            checkRemove = sv.RemoveServer(server);
            if (!checkRemove) break;
        }
        //if remove success to all client --> return
        return checkRemove && servers.remove(server);

    }

    @Override
    public int getID() throws RemoteException {

        return  ++this.numOfClient;
    }

    @Override
    public void CheckConnection() throws RemoteException {
        //Empty method, just check to call function from client
    }


}
