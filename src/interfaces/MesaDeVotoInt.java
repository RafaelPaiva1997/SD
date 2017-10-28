package interfaces;

import models.MesaDeVoto;
import models.Voto;
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
    LinkedList<Voto> getVotos() throws RemoteException;



    boolean addPessoa(long id) throws RemoteException;
    boolean addEleicao(long id) throws RemoteException;
    long getPessoa(int i) throws RemoteException;
    long getEleicao(int i) throws RemoteException;
    long getVoto(int i) throws RemoteException;
    boolean removePessoa(long id) throws RemoteException;
    boolean removeEleicao(long id) throws RemoteException;

    boolean hasReferences() throws RemoteException;

    String print() throws RemoteException;
    String printPessoas() throws RemoteException;
    String printEleicoes() throws RemoteException;
    String inLinePrint() throws RemoteException;
    String printVotos() throws RemoteException;
    String printReferences() throws RemoteException;

    boolean isWorking() throws RemoteException;
}
