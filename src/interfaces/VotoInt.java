package interfaces;

import java.rmi.RemoteException;

/**
 * Created by Carlos on 21-10-2017.
 */
public interface VotoInt extends ModelInt {

    long getPessoaInt() throws RemoteException;
    long getEleicaoInt() throws RemoteException;
    long getListaInt() throws RemoteException;
    long getMesaDeVotoInt() throws RemoteException;
    long getDataInt() throws RemoteException;

    String print() throws RemoteException;
    String inLinePrint() throws RemoteException;

    void novo(long id1, long id2, long id3, long id4) throws RemoteException;
    void novoBranco(long id1, long id2, long id3) throws RemoteException;
    void novoNulo(long id1, long id2, long id3) throws RemoteException;
    void delete() throws RemoteException;
}
