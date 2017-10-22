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
    public long newFaculdade(long id) throws RemoteException {
        Faculdade e = new Faculdade();
        e.setId(id);
        synchronized (faculdades) {
            faculdades.add(e);
            faculdades.getLast().put();
        }
        return e.getId();
    }

    @Override
    public long getFaculdade(int i) throws RemoteException {
        long out = -1;
        synchronized (faculdades) {
            if (faculdades.get(i).put())
                out = faculdades.get(i).getId();
        }
        return out;
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
}
