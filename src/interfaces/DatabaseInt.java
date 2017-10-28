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
    long newConselhoGeral() throws RemoteException;
    long newDirecaoDepartamento() throws RemoteException;
    long newDirecaoFaculdade() throws RemoteException;
    long newNucleoEstudantes() throws RemoteException;

    long getFaculdade(int i) throws RemoteException;
    long getEleicao(int i) throws RemoteException;

    boolean deleteFaculdade(long id) throws RemoteException;
    boolean deleteEleicao(long id) throws RemoteException;

    String printFaculdades() throws RemoteException;
    String printEleicoes() throws RemoteException;

    LinkedList<Long> searchPessoa(String query) throws RemoteException;
}
