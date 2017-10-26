package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Carlos on 20-10-2017.
 */
public interface ModelInt extends Remote {
    long getId() throws RemoteException;

    String print() throws RemoteException;
    String inLinePrint() throws RemoteException;
}
