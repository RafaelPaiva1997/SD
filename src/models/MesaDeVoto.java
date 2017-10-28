package models;

import interfaces.MesaDeVotoInt;
import models.eleicoes.Eleicao;
import models.organizacoes.Departamento;
import models.pessoas.Pessoa;
import rmi.RMIServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class MesaDeVoto
        extends Model
        implements MesaDeVotoInt, Serializable
{
    private Departamento departamento;
    private final LinkedList<Pessoa> pessoas;
    private final LinkedList<Eleicao> eleicoes;

    public MesaDeVoto() throws RemoteException {
        super();
        pessoas = new LinkedList<>();
        eleicoes = new LinkedList<>();
    }

    @Override
    public long getDepartamento() {
        return safePut(departamento);
    }

    @Override
    public LinkedList<Pessoa> getPessoas() {
        return pessoas;
    }

    @Override
    public LinkedList<Eleicao> getEleicoes() {
        return eleicoes;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public boolean addPessoa(long id) throws RemoteException {
        Pessoa e = (Pessoa) RMIServer.database.get(id);
        return add(pessoas, e) && add(e.getMesasDeVoto(), this);
    }

    @Override
    public boolean addEleicao(long id) throws RemoteException {
        Eleicao e = (Eleicao) RMIServer.database.get(id);
        return add(eleicoes, e) && add(e.getMesasDeVoto(), this);
    }

    @Override
    public long getPessoa(int i) throws RemoteException {
        return safePut(pessoas.get(i));
    }

    @Override
    public long getEleicao(int i) throws RemoteException {
        return safePut(eleicoes.get(i));
    }

    @Override
    public boolean removePessoa(long id) throws RemoteException {
        return remove(pessoas, id) && remove(((Pessoa) RMIServer.database.get(id)).getListas(), this.id);
    }

    @Override
    public boolean removeEleicao(long id) throws RemoteException {
        return remove(eleicoes, id) && remove(((Eleicao) RMIServer.database.get(id)).getListas(), this.id);
    }

    @Override
    public boolean hasReferences() throws RemoteException {
        return !eleicoes.isEmpty() && !pessoas.isEmpty();
    }


    @Override
    public String printPessoas() throws RemoteException {
        return printLinkedList(pessoas);
    }

    @Override
    public String printEleicoes() throws RemoteException {
        return printLinkedList(eleicoes);
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "MESA DE VOTO - Departamento: " + departamento.getNome() + " Faculdade: " + departamento.getFaculdade().getNome() + "\n";
    }

    @Override
    public boolean isWorking() throws RemoteException {
        return !eleicoes.isEmpty();
    }

    @Override
    public String printReferences() throws RemoteException {
        StringBuilder out = new StringBuilder();
        out.append("   ." + "realizada no departamento" + departamento + "\n");
        for (Pessoa e : pessoas)
            out.append(e.inLinePrint());
        return out.toString();
    }
}
