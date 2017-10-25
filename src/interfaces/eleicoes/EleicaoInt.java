package interfaces.eleicoes;

import java.rmi.RemoteException;

import interfaces.ModelInt;
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

    boolean setTitulo(String titulo) throws RemoteException;
    boolean setDescricao(String descricao) throws RemoteException;

    long getDataInicio() throws RemoteException;
    long getDataFim() throws RemoteException;
    long newVoto() throws RemoteException;
    long newMesaDeVoto() throws RemoteException;
    long getVoto(int i) throws RemoteException;
    long getMesaDeVoto(int i) throws RemoteException;
    boolean deleteVoto(long id) throws RemoteException;
    boolean deleteMesaDeVoto(long id) throws RemoteException;

    boolean isConselhoGeral() throws RemoteException;
    boolean isDirecaoDepartamento() throws RemoteException;
    boolean isDirecaoFaculdade() throws RemoteException;
    boolean isNucleoEstudantes() throws RemoteException;

    String printVotos() throws RemoteException;
    String printMesasDeVoto() throws RemoteException;
    String inLinePrint() throws RemoteException;

}
