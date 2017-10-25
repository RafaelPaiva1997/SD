package database;

import models.Model;
import models.eleicoes.Eleicao;
import models.organizacoes.Departamento;
import models.organizacoes.Faculdade;
import models.pessoas.Pessoa;
import interfaces.DatabaseInt;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class Database
        extends Model
        implements DatabaseInt, Serializable
{

    private final LinkedList<Faculdade> faculdades;
    private final LinkedList<Eleicao> eleicoes;

    public Database() throws RemoteException {
        super();
        faculdades = new LinkedList<>();
        eleicoes = new LinkedList<>();
    }

    @Override
    public LinkedList<Faculdade> getFaculdades() {
        return faculdades;
    }

    @Override
    public LinkedList<Eleicao> getEleicoes() {
        return eleicoes;
    }

    @Override
    public long newFaculdade() throws RemoteException {
        return safeAddPut(faculdades, new Faculdade());
    }

    @Override
    public long getFaculdade(int i) throws RemoteException {
        return safePut(faculdades.get(i));
    }

    @Override
    public boolean add(Faculdade e) throws RemoteException {
        return faculdades.add(e);
    }

    @Override
    public boolean add(Eleicao e) throws RemoteException {
        return eleicoes.add(e);
    }

    @Override
    public boolean add(Departamento e, int p) throws RemoteException {
        return faculdades.get(p).getDepartamentos().add(e);
    }

    @Override
    public boolean add(Pessoa e, int p1, int p2) throws RemoteException {
        return faculdades.get(p1).getDepartamentos().get(p2).getPessoas().add(e);
    }

    @Override
    public boolean deleteFaculdade(int i) throws RemoteException {
        return faculdades.remove(i) != null;
    }

    @Override
    public boolean deleteEleicao(int i) throws RemoteException {
        return eleicoes.remove(i) != null;
    }

    @Override
    public String printFaculdades() throws RemoteException {
        return printLinkedList(faculdades);
    }

    @Override
    public String printEleicoes() throws RemoteException {
        return printLinkedList(eleicoes);
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "DATABASE";
    }
}
