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
        data = new Data();
    }

    @Override
    public long getPessoa() {
        return safePut(pessoa);
    }

    @Override
    public long getEleicao() {
        return safePut(eleicao);
    }

    @Override
    public long getLista() {
        return safePut(lista);
    }

    @Override
    public long getMesaDeVoto() {
        return safePut(mesaDeVoto);
    }

    @Override
    public long getData() {
        return safePut(data);
    }

    public void setEleicao(Eleicao eleicao) {
        this.eleicao = eleicao;
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "VOTO - Nome: " + pessoa.getNome() + " Eleição: " + eleicao.getTitulo() + " Lista " + lista.getNome() + "\n";
    }
}
