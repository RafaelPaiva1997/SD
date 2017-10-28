package models.listas;

import java.rmi.RemoteException;

public class VotoBranco extends Lista{
    public VotoBranco() throws RemoteException{
        super();
    }

    @Override
    public boolean isVotoBranco() {
        return true;
    }
}
