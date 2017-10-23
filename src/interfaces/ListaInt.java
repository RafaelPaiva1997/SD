package interfaces;

import java.rmi.RemoteException;

/**
 * Created by Carlos on 21-10-2017.
 */
public interface ListaInt extends ModelInt {
    String printPessoas() throws RemoteException;
    String inLinePrint() throws RemoteException;
}
