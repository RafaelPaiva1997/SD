package admin.console.gestores;

import admin.console.AdminConsole;
import interfaces.DataInt;
import interfaces.DatabaseInt;
import interfaces.MesaDeVotoInt;
import interfaces.eleicoes.DirecaoDepartamentoInt;
import interfaces.eleicoes.DirecaoFaculdadeInt;
import interfaces.eleicoes.EleicaoInt;
import interfaces.eleicoes.NucleoEstudantesInt;

import java.rmi.RemoteException;
import java.util.function.BooleanSupplier;

import static admin.console.AdminConsole.*;

/**
 * Created by Carlos on 24-10-2017.
 */
public class Eleicao {

    private static EleicaoInt eleicaoInt;

    public static EleicaoInt escolhe(DatabaseInt databaseInt) {
        try {
            if ((r1 = AdminConsole.escolhe(
                    "Não Existem eleições. Por favor insira um.\n",
                    "Escolha a eleição:\n" + databaseInt.printEleicoes(),
                    "Por favor insira um número de eleição válido.\n",
                    databaseInt.getEleicoes())) == -1)
                return null;
            return (EleicaoInt) getRegistry(databaseInt.getEleicao(r1));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean gerir(DatabaseInt databaseInt) {
        try {
            AdminConsole.gerir(databaseInt.printEleicoes() +
                            "O que pretende fazer?:\n" +
                            "1 - Adicionar\n" +
                            "2 - Editar\n" +
                            "3 - Remover\n" +
                            "4 - Voltar\n",
                    "Por favor insira um número correspondente a uma das opcções disponíveis.\n",
                    new int[]{1, 2, 3, 4},
                    new BooleanSupplier[]{
                            () -> novo(databaseInt),
                            () -> edit(escolhe(databaseInt)),
                            () -> remove(databaseInt),
                    });
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean novo(DatabaseInt databaseInt) {
        try {
            getProperty(
                    "Escolha o tipo de elicao inserir:\n" +
                            "1 - Conselho geral\n" +
                            "2 - Núcleo de estudantes\n" +
                            "3 - Direcção de faculdade\n" +
                            "4 - Direcção de departamento\n",
                    "Por favor insira um número correspondente a um dos tipos disponíveis.\n",
                    () -> !contains(new int[]{1, 2, 3, 4}, r1 = sc.nextInt()));

            if (r1 == 1) {
                try {
                    eleicaoInt = (EleicaoInt) getRegistry(databaseInt.newConselhoGeral());
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

            } else if (r1 == 2) {
                try {
                    eleicaoInt = (EleicaoInt) getRegistry(databaseInt.newNucleoEstudantes());
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

            } else if (r1 == 3) {
                try {
                    eleicaoInt = (EleicaoInt) getRegistry(databaseInt.newDirecaoFaculdade());
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

            } else {
                try {
                    eleicaoInt = (EleicaoInt) getRegistry(databaseInt.newDirecaoDepartamento());
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            sc.nextLine();

            getProperty("Insira o título: ",
                    "Por favor insira um nome só com letras.\n",
                    (() -> {
                        try {
                            return !eleicaoInt.setTitulo(sc.nextLine());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }));

            getProperty("Insira a descrição: ",
                    "Por favor insira uma descrição só com letras.\n",
                    (() -> {
                        try {
                            return !eleicaoInt.setDescricao(sc.nextLine());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }));
            sc.nextLine();

            Data.edit("a data de inicio", (DataInt) getRegistry(eleicaoInt.getDataInicioInt()));

            Data.edit("a data de fim", (DataInt) getRegistry(eleicaoInt.getDataFimInt()));

            if (eleicaoInt.isNucleoEstudantes()) {
                NucleoEstudantesInt nucleoEstudantesInt = (NucleoEstudantesInt) eleicaoInt;
                nucleoEstudantesInt.setDepartamento(Departamento.escolhe(Faculdade.escolhe(databaseInt)).getId());
            }

            if (eleicaoInt.isDirecaoDepartamento()) {
                DirecaoDepartamentoInt direcaoDepartamentoInt = (DirecaoDepartamentoInt) eleicaoInt;
                direcaoDepartamentoInt.setDepartamento(Departamento.escolhe(Faculdade.escolhe(databaseInt)).getId());
            }

            if (eleicaoInt.isDirecaoFaculdade()) {
                DirecaoFaculdadeInt direcaoFaculdadeInt = (DirecaoFaculdadeInt) eleicaoInt;
                direcaoFaculdadeInt.setFaculdade(Faculdade.escolhe(databaseInt).getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean edit(EleicaoInt ei) {
        eleicaoInt = ei;
        try {
            if (eleicaoInt.isRunning()) {
                System.out.print("Eleição a decorrer!\n");
                return false;
            }

            sc.nextLine();

            getProperty(eleicaoInt.print() + "\nPor favor insira a propriedade a editar: ",
                    "Por favor insira uma característica correspondente a uma das disponíveis.\n",
                    () -> !contains(new String[]{
                            "título",
                            "titulo",
                            "descrição",
                            "descricão",
                            "descriçao",
                            "descricao",
                            "data de inicío",
                            "data de inicio",
                            "data de fim",
                            "listas",
                            "mesas de voto",
                            "votos"
                    }, r2 = sc.nextLine()));

            switch (r2.toLowerCase()) {
                case "título":
                case "titulo":
                    getProperty("Por favor insira um título só com letras.\n",
                            () -> {
                                try {
                                    return !eleicaoInt.setTitulo(editProperty("Título", eleicaoInt.getTitulo()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return false;
                            });
                    break;

                case "descrição":
                case "descricão":
                case "descriçao":
                case "descricao":
                    getProperty("Por favor insira uma descrição só com letras.\n",
                            () -> {
                                try {
                                    return !eleicaoInt.setDescricao(editProperty("Descrição", eleicaoInt.getDescricao()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return false;
                            });
                    break;

                case "data de inicío":
                case "data de inicio":
                    try {
                        Data.edit("a data de inicio", (DataInt) getRegistry(eleicaoInt.getDataInicioInt()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case "data de fim":
                    try {
                        Data.edit("a data de fim", (DataInt) getRegistry(eleicaoInt.getDataFimInt()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case "listas":
                    Lista.gerir(eleicaoInt);
                    break;

                case "mesas de voto":
                    MesadeVoto.gerir(eleicaoInt);
                    break;

                case "votos":
                    Voto.gerir(eleicaoInt);
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
                    edit(eleicaoInt);
                    break;

                case 2:
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addMesaDeVoto(EleicaoInt eleicaoInt) {
        try {
            return eleicaoInt.addMesaDeVoto(((MesaDeVotoInt) getRegistry(Departamento.escolhe(Faculdade.escolhe(databaseInt)).getMesaDeVotoInt())).getId());
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeMesaDeVoto(EleicaoInt eleicaoInt) {
        try {
            return eleicaoInt.removeMesaDeVoto(MesadeVoto.escolhe(eleicaoInt).getId());
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean remove(DatabaseInt databaseInt) {
        try {
            if ((eleicaoInt = escolhe(databaseInt)).hasReferences()) {
                getProperty("Esta eleição contem as seguintes referências:\n"
                                + eleicaoInt.printReferences() +
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
                databaseInt.deleteFaculdade(eleicaoInt.getId());
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}