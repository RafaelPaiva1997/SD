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

    public boolean test() {
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

    @Override
    public int getAno() {
        return ano;
    }

    @Override
    public int getMes() {
        return mes;
    }

    @Override
    public int getDia() {
        return dia;
    }

    @Override
    public int getHora() {
        return hora;
    }

    @Override
    public int getMinuto() {
        return minuto;
    }

    @Override
    public int getSegundo() {
        return segundo;
    }

    @Override
    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public void setMes(int mes) {
        this.mes = mes;
    }

    @Override
    public void setDia(int dia) {
        this.dia = dia;
    }

    @Override
    public void setHora(int hora) {
        this.hora = hora;
    }

    @Override
    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    @Override
    public void setSegundo(int segundo) {
        this.segundo = segundo;
    }

    @Override
    public String print() throws RemoteException {
        String out = "";
        if (hora != -1 && minuto != -1 && segundo != -1)
            out += hora + ":" + minuto + ":" + segundo + "   ";
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
        return "DATA - " + print() + "\n";
    }
}
