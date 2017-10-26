package interfaces.eleicoes;

import java.rmi.RemoteException;

/**
 * Created by Carlos on 21-10-2017.
 */
public interface DirecaoDepartamentoInt extends EleicaoInt {

    boolean setDepartamento(long id) throws RemoteException;

    boolean isDirecaoDepartamento() throws RemoteException;

    String print() throws RemoteException;
    String inLinePrint() throws RemoteException;
}
