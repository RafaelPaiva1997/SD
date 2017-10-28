package models.eleicoes;

import interfaces.eleicoes.DirecaoDepartamentoInt;
import models.organizacoes.Departamento;
import rmi.RMIServer;

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

    public Departamento getDepartamento() {
        return departamento;
    }

    @Override
    public boolean setDepartamento(long id) throws RemoteException {
        boolean flag = true;
        departamento = (Departamento) RMIServer.database.get(id);
        if (departamento == null)
            flag = false;
        return flag;
    }

    @Override
    public boolean isDirecaoDepartamento() throws RemoteException {
        return true;
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "ELEIÇÂO DIREÇÃO DEPARTAMENTO - " + super.inLinePrint() + "\n";
    }

    @Override
    public String print() throws RemoteException{
        return super.print() +
                "\nDepartamento   - " + departamento
                ;
    }
}
