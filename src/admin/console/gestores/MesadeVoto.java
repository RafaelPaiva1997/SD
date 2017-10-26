package admin.console.gestores;

import interfaces.MesaDeVotoInt;
import interfaces.eleicoes.EleicaoInt;

import java.rmi.RemoteException;

import static admin.console.AdminConsole.*;
import static admin.console.AdminConsole.r1;

/**
 * Created by Johny on 26/10/2017.
 */
public class MesadeVoto {
    MesaDeVotoInt mesaDeVotoInt;
    public static boolean gerir(MesaDeVotoInt mesaDeVotoInt){
        try {
            getProperty( mesaDeVotoInt.print() +
                            "O que pretende fazer?:\n" +
                            "1 - Adicionar\n" +
                            "2 - Remover\n" +
                            "3 - Voltar\n",
                    "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                    () -> !contains(new int[]{1, 2}, (r1 = sc.nextInt())));

            switch (r1) {
                case 1:
                    break;

                case 2:
                    eleicaoInt.deleteLista(listaInt.getId());
                    break;
                case 3:

            }
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
    }
}
