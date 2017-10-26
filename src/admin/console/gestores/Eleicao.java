import admin.console.gestores.Data;
import database.Database;
import interfaces.DataInt;
import interfaces.DatabaseInt;
import interfaces.ModelInt;
import interfaces.organizacoes.DepartamentoInt;
import interfaces.organizacoes.FaculdadeInt;
import interfaces.eleicoes.EleicaoInt;

import java.rmi.RemoteException;

import static admin.console.AdminConsole.*;
import static admin.console.AdminConsole.r1;

/**
 * Created by Carlos on 24-10-2017.
 */
public class Eleicao {

    private static EleicaoInt eleicaoInt;

    public static boolean novo(DatabaseInt databaseInt) {
        try {
            getProperty(
                    "Escolha o tipo de elicao inserir:\n" +
                            "1 - Conselho geral\n" +
                            "2 - Núcleo de estudantes\n" +
                            "3 - Direcção de faculdade\n" +
                            "4 - Direcção de departamento\n",
                    "Por favor insira um número correspondente a um dos tipos disponíveis.\n",
                    () -> !contains(new int[]{1, 2}, r1 = sc.nextInt()));
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

            getProperty("Insira hora de inicio: ",
                    "Por favor insira um nome só com letras.\n",
                    (() -> {
                        try {
                            return !pessoaInt.setNome(sc.nextLine());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }));

            getProperty("Insira o Username: ",
                    "Por favor insira um username com entre 8 a 20 caracteres.\n",
                    (() -> {
                        try {
                            return !pessoaInt.setUsername(sc.nextLine());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }));

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
                    break;
            }

        } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
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
                        Data.edit("Data de inicio.\n",(DataInt) getRegistry(eleicaoInt.getDataInicioInt()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                case "data de fim":
                    try {
                        Data.edit("Data de fim\n",(DataInt)getRegistry(eleicaoInt.getDataFimInt()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}