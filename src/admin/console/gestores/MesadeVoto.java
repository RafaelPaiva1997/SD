package admin.console.gestores;

import admin.console.AdminConsole;
import interfaces.MesaDeVotoInt;
import interfaces.eleicoes.EleicaoInt;
import models.MesaDeVoto;

import java.rmi.RemoteException;
import java.util.function.BooleanSupplier;

import static admin.console.AdminConsole.*;
import static admin.console.AdminConsole.r1;

/**
 * Created by Johny on 26/10/2017.
 */
public class MesadeVoto {
    MesaDeVotoInt mesaDeVotoInt;

    public static MesaDeVotoInt escolhe(EleicaoInt eleicaoInt) {
        try {
            if ((r1 = AdminConsole.escolhe(
                    "Não Existem Mesas de Voto. Por favor insira uma.\n",
                    "Escolha a Mesa de Voto:\n" + databaseInt.printFaculdades(),
                    "Por favor insira um número de mesa de voto válido.\n",
                    databaseInt.getFaculdades())) == -1)
                return null;
            return (MesaDeVotoInt) getRegistry(eleicaoInt.getMesaDeVoto(r1));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean gerir(EleicaoInt eleicaoInt) {
        try {
            AdminConsole.gerir(eleicaoInt.printMesasDeVoto() +
                            "O que pretende fazer?:\n" +
                            "1 - Adicionar \n" +
                            "2 - Remover\n" +
                            "3 - Voltar\n",
                    "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                    new int[]{1, 2, 3},
                    new BooleanSupplier[]{
                            () -> Eleicao.addMesaDeVoto(eleicaoInt),
                            () -> Eleicao.removeMesaDeVoto(eleicaoInt)
                    });
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}
