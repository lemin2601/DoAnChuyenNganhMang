package controller;

import bean.Task;
import bean.Ticket;
import controller.InterfServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * Created by Administrator on 10/21/2017.
 */
public interface InterfDatabase extends Remote{

    <T> T execute(Task<T> t) throws RemoteException;
    String getIP() throws RemoteException;

    //booking ticket
    int UpdateTicket(Ticket ticket) throws  RemoteException;
    ArrayList<Ticket> getTicketLists() throws  RemoteException;

    //connect
    ArrayList<InterfServer> getServerLists() throws  RemoteException;
    boolean Register(InterfServer server) throws  RemoteException;
    boolean UnRegis(InterfServer server) throws  RemoteException;
    int getID() throws  RemoteException;
    void CheckConnection() throws RemoteException;

}
