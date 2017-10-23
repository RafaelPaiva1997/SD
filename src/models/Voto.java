package models;

import interfaces.VotoInt;
import models.eleicoes.Eleicao;
import models.pessoas.Pessoa;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Voto
        extends Model
        implements VotoInt, Serializable
{
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

    @Override
    public String inLinePrint() throws RemoteException {
        return "\nVOTO - Nome: " + pessoa.getNome() + " Eleição: " + eleicao.getTitulo() + " Lista " + lista.getNome();
    }
}
