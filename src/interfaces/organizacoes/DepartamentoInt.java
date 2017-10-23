package interfaces.organizacoes;

import interfaces.ModelInt;
import models.organizacoes.Faculdade;
import models.pessoas.Pessoa;

import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * Created by Carlos on 19-10-2017.
 */
public interface DepartamentoInt extends ModelInt {
    String getNome() throws RemoteException;
    Faculdade getFaculdade() throws RemoteException;
    LinkedList<Pessoa> getPessoas() throws RemoteException;

    boolean setNome(String nome) throws RemoteException;
    boolean delete(Pessoa pessoa) throws RemoteException;
    long newAluno(long id) throws RemoteException;
    long newDocente(long id) throws RemoteException;
    long newFuncionario(long id) throws RemoteException;
    long getPessoa(int i) throws RemoteException;

    String printPessoas() throws RemoteException;
    String inLinePrint() throws RemoteException;
}
