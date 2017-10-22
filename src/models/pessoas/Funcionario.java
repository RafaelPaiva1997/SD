package models.pessoas;

import interfaces.pessoas.FuncionarioInt;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Funcionario
        extends Pessoa
        implements FuncionarioInt, Serializable
{
    private String funcao;

    public Funcionario() throws RemoteException {
        super();
    }

    @Override
    public String getFuncao() {
        return funcao;
    }

    @Override
    public boolean setFuncao(String funcao) {
        boolean flag = true;
        if (lenghtMaior(funcao, 0) &&
                isAlpha(funcao))
            this.funcao = funcao;
        else
            flag = false;
        return flag;
    }

    @Override
    public boolean isFuncionario() throws RemoteException {
        return true;
    }

    @Override
    public String print() throws RemoteException{
        return "   .FUNCIONÁRIO" +
                super.print() +
                "\nFunção         - " + funcao;
    }
}
