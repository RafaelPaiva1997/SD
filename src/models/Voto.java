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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Eleicao getEleicao() {
        return eleicao;
    }

    public Lista getLista() {
        return lista;
    }

    public MesaDeVoto getMesaDeVoto() {
        return mesaDeVoto;
    }

    public Data getData() {
        return data;
    }

    @Override
    public long getPessoaInt() throws RemoteException {
        return safePut(pessoa);
    }

    @Override
    public long getEleicaoInt() throws RemoteException {
        return safePut(eleicao);
    }

    @Override
    public long getListaInt() throws RemoteException {
        return safePut(lista);
    }

    @Override
    public long getMesaDeVotoInt() throws RemoteException {
        return safePut(mesaDeVoto);
    }

    @Override
    public long getDataInt() throws RemoteException {
        return safePut(data);
    }

    public void setEleicao(Eleicao eleicao) throws RemoteException {
        this.eleicao = eleicao;
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "VOTO - Nome: " + pessoa.getNome() + " Eleição: " + eleicao.getTitulo() + " Lista " + lista.getNome() + "\n";
    }
}
