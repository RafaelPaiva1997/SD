package interfaces.pessoas;

import java.rmi.RemoteException;

/**
 * Created by Carlos on 21-10-2017.
 */
public interface AlunoInt extends PessoaInt {

    long getNumeroAluno() throws RemoteException;
    String getCurso() throws RemoteException;

    boolean setCurso(String curso) throws RemoteException;
    boolean setNumeroAluno(String numeroAluno) throws RemoteException;

    boolean isAluno() throws RemoteException;

    String print() throws RemoteException;

    String inLinePrint() throws RemoteException;
}
