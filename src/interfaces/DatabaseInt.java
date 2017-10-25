package interfaces;

import models.eleicoes.Eleicao;
import models.organizacoes.Departamento;
import models.organizacoes.Faculdade;
import models.pessoas.Pessoa;

import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * Created by Carlos on 19-10-2017.
 */
public interface DatabaseInt extends ModelInt {

    LinkedList<Faculdade> getFaculdades() throws RemoteException;
    LinkedList<Eleicao> getEleicoes() throws RemoteException;

    long newFaculdade() throws RemoteException;
    long getFaculdade(int i) throws RemoteException;

    boolean add(Faculdade e) throws RemoteException;
    boolean add(Eleicao e) throws RemoteException;

    boolean add(Departamento e, int p) throws RemoteException;
    boolean add(Pessoa e, int p1, int p2) throws RemoteException;

    boolean deleteFaculdade(int i) throws RemoteException;
    boolean deleteEleicao(int i) throws RemoteException;

    String printFaculdades() throws RemoteException;
    String printEleicoes() throws RemoteException;
}
