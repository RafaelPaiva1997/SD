package interfaces.eleicoes;

import java.rmi.RemoteException;

import com.sun.org.apache.regexp.internal.RE;
import interfaces.ModelInt;
import models.listas.Lista;
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
    LinkedList<Lista> getListas(long id) throws RemoteException;

    boolean setTitulo(String titulo) throws RemoteException;
    boolean setDescricao(String descricao) throws RemoteException;
    void setRunning(boolean running) throws RemoteException;
    void setFinish(boolean finish) throws RemoteException;

    long getDataInicioInt() throws RemoteException;
    long getDataFimInt() throws RemoteException;

    long newListaAlunos() throws RemoteException;
    long newListaDocentes() throws RemoteException;
    long newListaFuncionarios() throws RemoteException;
    boolean addMesaDeVoto(long id) throws RemoteException;

    long getVoto(int i) throws RemoteException;
    long getMesaDeVoto(int i) throws RemoteException;
    long getLista(int i) throws RemoteException;

    boolean deleteLista(long id) throws RemoteException;
    boolean removeMesaDeVoto(long id) throws RemoteException;

    boolean isConselhoGeral() throws RemoteException;
    boolean isDirecaoDepartamento() throws RemoteException;
    boolean isDirecaoFaculdade() throws RemoteException;
    boolean isNucleoEstudantes() throws RemoteException;
    boolean isRunning() throws RemoteException;
    boolean isFinish() throws RemoteException;

    boolean hasReferences() throws RemoteException;

    String print() throws RemoteException;
    String printVotos() throws RemoteException;
    String printMesasDeVoto() throws RemoteException;
    String printListas() throws RemoteException;
    String printListas(long id) throws RemoteException;
    String inLinePrint() throws RemoteException;
    String printReferences() throws RemoteException;

}
