package interfaces.eleicoes;

import java.rmi.RemoteException;

import interfaces.ModelInt;
import models.Lista;
import models.MesaDeVoto;
import models.Voto;

import java.util.LinkedList;

/**
 * Created by Carlos on 21-10-2017.
 */
public interface EleicaoInt extends ModelInt {
    String getTitulo() throws RemoteException;
    String getDescricao() throws RemoteException;
    LinkedList<Voto> getVotos() throws RemoteException;
    LinkedList<MesaDeVoto> getMesasDeVoto() throws RemoteException;
    LinkedList<Lista> getListas() throws RemoteException;

    boolean setTitulo(String titulo) throws RemoteException;
    boolean setDescricao(String descricao) throws RemoteException;

    long getDataInicioInt() throws RemoteException;
    long getDataFimInt() throws RemoteException;

    long newVoto() throws RemoteException;
    long newLista() throws RemoteException;

    long getVoto(int i) throws RemoteException;
    long getMesaDeVoto(int i) throws RemoteException;
    long getLista(int i) throws RemoteException;

    boolean deleteMesaDeVoto(long id) throws RemoteException;
    boolean deleteLista(long id) throws RemoteException;

    boolean isConselhoGeral() throws RemoteException;
    boolean isDirecaoDepartamento() throws RemoteException;
    boolean isDirecaoFaculdade() throws RemoteException;
    boolean isNucleoEstudantes() throws RemoteException;

    boolean hasReferences() throws RemoteException;

    String print() throws RemoteException;
    String printVotos() throws RemoteException;
    String printMesasDeVoto() throws RemoteException;
    String printListas() throws RemoteException;
    String inLinePrint() throws RemoteException;
    String printReferences() throws RemoteException;

}
