package models.listas;

import java.rmi.RemoteException;

public class ListaDocentes extends Lista {

    public ListaDocentes() throws RemoteException {
        super();
    }

    @Override
    public boolean isListaDocentes() {
        return true;
    }
}
