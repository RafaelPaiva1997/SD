package interfaces.pessoas;

import java.rmi.RemoteException;

/**
 * Created by Carlos on 21-10-2017.
 */
public interface FuncionarioInt extends PessoaInt {
    String getFuncao() throws RemoteException;

    boolean setFuncao(String funcao) throws RemoteException;

    boolean isFuncionario() throws RemoteException;

    String print() throws RemoteException;

    String inLinePrint() throws RemoteException;
}
