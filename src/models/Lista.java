package models;

import models.eleicoes.Eleicao;
import models.pessoas.Pessoa;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class Lista extends Model implements Serializable {
    private String nome;
    private Eleicao eleicao;
    private LinkedList<Pessoa> pessoas;

    public Lista() throws RemoteException {
        super();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
