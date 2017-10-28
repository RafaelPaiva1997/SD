package admin.console.gestores;

import admin.console.AdminConsole;
import interfaces.ListaInt;
import interfaces.VotoInt;
import interfaces.eleicoes.EleicaoInt;

import java.rmi.RemoteException;
import java.util.function.BooleanSupplier;

import static admin.console.AdminConsole.*;
/**
 * Created by Johny on 28/10/2017.
 */
public class Voto {
    public static VotoInt escolhe(EleicaoInt eleicaoInt){

        try {
            if ((r1 = AdminConsole.escolhe(
                    "Não Existem votos. Por favor insira um.\n",
                    "Escolha o voto:\n" + eleicaoInt.printVotos(),
                    "Por favor insira um número de voto válido.\n",
                    eleicaoInt.getVotos())) == -1)
                return null;
            return (VotoInt)  getRegistry(eleicaoInt.getVoto(r1));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static boolean gerir(EleicaoInt eleicaoInt){
        try {
            AdminConsole.gerir(eleicaoInt.printVotos() +
                            "O que pretende fazer?:\n" +
                            "2 - Remover\n" +
                            "3 - Voltar\n",
                    "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                    new int[]{1, 2},
                    new BooleanSupplier[]{
                            () -> escolhe(eleicaoInt).d;
                    });
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}
