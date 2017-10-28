package database;

import models.listas.Lista;
import models.MesaDeVoto;
import models.Model;
import models.Voto;
import models.eleicoes.*;
import models.organizacoes.Departamento;
import models.organizacoes.Faculdade;
import models.pessoas.Pessoa;
import interfaces.DatabaseInt;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class Database
        extends Model
        implements DatabaseInt, Serializable {

    private final LinkedList<Faculdade> faculdades;
    private final LinkedList<Eleicao> eleicoes;

    public Database(int id) throws RemoteException {
        super(id);
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

    public Model get(long id) {
        try {
            synchronized (faculdades) {
                for (Faculdade faculdade : faculdades) {
                    if (id == faculdade.getId())
                        return faculdade;
                    synchronized (faculdade.getDepartamentos()) {
                        for (Departamento departamento : faculdade.getDepartamentos()) {
                            if (id == departamento.getId())
                                return departamento;
                            synchronized (departamento.getPessoas()) {
                                for (Pessoa pessoa : departamento.getPessoas()) {
                                    if (id == pessoa.getId())
                                        return pessoa;
                                    else if (id == pessoa.getValidadeCC().getId())
                                        return pessoa.getValidadeCC();
                                    else if (id == pessoa.getDataNascimento().getId())
                                        return pessoa.getDataNascimento();
                                }
                            }
                        }
                    }
                }
            }
            synchronized (eleicoes) {
                for (Eleicao eleicao : eleicoes) {
                    if (id == eleicao.getId())
                        return eleicao;
                    else if (id == eleicao.getDataInicio().getId())
                        return eleicao.getDataInicio();
                    else if (id == eleicao.getDataFim().getId())
                        return eleicao.getDataFim();
                    synchronized (eleicao.getVotos()) {
                        for (Voto voto : eleicao.getVotos()) {
                            if (id == voto.getId())
                                return voto;
                            else if (id == voto.getData().getId())
                                return voto.getData();
                        }
                    }
                    synchronized (eleicao.getMesasDeVoto()) {
                        for (MesaDeVoto mesaDeVoto : eleicao.getMesasDeVoto())
                            if (id == mesaDeVoto.getId())
                                return mesaDeVoto;
                    }
                    synchronized (eleicao.getListas()) {
                        for (Lista lista : eleicao.getListas())
                            if (id == lista.getId())
                                return lista;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean avaibleID(long id) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public long newFaculdade() throws RemoteException {
        return safeAddPut(faculdades, new Faculdade());
    }

    @Override
    public long newConselhoGeral() throws RemoteException {
        return safeAddPut(eleicoes, new ConselhoGeral());
    }

    @Override
    public long newDirecaoDepartamento() throws RemoteException {
        return safeAddPut(eleicoes, new DirecaoDepartamento());
    }

    @Override
    public long newDirecaoFaculdade() throws RemoteException {
        return safeAddPut(eleicoes, new DirecaoFaculdade());
    }

    @Override
    public long newNucleoEstudantes() throws RemoteException {
        return safeAddPut(eleicoes, new NucleoEstudantes());
    }

    @Override
    public long newVoto() throws RemoteException {
        return safePut(new Voto());
    }

    @Override
    public long getFaculdade(int i) throws RemoteException {
        return safePut(faculdades.get(i));
    }

    @Override
    public long getEleicao(int i) throws RemoteException {
        return safePut(eleicoes.get(i));
    }

    @Override
    public boolean deleteFaculdade(long id) throws RemoteException {
        if (faculdades.get(find(faculdades, id)).hasReferences())
            faculdades.get(find(faculdades, id)).removeReferences();
        return remove(faculdades, id);
    }

    @Override
    public boolean deleteEleicao(long id) throws RemoteException {
        if (eleicoes.get(find(eleicoes, id)).hasReferences())
            eleicoes.get(find(eleicoes, id)).removeReferences();
        return remove(eleicoes, id);
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

    @Override
    public LinkedList<Long> searchPessoa(String query, long id) throws RemoteException {
        LinkedList<Long> out = new LinkedList<>();
        Eleicao eleicao = (Eleicao) get(id);
        String[] q = query.split("-");
        for (Faculdade faculdade : faculdades) {
            for (Departamento departamento : faculdade.getDepartamentos()) {
                for (Pessoa pessoa : departamento.getPessoas()) {
                    if (!eleicao.hasVoted(pessoa)) {
                        switch (q[0]) {
                            case "nome":
                                if (pessoa.getNome().contains(q[1]))
                                    out.add(pessoa.getId());
                                pessoa.put();
                                break;

                            case "username":
                                if (pessoa.getUsername().contains(q[1]))
                                    out.add(pessoa.getId());
                                pessoa.put();
                                break;

                            case "password":
                                if (pessoa.getPassword().contains(q[1]))
                                    out.add(pessoa.getId());
                                pessoa.put();
                                break;

                            case "nº telemóvel":
                                if (String.valueOf(pessoa.getTelemovel()).contains(q[1]))
                                    out.add(pessoa.getId());
                                pessoa.put();
                                break;

                            case "morada":
                                if (pessoa.getMorada().contains(q[1]))
                                    out.add(pessoa.getId());
                                pessoa.put();
                                break;

                            case "código postal":
                                if (pessoa.getCodigoPostal().contains(q[1]))
                                    out.add(pessoa.getId());
                                pessoa.put();
                                break;


                            case "localidade":
                                if (pessoa.getLocalidade().contains(q[1]))
                                    out.add(pessoa.getId());
                                pessoa.put();
                                break;

                            case "número c.c.":
                                if (String.valueOf(pessoa.getNumeroCC()).contains(q[1]))
                                    out.add(pessoa.getId());
                                pessoa.put();
                                break;
                        }
                    }
                }
            }
        }
        return out;
    }

    public LinkedList<Long> searchPessoa(String query) throws RemoteException {
        LinkedList<Long> out = new LinkedList<>();
        String[] q = query.split("-");
        for (Faculdade faculdade : faculdades) {
            for (Departamento departamento : faculdade.getDepartamentos()) {
                for (Pessoa pessoa : departamento.getPessoas()) {
                    switch (q[0]) {
                        case "nome":
                            if (pessoa.getNome().contains(q[1]))
                                out.add(pessoa.getId());
                            pessoa.put();
                            break;

                        case "username":
                            if (pessoa.getUsername().contains(q[1]))
                                out.add(pessoa.getId());
                            pessoa.put();
                            break;

                        case "password":
                            if (pessoa.getPassword().contains(q[1]))
                                out.add(pessoa.getId());
                            pessoa.put();
                            break;

                        case "nº telemóvel":
                            if (String.valueOf(pessoa.getTelemovel()).contains(q[1]))
                                out.add(pessoa.getId());
                            pessoa.put();
                            break;

                        case "morada":
                            if (pessoa.getMorada().contains(q[1]))
                                out.add(pessoa.getId());
                            pessoa.put();
                            break;

                        case "código postal":
                            if (pessoa.getCodigoPostal().contains(q[1]))
                                out.add(pessoa.getId());
                            pessoa.put();
                            break;


                        case "localidade":
                            if (pessoa.getLocalidade().contains(q[1]))
                                out.add(pessoa.getId());
                            pessoa.put();
                            break;

                        case "número c.c.":
                            if (String.valueOf(pessoa.getNumeroCC()).contains(q[1]))
                                out.add(pessoa.getId());
                            pessoa.put();
                            break;
                    }
                }
            }

        }
        return out;
    }
}
