package models;

import interfaces.MesaDeVotoInt;
import models.eleicoes.Eleicao;
import models.organizacoes.Departamento;
import models.pessoas.Pessoa;
import rmi.RMIServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class MesaDeVoto
        extends Model
        implements MesaDeVotoInt, Serializable
{
    private Departamento departamento;
    private boolean[] logins;
    private final LinkedList<Pessoa> pessoas;
    private final LinkedList<Eleicao> eleicoes;
    private final LinkedList<Voto> votos;

    public MesaDeVoto() throws RemoteException {
        super();
        logins = new boolean[]{false, false, false};
        pessoas = new LinkedList<>();
        eleicoes = new LinkedList<>();
        votos = new LinkedList<>();
    }

    @Override
    public long getDepartamento() throws RemoteException  {
        return safePut(departamento);
    }

    @Override
    public LinkedList<Pessoa> getPessoas() throws RemoteException  {
        return pessoas;
    }

    @Override
    public LinkedList<Eleicao> getEleicoes() throws RemoteException  {
        return eleicoes;
    }

    @Override
    public LinkedList<Voto> getVotos() throws RemoteException  {
        return votos;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public boolean addPessoa(long id) throws RemoteException {
        Pessoa e = (Pessoa) RMIServer.database.get(id);
        return !pessoas.contains(e) && pessoas.size() <= logins.length && add(pessoas, e) && add(e.getMesasDeVoto(), this);
    }

    @Override
    public boolean addEleicao(long id) throws RemoteException {
        Eleicao e = (Eleicao) RMIServer.database.get(id);
        return !eleicoes.contains(e) && add(eleicoes, e) && add(e.getMesasDeVoto(), this);
    }

    @Override
    public long getPessoa(int i) throws RemoteException {
        return safePut(pessoas.get(i));
    }

    @Override
    public long getEleicao(int i) throws RemoteException {
        return safePut(eleicoes.get(i));
    }

    @Override
    public long getVoto(int i) throws RemoteException {
        return safePut(votos.get(i));
    }

    @Override
    public boolean removePessoa(long id) throws RemoteException {
        return remove(pessoas, id) && remove(((Pessoa) RMIServer.database.get(id)).getListas(), this.id);
    }

    @Override
    public boolean removeEleicao(long id) throws RemoteException {
        return remove(eleicoes, id) && remove(((Eleicao) RMIServer.database.get(id)).getListas(), this.id);
    }

    @Override
    public boolean hasReferences() throws RemoteException {
        return !(eleicoes.isEmpty() && pessoas.isEmpty() && votos.isEmpty());
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        for (int i = 0; i < pessoas.size(); i++){
            if (!logins[i] && pessoas.get(i).getUsername().equals(username) &&
                    pessoas.get(i).getPassword().equals(password)) {
                logins[i] = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void shutdown() throws RemoteException {
        for (int i = 0; i < logins.length; i++)
            logins[i] = false;
    }

    @Override
    public String print() throws RemoteException {
        return super.print() +
                "\nDepartament     - " + departamento +
                "\nEleição         -"  + eleicoes +
                "\nPessoas         -"  + pessoas.size() +
                "\nVoto            - " + votos.size();

    }

    @Override
    public String printPessoas() throws RemoteException {
        return printLinkedList(pessoas);
    }

    @Override
    public String printEleicoes() throws RemoteException {
        return printLinkedList(eleicoes);
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "MESA DE VOTO - Departamento: " + departamento.getNome() + " Faculdade: " + departamento.getFaculdade().getNome() + "\n";
    }

    @Override
    public boolean isWorking() throws RemoteException {
        return !eleicoes.isEmpty() && pessoas.size() == 3 && logins[0] && logins[1] && logins[2];
    }

    @Override
    public String printVotos() throws RemoteException {
        return printLinkedList(votos);
    }

    @Override
    public String printReferences() throws RemoteException {
        StringBuilder out = new StringBuilder();
        out.append("   .Realizada no departamento " + departamento + "\n");
        for (Pessoa e : pessoas)
            out.append(e.inLinePrint());
        for (Eleicao e : eleicoes)
            out.append(e.inLinePrint());
        for (Voto e : votos)
            out.append(e.inLinePrint());
        return out.toString();
    }

    public void removeReferences() {
        try {
            for (Pessoa e : pessoas)
                e.getMesasDeVoto().remove(this);
            for (Eleicao e : eleicoes)
                e.getMesasDeVoto().remove(this);
            for (Voto e : votos)
                e.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
