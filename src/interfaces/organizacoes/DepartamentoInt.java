package interfaces.organizacoes;

import interfaces.ModelInt;
import models.MesaDeVoto;
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

    long newAluno() throws RemoteException;
    long newDocente() throws RemoteException;
    long newFuncionario() throws RemoteException;
    long getPessoa(int i) throws RemoteException;
    long getMesaDeVotoInt() throws RemoteException;
    boolean deletePessoa(long id) throws RemoteException;

    boolean hasReferences() throws RemoteException;

    String printPessoas() throws RemoteException;
    String print() throws RemoteException;
    String inLinePrint() throws RemoteException;
    String printReferences() throws RemoteException;
}
