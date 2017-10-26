package models.eleicoes;

import interfaces.eleicoes.EleicaoInt;
import models.*;

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
    protected final LinkedList<Lista> listas;

    public Eleicao() throws RemoteException {
        super();
        dataInicio = new Data();
        dataFim = new Data();
        votos = new LinkedList<>();
        mesasDeVoto = new LinkedList<>();
        listas = new LinkedList<>();
    }

    @Override
    public String getTitulo() {
        return titulo;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    public Data getDataInicio() {
        return dataInicio;
    }

    public Data getDataFim() {
        return dataFim;
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
    public LinkedList<Lista> getListas() {
        return listas;
    }

    @Override
    public long getDataInicioInt() {
        return safePut(dataInicio);
    }

    @Override
    public long getDataFimInt() {
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
    public long newLista() throws RemoteException {
        Lista e = new Lista();
        e.setEleicao(this);
        return safeAddPut(listas, e);
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
    public long getLista(int i) throws RemoteException {
        return safePut(listas.get(i));
    }

    @Override
    public boolean deleteVoto(long id) throws RemoteException {
        return remove(votos, id);
    }

    @Override
    public boolean deleteMesaDeVoto(long id) throws RemoteException {
        return remove(mesasDeVoto, id);
    }

    @Override
    public boolean deleteLista(long id) throws RemoteException {
        return remove(listas, id);
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
    public String print() throws RemoteException {

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
    public String printListas() throws RemoteException {
        return printLinkedList(listas);
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "Título: " + titulo + " Data Início: " + dataInicio.print() + " Data Fim: " + dataFim.print();
    }
}
