package admin.console.gestores;

import admin.console.AdminConsole;
import interfaces.DataInt;
import interfaces.ListaInt;
import interfaces.organizacoes.DepartamentoInt;
import interfaces.pessoas.AlunoInt;
import interfaces.pessoas.DocenteInt;
import interfaces.pessoas.FuncionarioInt;
import interfaces.pessoas.PessoaInt;

import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.function.BooleanSupplier;

import static admin.console.AdminConsole.*;

/**
 * Created by Carlos on 24-10-2017.
 */
public class Pessoa {

    private static PessoaInt pessoaInt;
    private static AlunoInt alunoInt;
    private static DocenteInt docenteInt;
    private static FuncionarioInt funcionarioInt;

    public static PessoaInt escolhe(DepartamentoInt departamentoInt){
        try {
            if ((r1 = AdminConsole.escolhe(
                    "Não Existem pessoa. Por favor insira uma.\n",
                    "Escolha a pessoa:\n" + departamentoInt.printPessoas(),
                    "Por favor insira um número de pessoa válido.\n",
                    departamentoInt.getPessoas())) == -1)
                return null;
            return (PessoaInt)  getRegistry(departamentoInt.getPessoa(r1));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PessoaInt escolhe(ListaInt listaInt) {
        try {
            if ((r1 = AdminConsole.escolhe(
                    "Não Existem pessoas. Por favor insira uma.\n",
                    "Escolha a pessoa:\n" + listaInt.printPessoas(),
                    "Por favor insira um número de pessoa válido.\n",
                    listaInt.getPessoas())) == -1)
                return null;
            return (PessoaInt)  getRegistry(listaInt.getPessoa(r1));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void escolheGenero() {
        getProperty(
                "Escolha um Género:\n" +
                        "1 - Masculino\n" +
                        "2 - Femenino\n" +
                        "3 - Outro\n",
                "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                () -> !contains(new int[]{1, 2, 3}, (r1 = sc.nextInt())));

        try {
            switch (r1) {
                case 1:
                    pessoaInt.setGenero("Masculino");
                    break;

                case 2:
                    pessoaInt.setGenero("Feminino");
                    break;

                case 3:
                    pessoaInt.setGenero("Outro");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean gerir(DepartamentoInt departamentoInt) {
        try {
            AdminConsole.gerir(departamentoInt.printPessoas() +
                            "O que pretende fazer?:\n" +
                            "1 - Adicionar\n" +
                            "2 - Editar\n" +
                            "3 - Remover\n" +
                            "4 - Voltar\n",
                    "Por favor insira um número correspondente a uma das opcções disponíveis.\n",
                    new int[]{1, 2, 3, 4},
                    new BooleanSupplier[]{
                            () -> novo(departamentoInt),
                            () -> edit(escolhe(departamentoInt)),
                            () -> remove(departamentoInt),
                    });
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean gerir(ListaInt listaInt){
        try {
            AdminConsole.gerir(listaInt.printPessoas() +
                            "O que pretende fazer?:\n" +
                            "1 - Adicionar \n" +
                            "2 - Remover\n" +
                            "3 - Voltar\n",
                    "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                    new int[]{1, 2, 3},
                    new BooleanSupplier[]{
                            () -> Lista.addPessoa(listaInt),
                            () -> Lista.removePessoa(listaInt)
                });
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean novo(DepartamentoInt departamentoInt) {
        try {
            getProperty(
                    "Escolha o tipo de pessoa a inserir:\n" +
                            "1 - Aluno\n" +
                            "2 - Docente\n" +
                            "3 - Funcionário\n",
                    "Por favor insira um número correspondente a um dos tipos disponíveis.\n",
                    () -> !contains(new int[]{1, 2, 3}, r1 = sc.nextInt()));

            if (r1 == 1) {
                try {
                    pessoaInt = (PessoaInt) getRegistry(departamentoInt.newAluno());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (r1 == 2) {
                try {
                    pessoaInt = (PessoaInt) getRegistry(departamentoInt.newDocente());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    pessoaInt = (PessoaInt) getRegistry(departamentoInt.newFuncionario());
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            sc.nextLine();

            getProperty("Insira o Nome: ",
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

            getProperty("Insira a Password: ",
                    "Por favor insira uma password entre 8 a 20 caracteres.\n",
                    (() -> {
                        try {
                            return !pessoaInt.setPassword(sc.nextLine());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }));

            getProperty("Insira o número de telemóvel: ",
                    "Por favor insira um telemóvel com apenas 9 dígitos.\n",
                    (() -> {
                        try {
                            return !pessoaInt.setTelemovel(sc.nextLine());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }));

            getProperty("Insira uma Morada: ",
                    "Por favor insira pelo menos 1 carater na morada.\n",
                    (() -> {
                        try {
                            return !pessoaInt.setMorada(sc.nextLine());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }));

            getProperty("Insira o Código Postal: ",
                    "Por favor insira um código postal neste formato '0000-000.\n",
                    (() -> {
                        try {
                            return !pessoaInt.setCodigoPostal(sc.nextLine());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }));

            getProperty("Insira Localidade: ",
                    "Por favor insira um telemóvel com pelo menos 1 carater.\n",
                    (() -> {
                        try {
                            return !pessoaInt.setLocalidade(sc.nextLine());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }));

            getProperty("Insira o número do Cartão de Cidadão: ",
                    "Por favor insira um número de cartão de cidadão com apenas 8 digítos.\n",
                    (() -> {
                        try {
                            return !pessoaInt.setNumeroCC(sc.nextLine());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }));


            if (pessoaInt.isAluno()) {
                alunoInt = (AlunoInt) pessoaInt;

                getProperty("Insira o Número de Aluno: ",
                        "Por favor insira um número de aluno com apenas 10 digitos.\n",
                        (() -> {
                            try {
                                return !alunoInt.setNumeroAluno(sc.nextLine());
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            return false;
                        }));

                getProperty("Insira o Curso: ",
                        "Por favora insira o nome do curso usando apenas letras.\n",
                        (() -> {
                            try {
                                return !alunoInt.setCurso(sc.nextLine());
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            return false;
                        }));
            }

            if (pessoaInt.isDocente()) {
                docenteInt = (DocenteInt) pessoaInt;

                getProperty("Insira o Cargo: ",
                        "Por favora insira o cargo usando apenas letras.\n",
                        (() -> {
                            try {
                                return !docenteInt.setCargo(sc.nextLine());
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            return false;
                        }));
            }

            if (pessoaInt.isFuncionario()) {
                funcionarioInt = (FuncionarioInt) pessoaInt;

                getProperty("Insira a Função: ",
                        "Por favora insira a função usando apenas letras.\n",
                        (() -> {
                            try {
                                return !funcionarioInt.setFuncao(sc.nextLine());
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            return false;
                        }));
            }

            Data.editData("a validade do cartão de cidadão", (DataInt) getRegistry(pessoaInt.getValidadeCCInt()));
            Data.editData("a data de nascimento", (DataInt) getRegistry(pessoaInt.getDataNascimentoInt()));

            escolheGenero();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean edit(PessoaInt pi) {
        pessoaInt = pi;
        try {
            getProperty(pessoaInt.print() + "\nPor favor insira a propriedade a editar: ",
                    "Por favor insira uma característica correspondente a uma das disponíveis.\n",
                    () -> {
                        try {
                            return contains(new String[]{
                                    "nome",
                                    "username",
                                    "password",
                                    "nº telemóvel",
                                    "nº telemovel",
                                    "no telemóvel",
                                    "no telemovel",
                                    "morada",
                                    "código postal",
                                    "codigo postal",
                                    "localidade",
                                    "número c.c.",
                                    "numero c.c.",
                                    "número cc",
                                    "numero cc",
                                    "validade c.c.",
                                    "validade cc",
                                    "género",
                                    "genero",
                                    "data nascimento"
                            }, r2 = sc.nextLine()) ||
                                    pessoaInt.isAluno() && contains(new String[]{
                                            "nºaluno",
                                            "no aluno",
                                            "curso"
                                    }, r2) ||
                                    pessoaInt.isFuncionario() && contains(new String[]{
                                            "função",
                                            "funçao",
                                            "funcão",
                                            "funcao"
                                    }, r2) ||
                                    pessoaInt.isDocente() && r2.toLowerCase().equals("cargo");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return false;
                    });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        switch (r2.toLowerCase()) {
            case "nome":
                getProperty("Por favor insira um nome só com letras.\n",
                        () -> {
                            try {
                                return !pessoaInt.setNome(editProperty("Nome", pessoaInt.getNome()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        });
                break;
            case "username":
                getProperty("Por favor insira um username com entre 8 a 20 caracteres.\n",
                        () -> {
                            try {
                                return !pessoaInt.setUsername(editProperty("Nome", pessoaInt.getUsername()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        });
                break;
            case "password":
                getProperty("Por favor insira uma password com entre 8 a 20 caracteres.\n",
                        () -> {
                            try {
                                return !pessoaInt.setPassword(editProperty("Password", pessoaInt.getPassword()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        });
                break;
            case "nº telemóvel":
            case "nº telemovel":
            case "no telemóvel":
            case "no telemovel":
                getProperty("Por favor insira um número de telemóvel com apenas 9 dígitos.\n",
                        () -> {
                            try {
                                return !pessoaInt.setTelemovel(editProperty("Nº Telemóvel", String.valueOf(pessoaInt.getTelemovel())));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        });
            case "morada":
                getProperty("Por favor insira pelo menos 1 carater na morada.\n",
                        () -> {
                            try {
                                return !pessoaInt.setMorada(editProperty("Morada", pessoaInt.getMorada()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        });
            case "código postal":
                getProperty("Por favor insira um código postal no formato 0000-000.\n",
                        () -> {
                            try {
                                return !pessoaInt.setCodigoPostal(editProperty("Codigo Postal", pessoaInt.getCodigoPostal()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        });
            case "localidade":
                getProperty("Por favor insira uma localidade com pelo menos 1 caractér.\n",
                        () -> {
                            try {
                                return !pessoaInt.setLocalidade(editProperty("Localidade", pessoaInt.getLocalidade()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        });
            case "número c.c.":
            case "numero c.c.":
            case "número cc":
            case "numero cc":
                getProperty("Por favor insira um código postal no formato 0000-000.\n",
                        () -> {
                            try {
                                return !pessoaInt.setNumeroCC(editProperty("Codigo Postal", String.valueOf(pessoaInt.getNumeroCC())));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        });
            case "validade c.c.":
            case "validade cc":
                try {
                    Data.editData("a validade do cartão de cidadão", (DataInt) getRegistry(pessoaInt.getValidadeCCInt()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "género":
            case "genero":
                try {
                    System.out.print("Género Antigo: " + pessoaInt.getGenero());
                    escolheGenero();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                escolheGenero();
                break;
            case "data nascimento":
                try {
                    Data.editData("a data de nascimento", (DataInt) getRegistry(pessoaInt.getDataNascimentoInt()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "nº aluno":
            case "numero de aluno":
                alunoInt = (AlunoInt) pessoaInt;
                getProperty("Por favor insira um número de aluno com apenas 10 digitos.\n",
                        () -> {
                            try {
                                return (!alunoInt.setNumeroAluno(editProperty("Nº Aluno", String.valueOf(alunoInt.getNumeroAluno()))));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        });
            case "curso":
                alunoInt = (AlunoInt) pessoaInt;
                getProperty("Por favor insira um curso com pelo menos 1 caractér.\n",
                        () -> {
                            try {
                                return (!alunoInt.setCurso(editProperty("Curso", alunoInt.getCurso())));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        });
            case "cargo":
                docenteInt = (DocenteInt) pessoaInt;
                getProperty("Por favor insira um cargo com pelo menos 1 caractér.\n",
                        () -> {
                            try {
                                return (!docenteInt.setCargo(editProperty("Cargo", docenteInt.getCargo())));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        });
            case "função":
            case "funçao":
            case "funcão":
            case "funcao":
                funcionarioInt = (FuncionarioInt) pessoaInt;
                getProperty("Por favor insira um cargo com pelo menos 1 caractér.\n",
                        () -> {
                            try {
                                return (!funcionarioInt.setFuncao(editProperty("Funcção", funcionarioInt.getFuncao())));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        });
        }

        getProperty(
                "Quer editar mais alguma Propriedade?\n" +
                        "1 - Sim\n" +
                        "2 - Não\n",
                "Por favor insira um número correspondente a uma das opções disponíveis.\n",
                () -> contains(new int[]{1, 2}, r1 = sc.nextInt()));

        switch (r1) {
            case 1:
                edit(pessoaInt);
                break;

            case 2:
        }
        return true;
    }

    public static boolean remove(DepartamentoInt departamentoInt) {
        try {
            if ((pessoaInt = escolhe(departamentoInt)).hasReferences()) {
                getProperty("Esta pessoa esta registada em:\n"
                                + pessoaInt.printReferences() +
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
                departamentoInt.deletePessoa(pessoaInt.getId());
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}