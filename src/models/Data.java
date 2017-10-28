package models;

import interfaces.DataInt;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Created by Carlos on 20-10-2017.
 */
public class Data extends Model implements DataInt, Serializable
{

    private int ano;
    private int mes;
    private int dia;
    private int hora;
    private int minuto;
    private int segundo;

    public Data() throws RemoteException {
        super();
        ano = -1;
        mes = -1;
        dia = -1;
        hora = -1;
        minuto = -1;
        segundo = -1;
    }

    public boolean test() throws RemoteException {
        if (!((hora != -1 && minuto != -1 && segundo != -1) ||
                (hora == -1 && minuto == -1 && segundo == -1)) ||
                !((ano != -1 && mes != -1 && dia != -1) ||
                        (ano == -1 && mes == -1 && dia == -1)) ||
                ano < -1 || ano == 0 ||
                mes < -1 || mes == 0 || mes > 12 ||
                dia < -1 || dia == 0 ||
                hora < -1 || hora > 23 ||
                minuto < -1 || minuto > 59 ||
                segundo <-1)
            return false;

        if ((mes == 1 ||
                mes == 3 ||
                mes == 5 ||
                mes == 7 ||
                mes == 8 ||
                mes == 10 ||
                mes == 11)
                &&
                dia > 31)
            return false;
        else if (mes == 2 &&
                ((((ano % 400 == 0) || ((ano % 4 == 0) && (ano % 100 != 0))) && dia > 29) ||
                        (!((ano % 400 == 0) || ((ano % 4 == 0) && (ano % 100 != 0))) && dia > 28)))
            return false;
        else if (dia > 30)
            return false;
        return true;
    }

    public boolean maior(Data d) {
        if (ano > d.ano) return true;
        if (mes > d.mes) return true;
        if (dia > d.dia) return true;
        if (hora > d.hora) return true;
        if (minuto > d.minuto) return true;
        return segundo > d.segundo;
    }

    @Override
    public int getAno() throws RemoteException {
        return ano;
    }

    @Override
    public int getMes() throws RemoteException {
        return mes;
    }

    @Override
    public int getDia() throws RemoteException {
        return dia;
    }

    @Override
    public int getHora() throws RemoteException {
        return hora;
    }

    @Override
    public int getMinuto() throws RemoteException {
        return minuto;
    }

    @Override
    public int getSegundo() throws RemoteException {
        return segundo;
    }

    @Override
    public void setAno(int ano) throws RemoteException {
        this.ano = ano;
    }

    @Override
    public void setMes(int mes) throws RemoteException {
        this.mes = mes;
    }

    @Override
    public void setDia(int dia) throws RemoteException {
        this.dia = dia;
    }

    @Override
    public void setHora(int hora) throws RemoteException {
        this.hora = hora;
    }

    @Override
    public void setMinuto(int minuto) throws RemoteException {
        this.minuto = minuto;
    }

    @Override
    public void setSegundo(int segundo) throws RemoteException {
        this.segundo = segundo;
    }

    @Override
    public String print() throws RemoteException {
        String out = "";
        if (hora != -1 && minuto != -1 && segundo != -1)
            out += hora + ":" + minuto + ":" + segundo + " ";
        if (ano != -1 && mes != -1 && dia != -1)
            out += dia + "/" + mes + "/" + ano;
        return out;
    }

    @Override
    public String fullPrint() throws RemoteException {
        String out = "   .DATA" +
                super.print();
        if (ano != -1) out += "\nAno    - " + ano;
        if (mes != -1) out += "\nMês    - " + mes;
        if (dia != -1) out += "\nDia    - " + dia;
        if (hora != -1) out += "\nHora   - " + hora;
        if (minuto != -1) out += "\nMinuto - " + minuto;
        if (dia != -1) out += "\nDia    - " + dia;
        return out;
    }

    @Override
    public String inLinePrint() throws RemoteException {
        return "DATA - " + print();
    }
}
