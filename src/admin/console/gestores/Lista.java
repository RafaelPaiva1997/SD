package admin.console.gestores;

import admin.console.AdminConsole;
import interfaces.DatabaseInt;
import interfaces.eleicoes.EleicaoInt;
import interfaces.ListaInt;
import java.rmi.RemoteException;
import java.util.function.BooleanSupplier;

import static admin.console.AdminConsole.*;
import static admin.console.AdminConsole.databaseInt;

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
        try {
            AdminConsole.gerir(eleicaoInt.printListas() +
                            "O que pretende fazer?:\n" +
                            "1 - Adicionar\n" +
                            "2 - Editar\n" +
                            "3 - Remover\n" +
                            "4 - Voltar\n",
                    "Por favor insira um número correspondente a uma das opcções disponíveis.\n",
                    new int[]{1, 2, 3, 4},
                    new BooleanSupplier[]{
                            () -> novo(eleicaoInt),
                            () -> edit(escolhe(eleicaoInt)),
                            () -> remove(eleicaoInt),
                    });
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean novo(EleicaoInt eleicaoInt) {
        try {
            listaInt = (ListaInt) getRegistry(eleicaoInt.newLista());

            getProperty("Insira o Nome: ",
                    "Por favor insira um nome só com letras",
                    () -> {
                        try {
                            return listaInt.setNome(sc.nextLine());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            return false;
                        }
                    });

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean edit(ListaInt fi) {
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
                    Pessoa.gerir(listaInt);
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean addPessoa(ListaInt listaInt) {
        try {
            return listaInt.addPessoa(Pessoa.escolhe(Departamento.escolhe(Faculdade.escolhe(databaseInt))).getId());
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removePessoa(ListaInt listaInt) {
        try {
            return listaInt.removePessoa(Pessoa.escolhe(listaInt).getId());
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean remove(EleicaoInt eleicaoInt) {
        try {
            if ((listaInt = escolhe(eleicaoInt)).hasReferences()) {
                getProperty("Esta lista contem as seguintes referências:\n"
                                + listaInt.printReferences() +
                                "\nPretende apagá-la na mesma? ",
                        "Por favor insira sim ou não.\n",
                        () -> contains(new String[]{
                                "sim",
                                "não",
                                "nao",
                                "s",
                                "n"
                        }, r2 = sc.nextLine()));
            }
            if (contains(new String[]{"sim", "s"}, r2))
                databaseInt.deleteFaculdade(listaInt.getId());
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}