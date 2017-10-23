package models;

import interfaces.MesaDeVotoInt;
import models.eleicoes.Eleicao;
import models.organizacoes.Departamento;
import models.pessoas.Pessoa;

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

    public boolean add(Eleicao e) {
        return eleicoes.add(e);
    }

    public boolean add(Pessoa e) {
        return pessoas.add(e);
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
        return "\nMESA DE VOTO - Departamento: " + departamento.getNome() + " Faculdade: " + departamento.getFaculdade().getNome();
    }
}
