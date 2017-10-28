package models.listas;

import java.rmi.RemoteException;

public class VotoNulo extends Lista {
    public VotoNulo() throws RemoteException {
        super();
    }

    @Override
    public boolean isVotoNulo() {
        return true;
    }
}
