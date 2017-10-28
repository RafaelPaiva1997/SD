package models.eleicoes;

import interfaces.eleicoes.ConselhoGeralInt;
import models.listas.Lista;
import models.organizacoes.Departamento;
import models.pessoas.Pessoa;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class ConselhoGeral
        extends Eleicao
        implements ConselhoGeralInt, Serializable

{
    public ConselhoGeral() throws RemoteException {
        super();
    }

    @Override
    public LinkedList<Lista> getListas(Pessoa p) {
        LinkedList<Lista> out = new LinkedList<>();
        for (Lista l : listas) {
            try {
                if (l.isListaAlunos() && p.isAluno() ||
                        l.isListaDocentes() && p.isDocente() ||
                        l.isListaFuncionarios() && p.isFuncionario())
                    out.add(l);
                return null;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return out;
    }

    @Override
    public boolean isConselhoGeral() throws RemoteException {
        return true;
    }

    @Override
    public boolean canJoin(Lista e) {
        return true;
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "ELEIÇÃO CONSELHO GERAL - " + super.inLinePrint() + "\n";
    }
}
