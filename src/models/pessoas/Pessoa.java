package models.pessoas;

import interfaces.pessoas.PessoaInt;
import models.*;
import models.organizacoes.Departamento;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

public abstract class Pessoa
        extends Model
        implements PessoaInt, Serializable
{
    protected String nome;
    protected String username;
    protected String password;
    protected Departamento departamento;
    protected long telemovel;
    protected String morada;
    protected String codigoPostal;
    protected String localidade;
    protected long numeroCC;
    protected final Data validadeCC;
    protected String genero;
    protected final Data dataNascimento;

    protected final LinkedList<Lista> listas;
    protected final LinkedList<MesaDeVoto> mesasDeVoto;
    protected final LinkedList<Voto> votos;

    public Pessoa() throws RemoteException {
        super();
        validadeCC = new Data();
        dataNascimento = new Data();
        listas = new LinkedList<>();
        mesasDeVoto = new LinkedList<>();
        votos = new LinkedList<>();
    }


    @Override
    public String getNome() throws RemoteException {
        return nome;
    }

    @Override
    public String getUsername() throws RemoteException {
        return username;
    }

    @Override
    public String getPassword() throws RemoteException {
        return password;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    @Override
    public long getTelemovel() throws RemoteException {
        return telemovel;
    }

    @Override
    public String getMorada() throws RemoteException {
        return morada;
    }

    @Override
    public String getCodigoPostal() throws RemoteException {
        return codigoPostal;
    }

    @Override
    public String getLocalidade() throws RemoteException {
        return localidade;
    }

    @Override
    public long getNumeroCC() throws RemoteException {
        return numeroCC;
    }

    @Override
    public String getGenero() throws RemoteException {
        return genero;
    }

    public Data getValidadeCC() {
        return validadeCC;
    }

    public Data getDataNascimento() {
        return dataNascimento;
    }

    @Override
    public LinkedList<Lista> getListas() {
        return listas;
    }

    @Override
    public LinkedList<MesaDeVoto> getMesasDeVoto() {
        return mesasDeVoto;
    }

    @Override
    public LinkedList<Voto> getVotos() {
        return votos;
    }

    @Override
    public boolean setNome(String nome) throws RemoteException {
        boolean flag = true;
        if (lenghtMaior(nome, 0) &&
                isAlpha(nome))
            this.nome = nome;
        else
            flag = false;
        return flag;
    }

    @Override
    public boolean setUsername(String username) throws RemoteException {
        boolean flag = true;
        if (lenghtEntre(username, 8, 20))
            this.username = username;
        else
            flag = false;
        return flag;
    }


    @Override
    public boolean setPassword(String password) throws RemoteException, IllegalArgumentException {
        boolean flag = true;
        if (lenghtEntre(password, 8, 20))
            this.password = password;
        else
            flag = false;
        return flag;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public boolean setTelemovel(String telemovel) throws RemoteException {
        boolean flag = true;
        if (lenghtIgual(telemovel, 9) &&
                isNumber(telemovel))
            this.telemovel = Integer.parseInt(telemovel);
        else
            flag = false;
        return flag;
    }

    @Override
    public boolean setMorada(String morada) throws RemoteException {
        boolean flag = true;
        if (lenghtMaior(morada, 0))
            this.morada = morada;
        else
            flag = false;
        return flag;
    }

    private boolean testeCodigoPostal(String codigoPostal) {
        if (!lenghtIgual(codigoPostal, 8) || codigoPostal.charAt(4) != '-')
            return false;
        for (int i : new int[]{0, 1, 2, 3, 5, 6, 7})
            if (Character.getNumericValue(codigoPostal.charAt(i)) == -1 ||
                    Character.getNumericValue(codigoPostal.charAt(i)) == -2 ||
                    Character.getNumericValue(codigoPostal.charAt(i)) > 9)
                return false;
        return true;
    }

    @Override
    public boolean setCodigoPostal(String codigoPostal) throws RemoteException {
        boolean flag = true;
        if (testeCodigoPostal(codigoPostal))
            this.codigoPostal = codigoPostal;
        else
            flag = false;
        return flag;
    }

    @Override
    public boolean setLocalidade(String localidade) throws RemoteException {
        boolean flag = true;
        if (lenghtMaior(localidade, 0) &&
                isAlpha(localidade))
            this.localidade = localidade;
        else {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean setNumeroCC(String numeroCC) throws RemoteException {
        boolean flag = true;
        if (lenghtIgual(numeroCC, 8) &&
                isNumber(numeroCC))
            this.numeroCC = Integer.parseInt(numeroCC);
        else
            flag = false;
        return flag;
    }

    @Override
    public boolean setGenero(String genero) throws RemoteException {
        boolean flag = true;
        if (genero.matches("Femenino") ||
                genero.matches("Masculino") ||
                genero.matches("Outro"))
            this.genero = genero;
        else
            flag = false;
        return flag;

    }

    @Override
    public long getValidadeCCInt() throws RemoteException {
        return safePut(validadeCC);
    }

    @Override
    public long getDataNascimentoInt() throws RemoteException {
        return safePut(dataNascimento);
    }

    @Override
    public long getDepartamentoInt() throws RemoteException {
        return safePut(departamento);
    }

    @Override
    public long getVoto(int i) throws RemoteException {
        return safePut(votos.get(i));
    }

    @Override
    public boolean isAluno() throws RemoteException {
        return false;
    }

    @Override
    public boolean isDocente() throws RemoteException {
        return false;
    }

    @Override
    public boolean isFuncionario() throws RemoteException {
        return false;
    }

    @Override
    public boolean hasReferences() throws RemoteException {
        return !(listas.isEmpty() && mesasDeVoto.isEmpty() && votos.isEmpty());
    }

    @Override
    public String print() throws RemoteException{
        return super.print() +
                "\nNome            - " + nome +
                "\nUsername        - " + username +
                "\nPassword        - " + password +
                "\nDepartamento    - " + departamento.getNome() +
                "\nNº Telemóvel    - " + telemovel +
                "\nMorada          - " + morada +
                "\nCódigo Postal   - " + codigoPostal +
                "\nLocalidade      - " + localidade +
                "\nNúmero C.C.     - " + numeroCC +
                "\nValidade C.C.   - " + validadeCC.print() +
                "\nGénero          - " + genero +
                "\nData Nascimento - " + dataNascimento.print();
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "Nome: " + nome + " Nº CC: " + numeroCC + " Departamento: " + departamento.getNome();
    }

    @Override
    public String printVotos() throws RemoteException {
        return printLinkedList(votos);
    }

    @Override
    public String printReferences() throws RemoteException {
        StringBuilder out = new StringBuilder();
        out.append("   ." + nome + "\n");
        for (Lista e : listas)
            out.append(e.inLinePrint());
        for (MesaDeVoto e : mesasDeVoto)
            out.append(e.inLinePrint());
        for (Voto e : votos)
            out.append(e.inLinePrint());
        return out.toString();
    }

    public void removeReferences() {
        try {
            for (Lista e : listas)
                e.getPessoas().remove(this);
            for (MesaDeVoto e : mesasDeVoto)
                e.getPessoas().remove(this);
            for (Voto e : votos)
                e.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
