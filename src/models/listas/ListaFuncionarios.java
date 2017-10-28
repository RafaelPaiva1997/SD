package models.listas;

import java.rmi.RemoteException;

public class ListaFuncionarios extends Lista {

    public ListaFuncionarios() throws RemoteException {
        super();
    }

    @Override
    public boolean isListaFuncionarios() {
        return true;
    }
}
