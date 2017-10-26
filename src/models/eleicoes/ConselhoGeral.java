package models.eleicoes;

import interfaces.eleicoes.ConselhoGeralInt;
import models.organizacoes.Departamento;

import java.io.Serializable;
import java.rmi.RemoteException;

public class ConselhoGeral
        extends Eleicao
        implements ConselhoGeralInt, Serializable

{
    public ConselhoGeral() throws RemoteException {
        super();
    }

    @Override
    public boolean isConselhoGeral() throws RemoteException {
        return true;
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "ELEIÇÃO CONSELHO GERAL - " + super.inLinePrint() + "\n";
    }
    @Override
    public String print() throws RemoteException{
        return super.print() +
                "\nTítulo            - " + titulo +
                "\nDescrição        - " + descricao +
                "\nData de inicio   - " + dataInicio.inLinePrint() +
                "\nData de fim       -" + dataFim.inLinePrint() +
                "\nMesas de voto    - " + mesasDeVoto +
                "\nListas           -"  + listas
                ;
    }
}
