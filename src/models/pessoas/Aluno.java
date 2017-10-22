package models.pessoas;

import interfaces.pessoas.AlunoInt;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Aluno
        extends Pessoa
        implements AlunoInt, Serializable
{
    private long numeroAluno;
    private String curso;

    public Aluno() throws RemoteException {
        super();

    }

    @Override
    public long getNumeroAluno() throws RemoteException {
        return numeroAluno;
    }

    @Override
    public String getCurso() throws RemoteException {
        return curso;
    }

    @Override
    public boolean setNumeroAluno(String numeroAluno) throws RemoteException  {
        boolean flag = true;
        if (lenghtIgual(numeroAluno, 10) &&
                isNumber(numeroAluno))
            this.numeroAluno = Integer.parseInt(numeroAluno);
        else
            flag = false; ;
        return flag;

    }

    @Override
    public boolean setCurso(String curso) throws RemoteException {
        boolean flag = true;
        if (lenghtMaior(curso, 0) &&
                isAlpha(curso))
            this.curso = curso ;
        else
            flag = false;
        return flag;
    }

    @Override
    public boolean isAluno() throws RemoteException {
        return true;
    }

    @Override
    public String print() throws RemoteException{
        return "   .ALUNO" +
                super.print() +
                "\nNº Aluno        - " + numeroAluno +
                "\nCurso           - " + curso;
    }
}
