package models.eleicoes;

import interfaces.eleicoes.ConselhoGeralInt;
import models.organizacoes.Departamento;

import java.io.Serializable;
import java.rmi.RemoteException;

public class ConselhoGeral
        extends Eleicao
        implements ConselhoGeralInt, Serializable

{
    public ConselhoGeral() throws RemoteException {
        super();
    }

    @Override
    public boolean isConselhoGeral() throws RemoteException {
        return true;
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "ELEIÇÃO CONSELHO GERAL - " + super.inLinePrint() + "\n";
    }
}
