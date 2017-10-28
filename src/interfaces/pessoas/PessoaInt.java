package interfaces.pessoas;

import interfaces.ModelInt;
import models.Lista;
import models.MesaDeVoto;
import models.Voto;

import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * Created by Carlos on 19-10-2017.
 */
public interface PessoaInt extends ModelInt {
    String getNome() throws RemoteException;
    String getUsername() throws RemoteException;
    String getPassword() throws RemoteException;
    long getTelemovel() throws RemoteException;
    String getCodigoPostal() throws  RemoteException;
    String getLocalidade() throws  RemoteException;
    String getMorada() throws RemoteException;
    long getNumeroCC() throws RemoteException;
    String getGenero() throws RemoteException;

    LinkedList<Lista> getListas() throws RemoteException;
    LinkedList<MesaDeVoto> getMesasDeVoto() throws RemoteException;
    LinkedList<Voto> getVotos() throws RemoteException;

    boolean setNome(String nome) throws RemoteException;
    boolean setUsername(String username) throws RemoteException;
    boolean setPassword(String password) throws RemoteException;
    boolean setTelemovel(String telemovel) throws RemoteException;
    boolean setMorada(String morada) throws RemoteException;
    boolean setCodigoPostal(String codigoPostal) throws RemoteException;
    boolean setLocalidade(String localidade)throws RemoteException;
    boolean setNumeroCC(String numeroCC) throws RemoteException;
    boolean setGenero(String genero) throws RemoteException;

    long getValidadeCCInt() throws RemoteException;
    long getDataNascimentoInt() throws RemoteException;
    long getDepartamentoInt() throws RemoteException;
    long getVoto(int i) throws RemoteException;

    boolean isAluno() throws RemoteException;
    boolean isDocente() throws RemoteException;
    boolean isFuncionario() throws RemoteException;
    boolean hasReferences() throws RemoteException;

    String print() throws RemoteException;
    String inLinePrint() throws RemoteException;
    String printVotos() throws RemoteException;
    String printReferences() throws RemoteException;
}
