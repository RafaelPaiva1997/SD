package models.listas;

import models.pessoas.Pessoa;

import java.rmi.RemoteException;

public class ListaDocentes extends Lista {

    public ListaDocentes() throws RemoteException {
        super();
    }

    @Override
    public boolean isListaDocentes() {
        return true;
    }

    @Override
    public boolean canJoin(Pessoa e) {
        try {
            return e.isDocente();
        } catch (RemoteException e1) {
            e1.printStackTrace();
            return false;
        }
    }
}
