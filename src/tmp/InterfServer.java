package tmp;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfServer extends Remote {

    Matrix execute(Matrix a, Matrix b, String opera) throws RemoteException;

    String execute() throws RemoteException;

    boolean register(InterfClient client) throws RemoteException;

    boolean unRegister(InterfClient client) throws RemoteException;

    void checkConnect() throws RemoteException;
}
