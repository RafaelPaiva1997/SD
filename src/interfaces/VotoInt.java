package interfaces;

import java.rmi.RemoteException;

/**
 * Created by Carlos on 21-10-2017.
 */
public interface Voto extends ModelInt {
    String inLinePrint() throws RemoteException;
}
