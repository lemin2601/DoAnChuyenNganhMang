package server;

import lib.Message;
import lib.Task;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Administrator on 10/21/2017.
 */
public interface InterfServer extends Remote {
    <T> T execute(Task<T> t) throws RemoteException;

    String getIP() throws RemoteException;
    void CheckConnection() throws  RemoteException;
    boolean AddServer(InterfServer server) throws  RemoteException;
    boolean RemoveServer(InterfServer server) throws  RemoteException;


    //booking ticket
    boolean PushMessage(Message message)throws RemoteException;
}