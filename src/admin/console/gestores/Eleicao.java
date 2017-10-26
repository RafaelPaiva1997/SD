package admin.console.gestores;

import admin.console.AdminConsole;
import interfaces.DataInt;
import interfaces.DatabaseInt;
import interfaces.eleicoes.DirecaoDepartamentoInt;
import interfaces.eleicoes.DirecaoFaculdadeInt;
import interfaces.eleicoes.EleicaoInt;
import interfaces.eleicoes.NucleoEstudantesInt;
import models.eleicoes.NucleoEstudantes;

import java.rmi.RemoteException;

import static admin.console.AdminConsole.*;
/**
 * Created by Carlos on 24-10-2017.
 */
public class Eleicao {

    private static EleicaoInt eleicaoInt;

    public static EleicaoInt escolhe(DatabaseInt databaseInt) {
        try {
            if ((r1 = AdminConsole.escolhe(
                    "Não Existem Faculdades. Por favor insira uma.\n",
                    "Escolha a faculdade:\n" + databaseInt.printFaculdades(),
                    "Por favor insira um número de faculdade válido.\n",
                    databaseInt.getFaculdades())) == -1)
                return null;
            return (EleicaoInt) getRegistry(databaseInt.getEleicao(r1));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean gerir(DatabaseInt databaseInt, EleicaoInt eleicaoInt) {
        try {
            int pos = r1;
            getProperty(eleicaoInt.print() +
                            "O que pretende fazer?:\n" +
                            "1 - Editar " +
                            "2 - Apagar\n" +
                            "3 - Voltar\n",
                    "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                    () -> !contains(new int[]{1, 2}, (r1 = sc.nextInt())));


            switch (r1) {
                case 1:
                    edit(eleicaoInt);
                    break;

                case 2:
                    databaseInt.deleteEleicao(eleicaoInt.getId());
                    break;

                case 3:

            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
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

            } else if (r1 == 4) {
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

            Data.edit("a data de fim", (DataInt) getRegistry(eleicaoInt.getDataInicioInt()));

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
            getProperty(eleicaoInt.print() + "\nPor favor insira a propriedade a editar: ",
                    "Por favor insira uma característica correspondente a uma das disponíveis.\n",
                    () -> contains(new String[]{"título", "descrição", "data de inicío", "data de fim"}, r2 = sc.nextLine()));

            switch (r2.toLowerCase()) {
                case "título":
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
                    getProperty("Por favor insira uma descrição só com letras.\n",
                            () -> {
                                try {
                                    return !eleicaoInt.setDescricao(editProperty("Descrição", eleicaoInt.getDescricao()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return false;
                            });
                case "data de inicío":
                    try {
                        Data.edit("a data de inicio.\n",(DataInt) getRegistry(eleicaoInt.getDataInicioInt()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                case "data de fim":
                    try {
                        Data.edit("a data de fim\n",(DataInt)getRegistry(eleicaoInt.getDataFimInt()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}