package interfaces.eleicoes;

import java.rmi.RemoteException;

/**
 * Created by Carlos on 21-10-2017.
 */
public interface DirecaoDepartamentoInt extends EleicaoInt {

    boolean isDirecaoDepartamento() throws RemoteException;

    String inLinePrint() throws RemoteException;
}
