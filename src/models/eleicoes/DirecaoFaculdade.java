package models.eleicoes;

import interfaces.eleicoes.DirecaoFaculdadeInt;
import models.listas.Lista;
import models.organizacoes.Faculdade;
import models.pessoas.Pessoa;
import rmi.RMIServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class DirecaoFaculdade
        extends Eleicao
        implements DirecaoFaculdadeInt, Serializable
{
    private Faculdade faculdade;

    public DirecaoFaculdade() throws RemoteException {
        super();
    }

    @Override
    public LinkedList<Lista> getListas(Pessoa p) {
        LinkedList<Lista> out = new LinkedList<>();
        for (Lista l : listas) {
            try {
                if (p.isDocente() && l.isListaDocentes() &&
                        p.getDepartamento().getFaculdade().getId() == faculdade.getId())
                    out.add(l);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return out;
    }

    public Faculdade getFaculdade() {
        return faculdade;
    }

    @Override
    public boolean isDirecaoFaculdade() throws RemoteException {
        return true;
    }

    @Override
    public boolean canJoin(Lista e) {
        return e.isListaDocentes();
    }

    @Override
    public boolean setFaculdade(long id) throws RemoteException {
        boolean flag = true;
        faculdade = (Faculdade) RMIServer.database.get(id);
        if (faculdade == null)
            flag = false;
        return flag;
    }


    @Override
    public String inLinePrint() throws RemoteException {
        return "ELEIÇÂO DIREÇÂO FACULDADE - " + super.inLinePrint() + "\n";
    }

    @Override
    public String print() throws RemoteException{
        return super.print() +
                "\nFaculdade      - " + faculdade.getNome();
    }
}
