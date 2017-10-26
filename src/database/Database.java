package database;

import models.Lista;
import models.MesaDeVoto;
import models.Model;
import models.Voto;
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

    public boolean avaibleID(long id) {
        synchronized (faculdades) {
            for (Faculdade faculdade : faculdades) {
                if (id == faculdade.getId())
                    return false;
                synchronized (faculdade.getDepartamentos()) {
                    for (Departamento departamento : faculdade.getDepartamentos()) {
                        if (id == departamento.getId())
                            return false;
                        synchronized (departamento.getPessoas()) {
                            for (Pessoa pessoa : departamento.getPessoas())
                                if (id == pessoa.getId() ||
                                        id == pessoa.getValidadeCC().getId() ||
                                        id == pessoa.getDataNascimento().getId())
                                    return false;
                        }
                    }
                }
            }
        }
        synchronized (eleicoes) {
            for (Eleicao eleicao : eleicoes) {
                if (id == eleicao.getId() ||
                        id == eleicao.getDataInicio().getId() ||
                        id == eleicao.getDataFim().getId())
                    return false;
                synchronized (eleicao.getVotos()) {
                    for (Voto voto : eleicao.getVotos())
                        if (id == voto.getId() ||
                                id == voto.getData().getId())
                            return false;
                }
                synchronized (eleicao.getMesasDeVoto()) {
                    for (MesaDeVoto mesaDeVoto : eleicao.getMesasDeVoto())
                        if (id == mesaDeVoto.getId())
                            return false;
                }
                synchronized (eleicao.getListas()) {
                    for (Lista lista : eleicao.getListas())
                        if (id == lista.getId())
                            return false;
                }
            }
        }
        return true;
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
    public boolean deleteFaculdade(long id) throws RemoteException {
        return faculdades.remove() != null;
    }

    @Override
    public boolean deleteEleicao(long id) throws RemoteException {
        return eleicoes.remove() != null;
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
