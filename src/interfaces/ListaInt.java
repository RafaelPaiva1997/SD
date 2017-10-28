package interfaces;

import models.Voto;
import models.pessoas.Pessoa;

import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * Created by Carlos on 21-10-2017.
 */
public interface ListaInt extends ModelInt {
    String getNome() throws RemoteException;
    long getEleicao() throws RemoteException;
    LinkedList<Pessoa> getPessoas() throws RemoteException;
    LinkedList<Voto> getVotos() throws RemoteException;

    boolean setNome(String nome) throws RemoteException;

    boolean addPessoa(long id) throws RemoteException;
    long getPessoa(int i) throws RemoteException;
    boolean removePessoa(long id) throws RemoteException;

    long getVoto(int i) throws RemoteException;

    boolean hasReferences() throws RemoteException;

    String printVotos() throws RemoteException;
    String printPessoas() throws RemoteException;
    String inLinePrint() throws RemoteException;
    String printReferences() throws RemoteException;

}
