package interfaces.pessoas;

import java.rmi.RemoteException;

/**
 * Created by Carlos on 21-10-2017.
 */
public interface DocenteInt extends PessoaInt {
    String getCargo() throws RemoteException;

    boolean setCargo(String cargo) throws RemoteException;

    boolean isDocente() throws RemoteException;

    String print() throws RemoteException;
}
