package interfaces.eleicoes;

import java.rmi.RemoteException;

/**
 * Created by Carlos on 21-10-2017.
 */
public interface DirecaoFaculdadeInt extends EleicaoInt {

    boolean setFaculdade(long id) throws RemoteException;

    boolean isDirecaoFaculdade() throws RemoteException;

    String print() throws RemoteException;
    String inLinePrint() throws RemoteException;
}
