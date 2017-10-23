package models.eleicoes;

import interfaces.eleicoes.DirecaoFaculdadeInt;
import models.organizacoes.Faculdade;

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
    public String inLinePrint() throws RemoteException {
        return "\nELEIÇÂO DIREÇÂO FACULDADE" + super.inLinePrint();
    }
}
