package models.organizacoes;

import interfaces.organizacoes.FaculdadeInt;
import models.Model;
import models.pessoas.Pessoa;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class Faculdade
        extends Model
        implements FaculdadeInt, Serializable
{

    private String nome;
    private final LinkedList<Departamento> departamentos;

    public Faculdade() throws RemoteException {
        super();
        departamentos = new LinkedList<>();
    }

    @Override
    public LinkedList<Departamento> getDepartamentos() {
        return departamentos;
    }

    @Override
    public String getNome() throws RemoteException {
        return nome;
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
    public long newDepartamento() throws RemoteException {
        Departamento e = new Departamento();
        e.setFaculdade(this);
        return safeAddPut(departamentos, e);
    }

    @Override
    public long getDepartamento(int i) throws RemoteException {
        return safePut(departamentos.get(i));
    }

    @Override
    public boolean deleteDepartamento(long id) throws RemoteException {
        if(departamentos.get(find(departamentos, id)).hasReferences())
            departamentos.get(find(departamentos, id)).removeReferences();
        return remove(departamentos, id);
    }

    @Override
    public String printDepartamentos() throws RemoteException {
        return printLinkedList(departamentos);
    }

    @Override
    public String print() throws RemoteException {
        return "   .FACULDADE" +
                "\nNome          - " + nome +
                "\nDepartamentos - " + departamentos.size();
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "FACULDADE - Nome: " + nome +"\n";
    }

    @Override
    public boolean hasReferences() throws RemoteException {
        for (Departamento e : departamentos)
            if (e.hasReferences())
                return true;
        return false;
    }

    @Override
    public String printReferences() throws RemoteException {
        StringBuilder out = new StringBuilder();
        out.append("   ." + nome + "\n");
        for (Departamento e : departamentos)
            out.append(e.printReferences());
        return out.toString();
    }

    public void removeReferences() {
        try {
            for (Departamento e : departamentos)
                if (e.hasReferences())
                    e.removeReferences();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
