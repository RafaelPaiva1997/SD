package admin.console.gestores;

import admin.console.AdminConsole;
import interfaces.DataInt;
import interfaces.DatabaseInt;
import interfaces.eleicoes.DirecaoDepartamentoInt;
import interfaces.eleicoes.DirecaoFaculdadeInt;
import interfaces.eleicoes.EleicaoInt;
import interfaces.eleicoes.NucleoEstudantesInt;
import interfaces.pessoas.PessoaInt;
import models.eleicoes.NucleoEstudantes;
import interfaces.ListaInt;
import java.rmi.RemoteException;

import static admin.console.AdminConsole.*;
/**
 * Created by Johny on 26/10/2017.
 */
public class Lista {
    private static ListaInt listaInt;

    public static ListaInt escolhe(EleicaoInt eleicaoInt) {
        try {
            if ((r1 = AdminConsole.escolhe(
                    "Não Existem listas. Por favor insira uma.\n",
                    "Escolha a lista:\n" + eleicaoInt.printListas(),
                    "Por favor insira um número de uma lista válida.\n",
                    eleicaoInt.getListas())) == -1)
                return null;
            return (ListaInt) getRegistry(listaInt.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean gerir(EleicaoInt eleicaoInt){

    }
}



    public static boolean gerir(ListaInt listaInt, EleicaoInt eleicaoInt) throws RemoteException {
        try {
            int pos = r1;
            getProperty(listaInt.print() +
                            "O que pretende fazer?:\n" +
                            "1 - Editar " +
                            "2 - Apagar\n" +
                            "3 - Voltar\n",
                    "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                    () -> !contains(new int[]{1, 2, 3}, (r1 = sc.nextInt())));

            switch (r1) {
                case 1:
                    edit(listaInt, eleicaoInt);
                    break;

                case 2:
                    eleicaoInt.deleteLista(listaInt.getId());
                    break;

                case 3:
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean edit(ListaInt fi, EleicaoInt eleicaoInt) {
        listaInt = fi;
        try {
            getProperty(listaInt.print() + "\nPor favor insira a propriedade a ediar: ",
                    "Por favor insira uma característica correspondente a uma das disponíveis.\n",
                    () -> contains(new String[]{"nome", "pessoas"}, r2 = sc.nextLine()));
            switch (r2) {
                case "nome":
                    getProperty("Por favor insira um nome só com letras.\n",
                            () -> {
                                try {
                                    return !listaInt.setNome(editProperty("Nome", listaInt.getNome()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return false;
                            });
                    break;
                case "pessoas":
                    PessoaInt pessoaInt;
                    pessoaInt = Pessoa.escolhe(listaInt);
                    Pessoa.gerir(pessoaInt,listaInt,eleicaoInt);
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return true;
    }