package interfaces.organizacoes;

import interfaces.ModelInt;
import models.organizacoes.Departamento;

import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * Created by Carlos on 19-10-2017.
 */
public interface FaculdadeInt extends ModelInt {
    String getNome() throws RemoteException;
    LinkedList<Departamento> getDepartamentos() throws RemoteException;

    boolean setNome(String nome) throws RemoteException;

    long newDepartamento(long id) throws RemoteException;
    long getDepartamento(int i) throws RemoteException;

    String printDepartamentos() throws RemoteException;
    String inLinePrint() throws RemoteException;
}
