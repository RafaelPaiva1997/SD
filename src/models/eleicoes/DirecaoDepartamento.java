package models.eleicoes;

import interfaces.eleicoes.DirecaoDepartamentoInt;
import models.listas.Lista;
import models.organizacoes.Departamento;
import models.pessoas.Pessoa;
import rmi.RMIServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class DirecaoDepartamento
        extends Eleicao
        implements DirecaoDepartamentoInt, Serializable
{
    private Departamento departamento;

    public DirecaoDepartamento() throws RemoteException {
        super();
    }

    @Override
    public LinkedList<Lista> getListas(Pessoa p) {
        LinkedList<Lista> out = new LinkedList<>();
        for (Lista l : listas) {
            try {
                if (p.isDocente() && l.isListaDocentes() &&
                        p.getDepartamento().getId() == departamento.getId())
                    out.add(l);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return out;
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
    public boolean isDirecaoDepartamento() throws RemoteException {
        return true;
    }

    @Override
    public boolean canJoin(Lista e) {
        return e.isListaDocentes();
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "ELEIÇÂO DIREÇÃO DEPARTAMENTO - " + super.inLinePrint() + "\n";
    }

    @Override
    public String print() throws RemoteException{
        return super.print() +
                "\nDepartamento   - " + departamento.getNome();
    }
}
