package models.organizacoes;

import interfaces.organizacoes.FaculdadeInt;
import models.Model;

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
    public long newDepartamento(long id) throws RemoteException {
        Departamento e = new Departamento();
        e.setId(id);
        e.setFaculdade(this);
        return safeAddPut(departamentos, e);
    }

    @Override
    public long getDepartamento(int i) throws RemoteException {
        return safePut(departamentos.get(i));
    }

    @Override
    public boolean deleteDepartamento(int i) throws RemoteException {
        return departamentos.remove(i) != null;
    }

    @Override
    public String printDepartamentos() throws RemoteException {
        return printLinkedList(departamentos);
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "\nFACULDADE - Nome: " + nome;
    }
}
