package rmi;

import models.Data;
import models.eleicoes.Eleicao;

import java.util.Date;

public class DataChecker extends Thread {

    private Date date;
    private Data data;

    @Override
    public void run() {
        try {
            while (true) {
                date = new Date();
                data = new Data();
                data.setAno(date.getYear());
                data.setMes(date.getMonth());
                data.setDia(date.getDay());
                data.setHora(date.getHours());
                data.setMinuto(date.getMinutes());
                data.setSegundo(date.getSeconds());
                if (!RMIServer.database.getEleicoes().isEmpty()) {
                    for (Eleicao e : RMIServer.database.getEleicoes()) {
                        if (!e.isRunning() && data.maior(e.getDataInicio()) && e.getDataFim().maior(data))
                            e.setRunning(true);
                        if (e.isRunning() && data.maior(e.getDataFim())) {
                            e.setRunning(false);
                            e.setFinish(true);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
