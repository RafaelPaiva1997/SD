package models.listas;

import java.rmi.RemoteException;

public class ListaAlunos extends Lista {
    public ListaAlunos() throws RemoteException {
        super();
    }

    @Override
    public boolean isListaAlunos() {
        return true;
    }
}
