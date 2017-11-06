package admin.console.gestores;

import admin.console.AdminConsole;
import interfaces.DatabaseInt;
import interfaces.eleicoes.EleicaoInt;
import interfaces.ListaInt;
import interfaces.pessoas.PessoaInt;

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

    public static ListaInt escolhe(PessoaInt pessoaInt){
        try {
            if ((r1 = AdminConsole.escolhe(
                    "Não Existem pessoa. Por favor insira uma.\n",
                    "Escolha a pessoa:\n" + pessoaInt.printListas(),
                    "Por favor insira um número de pessoa válido.\n",
                    pessoaInt.getListas())) == -1)
                return null;
            return (ListaInt)  getRegistry(listaInt.getId());
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

    public static boolean gerir(PessoaInt pessoaInt){
        try {
            AdminConsole.gerir(pessoaInt.printListas() +
                            "O que pretende fazer?:\n" +
                            "1 - Adicionar \n" +
                            "2 - Remover\n" +
                            "3 - Voltar\n",
                    "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                    new int[]{1, 2, 3},
                    new BooleanSupplier[]{
                            () -> Pessoa.addLista(pessoaInt),
                            () -> Pessoa.removeLista(pessoaInt)
                    });
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean novo(EleicaoInt eleicaoInt) {
        try {
            getProperty(
                    "Escolha o tipo de Lista a inserir:\n" +
                            "1 - Alunos\n" +
                            "2 - Docentes\n" +
                            "3 - Funcionários\n",
                    "Por favor insira um número correspondente a um dos tipos disponíveis.\n",
                    () -> !contains(new int[]{1, 2, 3}, r1 = sc.nextInt()));

            if (r1 == 1) {
                try {
                    listaInt = (ListaInt) getRegistry(eleicaoInt.newListaAlunos());
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } else if (r1 == 2) {
                try {
                    listaInt = (ListaInt) getRegistry(eleicaoInt.newListaDocentes());
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } else if (r1 == 3){
                try {
                    listaInt = (ListaInt) getRegistry(eleicaoInt.newListaFuncionarios());
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            sc.nextLine();

            getProperty("Insira o Nome: ",
                    "Por favor insira um nome só com letras",
                    () -> {
                        try {
                            return !listaInt.setNome(sc.nextLine());
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

            sc.nextLine();

            getProperty(listaInt.print() + "\nPor favor insira a propriedade a editar: ",
                    "Por favor insira uma característica correspondente a uma das disponíveis.\n",
                    () -> !contains(new String[]{"nome", "pessoas"}, r2 = sc.nextLine()));
            switch (r2.toLowerCase()) {
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

            getProperty(
                    "Quer editar mais alguma Propriedade?\n" +
                            "1 - Sim\n" +
                            "2 - Não\n",
                    "Por favor insira um número correspondente a uma das opções disponíveis.\n",
                    () -> !contains(new int[]{1, 2}, r1 = sc.nextInt()));

            switch (r1) {
                case 1:
                    edit(listaInt);
                    break;

                case 2:
            }
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
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
                        () -> !contains(new String[]{
                                "sim",
                                "não",
                                "nao",
                                "s",
                                "n"
                        }, r2 = sc.nextLine()));
            }
            if (!contains(new String[]{"sim", "s"}, r2))
                databaseInt.deleteFaculdade(listaInt.getId());
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}