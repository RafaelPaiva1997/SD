package models.eleicoes;

import interfaces.eleicoes.DirecaoFaculdadeInt;
import models.organizacoes.Faculdade;
import rmi.RMIServer;

import java.io.Serializable;
import java.rmi.RemoteException;

public class DirecaoFaculdade
        extends Eleicao
        implements DirecaoFaculdadeInt, Serializable
{
    private Faculdade faculdade;

    public DirecaoFaculdade() throws RemoteException {
        super();
    }

    @Override
    public boolean isDirecaoFaculdade() throws RemoteException {
        return true;
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
                "\nFaculdade      - " + faculdade
                ;
    }
}
