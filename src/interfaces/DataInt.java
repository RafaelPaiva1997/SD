package interfaces;

import java.rmi.RemoteException;

/**
 * Created by Carlos on 20-10-2017.
 */
public interface DataInt extends ModelInt {
    int getAno() throws RemoteException;
    int getMes() throws RemoteException;
    int getDia() throws RemoteException;
    int getHora() throws RemoteException;
    int getMinuto() throws RemoteException;
    int getSegundo() throws RemoteException;

    void setAno(int ano) throws RemoteException;
    void setMes(int mes) throws RemoteException;
    void setDia(int dia) throws RemoteException;
    void setHora(int hora) throws RemoteException;
    void setMinuto(int minuto) throws RemoteException;
    void setSegundo(int segundo) throws RemoteException;

    boolean test() throws RemoteException;

    String print() throws RemoteException;

    String fullPrint() throws RemoteException;
}
