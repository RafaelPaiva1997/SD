package models;

import models.eleicoes.Eleicao;
import models.pessoas.Pessoa;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Voto extends Model implements Serializable {
    private Pessoa pessoa;
    private Eleicao eleicao;
    private Lista lista;
    private MesaDeVoto mesaDeVoto;
    private Data data;

    public Voto() throws RemoteException {
        super();
    }

    public void setEleicao(Eleicao eleicao) {
        this.eleicao = eleicao;
    }
}
