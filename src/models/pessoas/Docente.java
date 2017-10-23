package models.pessoas;

import interfaces.pessoas.DocenteInt;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Docente
        extends Pessoa
        implements DocenteInt, Serializable
{
    private String cargo;

    public Docente() throws RemoteException {
        super();
    }

    @Override
    public String getCargo() {
        return cargo;
    }

    @Override
    public boolean setCargo(String cargo) {
        boolean flag = true;
        if (lenghtMaior(cargo, 0) &&
                isAlpha(cargo))
            this.cargo = cargo;
        else
            flag = false;
        return flag;
    }

    @Override
    public boolean isDocente() throws RemoteException {
        return true;
    }

    @Override
    public String print() throws RemoteException{
        return  "   .DOCENTE" +
                super.print() +
                "\nCargo          - " + cargo;
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "DOCENTE - " + super.inLinePrint() + " Cargo: " + cargo + "\n";
    }
}
