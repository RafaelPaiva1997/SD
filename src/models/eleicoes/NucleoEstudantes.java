package models.eleicoes;

import interfaces.eleicoes.NucleoEstudantesInt;
import models.listas.Lista;
import models.organizacoes.Departamento;
import models.pessoas.Pessoa;
import rmi.RMIServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class NucleoEstudantes
        extends Eleicao
        implements NucleoEstudantesInt, Serializable
{
    private Departamento departamento;

    public NucleoEstudantes() throws RemoteException {
        super();
    }

    @Override
    public LinkedList<Lista> getListas(Pessoa p) {
        LinkedList<Lista> out = new LinkedList<>();
        for (Lista l : listas) {
            try {
                if (p.isAluno() && l.isListaAlunos() &&
                        p.getDepartamento().getId() == departamento.getId())
                    out.add(l);
                return null;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return out;
    }

    @Override
    public boolean isNucleoEstudantes() throws RemoteException {
        return true;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    @Override
    public boolean setDepartamento(long id) throws RemoteException {
        boolean flag = true;
        departamento = (Departamento) RMIServer.database.get(id);
        if (departamento == null)
            flag = false;
        return flag;
    }

    @Override
    public boolean canJoin(Lista e) {
        return e.isListaAlunos();
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "ELEIÇÃO NÚCLEO ESTUDANTES - " + super.inLinePrint() + "\n";
    }

    @Override
    public String print() throws RemoteException{
        return super.print() +
                "\nDepartamento   - " + departamento.getNome();
    }
}

