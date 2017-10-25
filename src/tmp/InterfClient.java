package tmp;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfClient extends Remote {

    <T> T execute(Task<T> t) throws RemoteException;

    String getIP() throws RemoteException;
}
