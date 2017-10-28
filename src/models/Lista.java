package models;

import interfaces.ListaInt;
import models.eleicoes.Eleicao;
import models.pessoas.Pessoa;
import rmi.RMIServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class Lista
        extends Model
        implements ListaInt, Serializable
{
    private String nome;
    private Eleicao eleicao;
    private final LinkedList<Pessoa> pessoas;

    public Lista() throws RemoteException {
        super();
        pessoas = new LinkedList<>();
    }

    @Override
    public String getNome() throws RemoteException {
        return nome;
    }

    @Override
    public long getEleicao() throws RemoteException {
        return safePut(eleicao);
    }

    @Override
    public LinkedList<Pessoa> getPessoas() throws RemoteException {
        return pessoas;
    }

    @Override
    public boolean setNome(String nome) throws RemoteException {
        boolean flag = true;
        if (lenghtMaior(nome, 0) &&
                isAlpha(nome))
            this.nome = nome;
        else
            flag = false;
        return flag;
    }

    public void setEleicao(Eleicao eleicao) {
        this.eleicao = eleicao;
    }

    @Override
    public boolean addPessoa(long id) throws RemoteException {
        Pessoa e = (Pessoa) RMIServer.database.get(id);
        return add(pessoas, e) && add(e.getListas(), this);
    }

    @Override
    public long getPessoa(int i) throws RemoteException {
        return safePut(pessoas.get(i));
    }

    @Override
    public boolean removePessoa(long id) throws RemoteException {
        return remove(pessoas, id) && remove(((Pessoa) RMIServer.database.get(id)).getListas(), this.id);
    }

    @Override
    public String printPessoas() throws RemoteException {
        return printLinkedList(pessoas);
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "LISTA - Nome: " + nome + " Eleição: " + eleicao.getTitulo() + "\n";
    }
}
