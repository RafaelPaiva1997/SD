package models.eleicoes;

import interfaces.eleicoes.NucleoEstudantesInt;
import models.organizacoes.Departamento;

import java.io.Serializable;
import java.rmi.RemoteException;

public class NucleoEstudantes
        extends Eleicao
        implements NucleoEstudantesInt, Serializable
{
    private Departamento departamento;

    public NucleoEstudantes() throws RemoteException {
        super();
    }

    @Override
    public boolean isNucleoEstudantes() throws RemoteException {
        return true;
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "ELEIÇÃO NÚCLEO ESTUDANTES - " + super.inLinePrint() + "\n";
    }
}

