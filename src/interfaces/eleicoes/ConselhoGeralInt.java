package interfaces.eleicoes;

import java.rmi.RemoteException;

/**
 * Created by Carlos on 21-10-2017.
 */
public interface ConselhoGeralInt extends EleicaoInt {

    boolean isConselhoGeral() throws RemoteException;
}
