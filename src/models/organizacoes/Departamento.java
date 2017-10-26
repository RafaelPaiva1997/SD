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
    public long newAluno() throws RemoteException {
        return newPessoa(new Aluno());
    }

    @Override
    public long newDocente() throws RemoteException {
        return newPessoa(new Docente());
    }

    @Override
    public long newFuncionario() throws RemoteException {
        return newPessoa(new Funcionario());
    }

    public long newPessoa(Pessoa e) {
        e.setDepartamento(this);
        return safeAddPut(pessoas, e);
    }

    @Override
    public long getPessoa(int i) throws RemoteException {
        return safePut(pessoas.get(i));
    }

    @Override
    public boolean deletePessoa(long id) throws RemoteException {
        return remove(pessoas, id);
    }

    @Override
    public String printPessoas() throws RemoteException {
        return printLinkedList(pessoas);
    }

    @Override
    public String print() throws RemoteException {
        return "   .DEPARTAMENTO" +
                "\nNome    - " + nome +
                "\nPessoas - " + pessoas.size();
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "DEPARTAMENTO - Nome: " + nome + " Faculdade: " + faculdade.getNome() + "\n";
    }
}
