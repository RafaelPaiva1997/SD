package models.eleicoes;

import interfaces.eleicoes.DirecaoDepartamentoInt;
import models.organizacoes.Departamento;

import java.io.Serializable;
import java.rmi.RemoteException;

public class DirecaoDepartamento
        extends Eleicao
        implements DirecaoDepartamentoInt, Serializable
{
    private Departamento departamento;

    public DirecaoDepartamento() throws RemoteException {
        super();
    }

    @Override
    public boolean isDirecaoDepartamento() throws RemoteException {
        return true;
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "ELEIÇÂO DIREÇÃO DEPARTAMENTO - " + super.inLinePrint() + "\n";
    }
}
