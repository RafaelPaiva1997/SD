package models;

import interfaces.VotoInt;
import models.eleicoes.Eleicao;
import models.listas.Lista;
import models.pessoas.Pessoa;
import rmi.RMIServer;

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
    public String print() throws RemoteException {
        return super.print() +
                "\nPessoa  - " + pessoa  +
                "\nEleição - " + eleicao +
                "\nLista   - " + lista +
                "\nLocal   - " + mesaDeVoto.getDepartamento().getNome() +
                "\nData    - " + data.inLinePrint();
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "VOTO - Nome: " + pessoa.getNome() + " Eleição: " + eleicao.getTitulo() + " Lista " + lista.getNome() + "\n";
    }

    @Override
    public void novo(long id1, long id2, long id3, long id4) throws RemoteException {
        pessoa = (Pessoa) RMIServer.database.get(id1);
        pessoa.getVotos().add(this);
        eleicao = (Eleicao) RMIServer.database.get(id2);
        eleicao.getVotos().add(this);
        lista = (Lista) RMIServer.database.get(id3);
        lista.getVotos().add(this);
        mesaDeVoto = (MesaDeVoto) RMIServer.database.get(id4);
        mesaDeVoto.getVotos().add(this);
    }

    @Override
    public void novoBranco(long id1, long id2, long id3) throws RemoteException {
        pessoa = (Pessoa) RMIServer.database.get(id1);
        pessoa.getVotos().add(this);
        eleicao = (Eleicao) RMIServer.database.get(id2);
        eleicao.getVotos().add(this);
        lista = new Lista();
        lista.setNome("Branco");
        mesaDeVoto = (MesaDeVoto) RMIServer.database.get(id3);
        mesaDeVoto.getVotos().add(this);
    }

    @Override
    public void novoNulo(long id1, long id2, long id3) throws RemoteException {
        pessoa = (Pessoa) RMIServer.database.get(id1);
        pessoa.getVotos().add(this);
        eleicao = (Eleicao) RMIServer.database.get(id2);
        eleicao.getVotos().add(this);
        lista = new Lista();
        lista.setNome("Nulo");
        mesaDeVoto = (MesaDeVoto) RMIServer.database.get(id3);
        mesaDeVoto.getVotos().add(this);
    }

    @Override
    public void delete() {
        try {
            pessoa.getVotos().remove(this);
            eleicao.getVotos().remove(this);
            lista.getVotos().remove(this);
            mesaDeVoto.getVotos().remove(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
