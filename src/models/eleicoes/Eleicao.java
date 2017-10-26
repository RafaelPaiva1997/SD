package models.eleicoes;

import interfaces.eleicoes.EleicaoInt;
import models.Data;
import models.MesaDeVoto;
import models.Model;
import models.Voto;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

public abstract class Eleicao
        extends Model
        implements EleicaoInt, Serializable
{

    protected String titulo;
    protected String descricao;
    protected final Data dataInicio;
    protected final Data dataFim;
    protected final LinkedList<Voto> votos;
    protected final LinkedList<MesaDeVoto> mesasDeVoto;

    public Eleicao() throws RemoteException {
        super();
        dataInicio = new Data();
        dataFim = new Data();
        votos = new LinkedList<>();
        mesasDeVoto = new LinkedList<>();
        dataInicio.setId(201);
        dataFim.setId(202);
    }

    @Override
    public String getTitulo() {
        return titulo;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public LinkedList<Voto> getVotos() {
        return votos;
    }

    @Override
    public LinkedList<MesaDeVoto> getMesasDeVoto() {
        return mesasDeVoto;
    }

    @Override
    public long getDataInicio() {
        return safePut(dataInicio);
    }

    @Override
    public long getDataFim() {
        return safePut(dataFim);
    }

    @Override
    public boolean setTitulo(String titulo)throws RemoteException {
        boolean flag = true;
        if (lenghtMaior(titulo, 0) &&
                isAlpha(titulo))
            this.titulo = titulo ;
        else
            flag = false;
        return flag;
    }

    @Override
    public boolean setDescricao(String descricao) {
        boolean flag = true;
        if (lenghtMaior(descricao, 0) &&
                isAlpha(descricao))
            this.descricao = descricao ;
        else
            flag = false;
        return flag;
    }

    @Override
    public long newVoto() throws RemoteException {
        Voto e = new Voto();
        e.setEleicao(this);
        return safeAddPut(votos, e);
    }

    @Override
    public long newMesaDeVoto() throws RemoteException {
        MesaDeVoto e = new MesaDeVoto();
        e.add(this);
        return safeAddPut(mesasDeVoto, e);
    }

    @Override
    public long getVoto(int i) throws RemoteException {
        return safePut(votos.get(i));
    }

    @Override
    public long getMesaDeVoto(int i) throws RemoteException {
        return safePut(mesasDeVoto.get(i));
    }

    @Override
    public boolean deleteVoto(long id) throws RemoteException {
        return !(votos.remove() == null);
    }

    @Override
    public boolean deleteMesaDeVoto(long id) throws RemoteException {
        return mesasDeVoto.remove() != null;
    }

    @Override
    public boolean isConselhoGeral() throws RemoteException {
        return false;
    }

    @Override
    public boolean isDirecaoDepartamento() throws RemoteException {
        return false;
    }

    @Override
    public boolean isDirecaoFaculdade() throws RemoteException {
        return false;
    }

    @Override
    public boolean isNucleoEstudantes() throws RemoteException {
        return false;
    }

    @Override
    public String printVotos() throws RemoteException {
        return printLinkedList(votos);
    }

    @Override
    public String printMesasDeVoto() throws RemoteException {
        return printLinkedList(mesasDeVoto);
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "Título: " + titulo + " Data Início: " + dataInicio.print() + " Data Fim: " + dataFim.print();
    }
}
