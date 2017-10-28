package models.listas;

import models.pessoas.Pessoa;

import java.rmi.RemoteException;

public class ListaFuncionarios extends Lista {

    public ListaFuncionarios() throws RemoteException {
        super();
    }

    @Override
    public boolean isListaFuncionarios() {
        return true;
    }

    @Override
    public boolean canJoin(Pessoa e) {
        try {
            return e.isFuncionario();
        } catch (RemoteException e1) {
            e1.printStackTrace();
            return false;
        }
    }
}
