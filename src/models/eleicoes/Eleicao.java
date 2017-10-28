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
    public String getTitulo() throws RemoteException {
        return titulo;
    }

    @Override
    public String getDescricao() throws RemoteException {
        return descricao;
    }

    public Data getDataInicio() throws RemoteException {
        return dataInicio;
    }

    public Data getDataFim() throws RemoteException {
        return dataFim;
    }

    @Override
    public LinkedList<Voto> getVotos() throws RemoteException {
        return votos;
    }

    @Override
    public LinkedList<MesaDeVoto> getMesasDeVoto() throws RemoteException {
        return mesasDeVoto;
    }

    @Override
    public LinkedList<Lista> getListas() throws RemoteException {
        return listas;
    }

    @Override
    public long getDataInicioInt() throws RemoteException {
        return safePut(dataInicio);
    }

    @Override
    public long getDataFimInt() throws RemoteException {
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
    public boolean setDescricao(String descricao) throws RemoteException {
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
    public boolean hasReferences() throws RemoteException {
        for (MesaDeVoto e : mesasDeVoto)
            if (e.hasReferences())
                return true;
        for (Lista e : listas)
            if (e.hasReferences())
                return true;
        return !votos.isEmpty();
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

    @Override
    public String print() throws RemoteException{
        return super.print() +
                "\nTítulo         - " + titulo +
                "\nDescrição      - " + descricao +
                "\nData de inicio - " + dataInicio.inLinePrint() +
                "\nData de fim    - " + dataFim.inLinePrint() +
                "\nMesas de voto  - " + mesasDeVoto +
                "\nListas         - " + listas
                ;
    }

    @Override
    public String printReferences() throws RemoteException {
        StringBuilder out = new StringBuilder();
        out.append("   ." + titulo + "\n");
        for (Voto e : votos)
            out.append(e.inLinePrint());
        for (MesaDeVoto e : mesasDeVoto)
            out.append(e.printReferences());
        for (Lista e : listas)
            out.append(e.printReferences());
        return out.toString();
    }

}
