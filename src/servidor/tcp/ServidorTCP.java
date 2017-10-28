package servidor.tcp;

import interfaces.DatabaseInt;
import interfaces.MesaDeVotoInt;
import interfaces.eleicoes.EleicaoInt;
import interfaces.organizacoes.DepartamentoInt;
import interfaces.organizacoes.FaculdadeInt;
import interfaces.pessoas.PessoaInt;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.function.BooleanSupplier;

public class ServidorTCP {

    private static ClientReciever clientReciever;
    private static MesaDeVotoInt mesaDeVotoInt;
    private static DatabaseInt databaseInt;
    private static EleicaoInt eleicaoInt;
    private static String ipRMI;
    private static int portRMI;
    private static int portTCP;
    private static int r1;
    private static String r2;
    private static Scanner sc;
    private static String username;
    private static String password;

    public static Remote getRegistry(long id) {
        try {
            return LocateRegistry.getRegistry(ipRMI, portRMI).lookup(String.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getProperty(String s1, BooleanSupplier call) {
        while (call.getAsBoolean())
            System.out.print(s1);
    }

    public static void getProperty(String s1, String s2, BooleanSupplier call) {
        System.out.print(s1);
        getProperty(s2, () -> call.getAsBoolean());
    }

    public static boolean contains(String[] a, String s) {
        return Arrays.toString(a).toLowerCase().contains(s.toLowerCase());
    }

    public static boolean contains(int[] a, int i) {
        return Arrays.toString(a).toLowerCase().contains(String.valueOf(i).toLowerCase());
    }

    public static int escolhe(String s1, String s2, String s3, LinkedList l) {
        Scanner sc = new Scanner(System.in);
        try {
            int r1;

            if (l.isEmpty()) {
                System.out.print(s1);
                sc.nextLine();
                return -1;
            }

            System.out.print(s2);

            r1 = sc.nextInt();

            while (r1 < 0 || r1 > l.size() - 1) {
                System.out.print(s3);
                r1 = sc.nextInt();
            }

            return r1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean gerir(String s1, String s2, int[] a, BooleanSupplier[] c) {
        while (true) {
            getProperty(s1, s2, () -> !contains(a, (r1 = sc.nextInt())));

            if (r1 - 1 == c.length) return true;

            for (int i = 0; i < c.length; i++) {
                if (i == r1 - 1) {
                    c[i].getAsBoolean();
                    i = c.length;
                }
            }
        }
    }


    public static DepartamentoInt escolhe(FaculdadeInt faculdadeInt) {
        try {
            if ((r1 = escolhe(
                    "Não Existem departamentos. Por favor insira um.\n",
                    "Escolha o departamento:\n" + faculdadeInt.printDepartamentos(),
                    "Por favor insira um número de Departamento válido.\n",
                    faculdadeInt.getDepartamentos())) == -1)
                return null;
            return (DepartamentoInt) getRegistry(faculdadeInt.getDepartamento(r1));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FaculdadeInt escolhe(DatabaseInt databaseInt) {
        try {
            if ((r1 = escolhe(
                    "Não Existem Faculdades. Por favor insira uma.\n",
                    "Escolha a faculdade:\n" + databaseInt.printFaculdades(),
                    "Por favor insira um número de faculdade válido.\n",
                    databaseInt.getFaculdades())) == -1)
                return null;
            return (FaculdadeInt) getRegistry(databaseInt.getFaculdade(r1));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static EleicaoInt escolhe(MesaDeVotoInt mesaDeVotoInt) {
        try {
            if ((r1 = escolhe(
                    "Não Existem eleições. Por favor insira uma.\n",
                    "Escolha a eleição:\n" + mesaDeVotoInt.printEleicoes(),
                    "Por favor insira um número de eleição válido.\n",
                    mesaDeVotoInt.getEleicoes())) == -1)
                return null;
            return (EleicaoInt) getRegistry(mesaDeVotoInt.getEleicao(r1));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void login() {
        System.out.print("Insira o username: ");
        username = sc.nextLine();
        System.out.print("Insira a password: ");
        password = sc.nextLine();
        try {
            if (mesaDeVotoInt.login(username, password))
                System.out.print("Login bem sucedido.");
            else
                System.out.print("Login falhou ou utilizador ja se encontra loggado.\n");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static String printPessoas(LinkedList<Long> pessoas) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < pessoas.size(); i++) {
            try {
                out.append(i + "->" + ((PessoaInt) getRegistry(pessoas.get(i))).inLinePrint());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return out.toString();
    }

    public static void main(String[] args) {
        if (args.length == 3) {
            ipRMI = args[0];
            portRMI = Integer.parseInt(args[1]);
            portTCP = Integer.parseInt(args[2]);
            sc = new Scanner(System.in);
            clientReciever = new ClientReciever(portTCP);
            clientReciever.start();
            databaseInt = (DatabaseInt) getRegistry(1);
            System.out.print("Escolha o departamento desta mesa de voto:\n");
            try {
                mesaDeVotoInt = (MesaDeVotoInt) getRegistry(((DepartamentoInt) escolhe(escolhe(databaseInt))).getMesaDeVotoInt());
                mesaDeVotoInt.shutdown();
                while (!mesaDeVotoInt.isWorking())
                    login();
                while (true) {
                    System.out.print("Aceitar novo votante: ");
                    if (mesaDeVotoInt.getEleicoes().size() > 1)
                        eleicaoInt = escolhe(mesaDeVotoInt);
                    else
                        eleicaoInt = (EleicaoInt) getRegistry(mesaDeVotoInt.getEleicao(0));
                    getProperty("Procurar pessoa por uma das seguintes caracteristicas:\n" +
                                    "nome, username, password, nº telemóvel, morada\n" +
                                    "código postal, localidade, número c.c.\n",
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
                                    }, r2 = sc.nextLine());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return false;
                            });

                    LinkedList<Long> pessoas = new LinkedList<>();

                    switch (r2.toLowerCase()) {
                        case "nome":
                            System.out.print("Insira o nome: ");
                            pessoas = databaseInt.searchPessoa("nome-" + sc.nextLine());
                            break;
                        case "username":
                            System.out.print("Insira o username: ");
                            pessoas = databaseInt.searchPessoa("username-" + sc.nextLine());
                            break;
                        case "password":
                            System.out.print("Insira a password: ");
                            pessoas = databaseInt.searchPessoa("password-" + sc.nextLine());
                            break;
                        case "nº telemóvel":
                        case "nº telemovel":
                        case "no telemóvel":
                        case "no telemovel":
                            System.out.print("Insira o nº telemóvel: ");
                            pessoas = databaseInt.searchPessoa("nº telemóvel-" + sc.nextLine());
                            break;
                        case "morada":
                            System.out.print("Insira o morada: ");
                            pessoas = databaseInt.searchPessoa("morada-" + sc.nextLine());
                            break;
                        case "código postal":
                            System.out.print("Insira o código postal: ");
                            pessoas = databaseInt.searchPessoa("código postal-" + sc.nextLine());
                            break;
                        case "localidade":
                            System.out.print("Insira o localidade: ");
                            pessoas = databaseInt.searchPessoa("localidade-" + sc.nextLine());
                            break;
                        case "número c.c.":
                        case "numero c.c.":
                        case "número cc":
                        case "numero cc":
                            System.out.print("número c.c.: ");
                            pessoas = databaseInt.searchPessoa("número c.c.-" + sc.nextLine());
                            break;
                    }

                    r1 = escolhe("", "Escolha a pessoa:\n" + printPessoas(pessoas), "Por favor insira um número de pessoa válido.\n",
                            pessoas);

                    while (!clientReciever.hasFreeHandler()) {
                        System.out.print("Todos os terminais de voto estão ocupados.\n" +
                                "Por favor espere que um deles fique livre e carregue Enter.\n");
                        sc.nextLine();
                    }

                    clientReciever.newVoter((PessoaInt) getRegistry(pessoas.get(r1)), eleicaoInt);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
