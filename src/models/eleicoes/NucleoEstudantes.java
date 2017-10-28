package models.eleicoes;

import interfaces.eleicoes.NucleoEstudantesInt;
import models.organizacoes.Departamento;
import rmi.RMIServer;

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
    public String inLinePrint() throws RemoteException {
        return "ELEIÇÃO NÚCLEO ESTUDANTES - " + super.inLinePrint() + "\n";
    }

    @Override
    public String print() throws RemoteException{
        return super.print() +
                "\nDepartamento   - " + departamento
                ;
    }
}

