package interfaces;

import java.rmi.RemoteException;

/**
 * Created by Carlos on 21-10-2017.
 */
public interface VotoInt extends ModelInt {

    long getPessoa() throws RemoteException;
    long getEleicao() throws RemoteException;
    long getLista() throws RemoteException;
    long getMesaDeVoto() throws RemoteException;
    long getData() throws RemoteException;

    String inLinePrint() throws RemoteException;
}
