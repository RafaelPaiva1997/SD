package models.listas;

import models.pessoas.Pessoa;

import java.rmi.RemoteException;

public class ListaAlunos extends Lista {
    public ListaAlunos() throws RemoteException {
        super();
    }

    @Override
    public boolean isListaAlunos() {
        return true;
    }

    @Override
    public boolean canJoin(Pessoa e) {
        try {
            return e.isAluno();
        } catch (RemoteException e1) {
            e1.printStackTrace();
            return false;
        }
    }
}
