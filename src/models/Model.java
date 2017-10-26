package models;

import interfaces.ModelInt;
import models.organizacoes.Departamento;
import rmi.RMIServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Model
        extends java.rmi.server.UnicastRemoteObject
        implements ModelInt, Serializable
{
    protected long id;

    public Model() throws RemoteException {
        super();
    }

    @Override
    public long getId() {
        return id;
    }

    public boolean put() {
        boolean flag = true;
        try {
            RMIServer.registry.rebind(String.valueOf(id), this);
        } catch (RemoteException e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    public long safePut(Model e) {
        synchronized (e) {
            if(!e.put())
                return -1;
        }
        return e.getId();
    }

    public long safeAddPut(LinkedList l, Model e) {
        synchronized (l) {
            l.add(e);
            return safePut(e);
        }
    }

    public boolean lenghtIgual(String s, int i) {
        return s.length() == i;
    }

    public boolean lenghtMaior(String s, int i) {
        return s.length() > i;
    }

    public boolean lenghtEntre(String s, int a, int b) {
        return s.length() >= a && s.length() <= b;
    }

    public boolean isAlpha(String s) {
        Pattern p = Pattern.compile("^[ A-Za-z]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public boolean isNumber(String s) {
        return s.matches("^[0-9]*$");
    }

    @Override
    public String print() throws RemoteException {
        return "\nId - " + id;
    }

    public String printLinkedList(LinkedList l) throws RemoteException {
        String out = "";
        int i = 0;
        Model e;
        for (Object o : l) {
            e = (Model) o;
            out = out.concat(i + "->" +e.inLinePrint());
        }
        return out;
    }

    public boolean remove(LinkedList l, long id) {

    }

    @Override
    public abstract String inLinePrint() throws RemoteException;
}
