package models;

import models.eleicoes.Eleicao;
import models.organizacoes.Departamento;
import models.pessoas.Pessoa;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class MesaDeVoto extends Model implements Serializable {
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
}
