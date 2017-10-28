package models.organizacoes;

import interfaces.organizacoes.DepartamentoInt;
import models.MesaDeVoto;
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
    private final MesaDeVoto mesaDeVoto;
    private final LinkedList<Pessoa> pessoas;

    public Departamento() throws RemoteException {
        super();
        pessoas = new LinkedList<>();
        mesaDeVoto = new MesaDeVoto();
    }

    @Override
    public String getNome() throws RemoteException {
        return nome;
    }

    @Override
    public Faculdade getFaculdade() throws RemoteException {
        return faculdade;
    }

    @Override
    public MesaDeVoto getMesaDeVoto() throws RemoteException {
        return mesaDeVoto;
    }

    @Override
    public LinkedList<Pessoa> getPessoas() throws RemoteException {
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

    public void setFaculdade(Faculdade faculdade) throws RemoteException {
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

    private long newPessoa(Pessoa e) {
        e.setDepartamento(this);
        return safeAddPut(pessoas, e);
    }

    @Override
    public long getPessoa(int i) throws RemoteException {
        return safePut(pessoas.get(i));
    }

    @Override
    public boolean deletePessoa(long id) throws RemoteException {
        if (pessoas.get(find(pessoas, id)).hasReferences())
            pessoas.get(find(pessoas, id)).removeReferences();
        return remove(pessoas, id);
    }

    @Override
    public boolean hasReferences() throws RemoteException {
        for (Pessoa e : pessoas)
            if (e.hasReferences())
                return true;
        return mesaDeVoto.hasReferences();
    }

    @Override
    public String printPessoas() throws RemoteException {
        return printLinkedList(pessoas);
    }

    @Override
    public String print() throws RemoteException {
        return "   .DEPARTAMENTO" +
                "\nNome    - " + nome +
                "\nPessoas - " + pessoas.size()
                ;
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "DEPARTAMENTO - Nome: " + nome + " Faculdade: " + faculdade.getNome() + "\n";
    }

    @Override
    public String printReferences() throws RemoteException {
        StringBuilder out = new StringBuilder();
        out.append("   ." + nome + "\n");
        for (Pessoa e : pessoas)
            out.append(e.printReferences());
        out.append(mesaDeVoto.printReferences()) ;
        return out.toString();
    }

    public void removeReferences() {
        try {
            for (Pessoa e : pessoas)
                if (e.hasReferences())
                    e.removeReferences();
            if (mesaDeVoto.hasReferences())
                mesaDeVoto.removeReferences();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
