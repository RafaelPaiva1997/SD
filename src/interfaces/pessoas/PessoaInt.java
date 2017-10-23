package interfaces.pessoas;

import interfaces.ModelInt;

import java.rmi.RemoteException;

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

    boolean setNome(String nome) throws RemoteException;
    boolean setUsername(String username) throws RemoteException;
    boolean setPassword(String password) throws RemoteException;
    boolean setTelemovel(String telemovel) throws RemoteException;
    boolean setMorada(String morada) throws RemoteException;
    boolean setCodigoPostal(String codigoPostal) throws RemoteException;
    boolean setLocalidade(String localidade)throws RemoteException;
    boolean setNumeroCC(String numeroCC) throws RemoteException;
    boolean setGenero(String genero) throws RemoteException;

    long getValidadeCC() throws RemoteException;
    long getDataNascimento() throws RemoteException;
    long getDepartamentoInt() throws RemoteException;

    boolean isAluno() throws RemoteException;
    boolean isDocente() throws RemoteException;
    boolean isFuncionario() throws RemoteException;

    String print() throws RemoteException;

    String inLinePrint() throws RemoteException;
}
