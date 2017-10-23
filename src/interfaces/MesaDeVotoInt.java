package interfaces;

import models.eleicoes.Eleicao;
import models.pessoas.Pessoa;

import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * Created by Carlos on 21-10-2017.
 */
public interface MesaDeVotoInt extends ModelInt {

    long getDepartamento() throws RemoteException;
    LinkedList<Pessoa> getPessoas() throws RemoteException;
    LinkedList<Eleicao> getEleicoes() throws RemoteException;

    boolean add(Pessoa e) throws RemoteException;
    boolean add(Eleicao e) throws RemoteException;
    long getPessoa(int i) throws RemoteException;
    long getEleicao(int i) throws RemoteException;
    boolean deletePessoa(int i) throws RemoteException;
    boolean deleteEleicao(int i) throws RemoteException;

    String printPessoas() throws RemoteException;
    String printEleicoes() throws RemoteException;
    String inLinePrint() throws RemoteException;
}
