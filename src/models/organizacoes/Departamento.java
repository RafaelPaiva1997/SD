package models.organizacoes;

import interfaces.organizacoes.DepartamentoInt;
import models.Model;
import models.pessoas.Aluno;
import models.pessoas.Docente;
import models.pessoas.Funcionario;
import models.pessoas.Pessoa;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class Departamento
        extends Model
        implements DepartamentoInt, Serializable
{

    private String nome;
    private Faculdade faculdade;
    private final LinkedList<Pessoa> pessoas;

    public Departamento() throws RemoteException {
        super();
        pessoas = new LinkedList<>();
    }



    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public Faculdade getFaculdade() {
        return faculdade;
    }

    @Override
    public LinkedList<Pessoa> getPessoas() {
        return pessoas;
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

    public void setFaculdade(Faculdade faculdade) {
        this.faculdade = faculdade;
    }

    @Override
    public long newAluno(long id) throws RemoteException {
        Pessoa e = new Aluno();
        return newPessoa(e, id);
    }

    @Override
    public long newDocente(long id) throws RemoteException {
        Pessoa e = new Docente();
        return newPessoa(e, id);
    }

    @Override
    public long newFuncionario(long id) throws RemoteException {
        Pessoa e = new Funcionario();
        return newPessoa(e, id);
    }

    public long newPessoa(Pessoa e, long id) {
        e.setId(id);
        e.setDepartamento(this);
        return safeAddPut(pessoas, e);
    }

    @Override
    public long getPessoa(int i) throws RemoteException {
        return safePut(pessoas.get(i));
    }

    @Override
    public String printPessoas() throws RemoteException {
        return printLinkedList(pessoas);
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "\nDEPARTAMENTO - Nome: " + nome + " Faculdade: " + faculdade.getNome();
    }
}
