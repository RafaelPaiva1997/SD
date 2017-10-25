package admin.console;

import database.Database;
import interfaces.DataInt;
import interfaces.DatabaseInt;
import interfaces.organizacoes.DepartamentoInt;
import interfaces.organizacoes.FaculdadeInt;
import interfaces.pessoas.AlunoInt;
import interfaces.pessoas.DocenteInt;
import interfaces.pessoas.FuncionarioInt;
import interfaces.pessoas.PessoaInt;
import models.organizacoes.Departamento;
import models.organizacoes.Faculdade;
import models.pessoas.Pessoa;
import
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.function.BooleanSupplier;

/**
 * Created by Carlos on 19-10-2017.
 */
public class AdminConsole {

    public static String ip = "192.168.1.67";
    public static int port = 8000;
    public static Scanner sc = new Scanner(System.in);
    public static int r1;
    public static String r2;

    public static Remote getRegistry(long id) {
        try {
            return LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(id));
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
    public static void getProperty(String s1, String s2, String s3 Boolean)

    public static String editProperty(String p, String v) {
        System.out.print(p + " Antigo: " + v +
                "\n" + p + " Novo: ");
        return sc.nextLine();
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







    private static DatabaseInt escolheEleicao(DatabaseInt databaseInt){
        int i;
        try {
            if ((i = escolhe(
                    "Não Existem pessoa. Por favor insira uma.\n",
                    "Escolha a pessoa:\n" + databaseInt.printEleicoes(),
                    "Por favor insira um número de pessoa válido.\n",
                    databaseInt.getEleicoes())) == -1)
                return null;
            return  (DatabaseInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(databaseInt.getEleicao(i)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean editDepartamento(DepartamentoInt departamentoInt) {
        return true;
    }

    private static boolean editData(DataInt dataInt) {
        return true;
    }

    private static PessoaInt esolhePessoa(DatabaseInt databaseInt) {
        Scanner sc = new Scanner(System.in);
        try {
            if (databaseInt.getFaculdades().isEmpty()) {
                System.out.print("Não Existem Faculdades. Por favor insira uma.\n");
                sc.nextLine();
                return null;
            }


            FaculdadeInt faculdadeInt;
            DepartamentoInt departamentoInt;
            PessoaInt pessoaInt;

            int r1;


            System.out.print("Escolha a faculdade:\n");

            for (int i = 0; i < databaseInt.getFaculdades().size(); i++) {
                faculdadeInt = (FaculdadeInt) getRegistry(databaseInt.getFaculdade(i)));
                System.out.print(i + " - " + faculdadeInt.getNome() + "\n");
            }

            r1 = sc.nextInt();

            while (r1 < 0 || r1 > databaseInt.getFaculdades().size() - 1) {
                System.out.print("Por favor insira um número de faculdade válido.\n");
                r1 = sc.nextInt();
            }

            faculdadeInt = (FaculdadeInt) getRegistry(databaseInt.getFaculdade(r1)));


            if (faculdadeInt.getDepartamentos().isEmpty()) {
                System.out.print("Esta faculdade não tem departamentos. Por favor insira um.");
                return null;
            }


            System.out.print("Escolha o departamento:\n");

            for (int i = 0; i < faculdadeInt.getDepartamentos().size(); i++) {
                departamentoInt = (DepartamentoInt) getRegistry(faculdadeInt.getDepartamento(i)));
                System.out.print(i + " - " + departamentoInt.getNome() + "\n");
            }

            r1 = sc.nextInt();

            while (r1 < 0 || r1 > faculdadeInt.getDepartamentos().size() - 1) {
                System.out.print("Por favor insira um número de departamento válido.\n");
            }

            departamentoInt = (DepartamentoInt) getRegistry(faculdadeInt.getDepartamento(r1)));

            if (faculdadeInt.getDepartamentos().isEmpty()) {
                System.out.print("Este departamento não tem Pessoas, por favor insira uma..");
                return null;
            }


            System.out.print("Escolha a Pessoas a Editar:\n");

            for (int i = 0; i < departamentoInt.getPessoas().size(); i++) {
                pessoaInt = (PessoaInt) getRegistry(departamentoInt.getPessoa(i)));
                System.out.print(i + " - " + pessoaInt.getNome() + "\n");
            }

            r1 = sc.nextInt();

            while (r1 < 0 || r1 > departamentoInt.getPessoas().size() - 1) {
                System.out.print("Por favor insira um número de pessoa válido.\n");
            }

            return (PessoaInt) getRegistry(departamentoInt.getPessoa(r1)));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean ApagarPessoas (Departamento departamentoInt)  {
        Scanner sc = new Scanner(System.in);
        int r1;
        try {
            for (int i = 0; i < departamentoInt.getPessoas().size(); i++) {
                DepartamentoInt pessoaInt = (DepartamentoInt) getRegistry(departamentoInt.getPessoa(i)));
                System.out.print(i + " - " + pessoaInt.getNome() + "\n");
            }
            r1 = sc.nextInt();

            while (r1 < 0 || r1 > departamentoInt.getPessoas().size() - 1) {
                System.out.print("Por favor insira um número de pessoa válido.\n");
            }

            return (PessoaInt) getRegistry(departamentoInt.delete(departamentoInt.delete(departamentoInt.getPessoa(r1)))));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static FaculdadeInt esolheFaculdade(DatabaseInt databaseInt) {
        Scanner sc = new Scanner(System.in);
        try {
            int r1;
            FaculdadeInt faculdadeInt;
            for (int i = 0; i < databaseInt.getFaculdades().size(); i++) {
                faculdadeInt = (FaculdadeInt) getRegistry(databaseInt.getFaculdade(i)));
                System.out.print(i + " - " + faculdadeInt.getNome() + "\n");
            }

            r1 = sc.nextInt();

            while (r1 < 0 || r1 > databaseInt.getFaculdades().size() - 1) {
                System.out.print("Por favor insira um número de faculdade válido.\n");
                r1 = sc.nextInt();
            }

            faculdadeInt = (FaculdadeInt) getRegistry(databaseInt.getFaculdade(r1)));
            return  faculdadeInt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static DepartamentoInt escolheDepartamento(Faculdade faculdadeInt){
        Scanner sc = new Scanner(System.in);
        try {
            int r1;

            DepartamentoInt departamentoInt;
            for (int i = 0; i < faculdadeInt.getDepartamentos().size(); i++) {
                departamentoInt = (DepartamentoInt) getRegistry(faculdadeInt.getDepartamento(i)));
                System.out.print(i + " - " + departamentoInt.getNome() + "\n");
            }

            r1 = sc.nextInt();

            while (r1 < 0 || r1 > faculdadeInt.getDepartamentos().size() - 1) {
                System.out.print("Por favor insira um número de departamento válido.\n");
            }

            departamentoInt = (DepartamentoInt) getRegistry(faculdadeInt.getDepartamento(r1)));
            if (faculdadeInt.getDepartamentos().isEmpty()) {
                System.out.print("Este departamento não tem Pessoas, por favor insira uma..");
                return null;
            }
            return departamentoInt;

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    apagarPessoas(departamentoInt = escolheDepartamento(esolheFaculdade(databaseInt()))));
    private static boolean newFaculdade(DatabaseInt databaseInt) throws RemoteException {
        Scanner sc = new Scanner(System.in);
        try{
            int r1;
            FaculdadeInt faculdadeInt;
            DepartamentoInt departamentoInt;
            faculdadeInt = (FaculdadeInt) getRegistry(databaseInt.newFaculdade(11)));
            System.out.println("Insira um nome.\n");
            sc.nextLine();

            while (!faculdadeInt.setNome(sc.nextLine()))
                System.out.print("Por favor insira um nome correto.\n");


            System.out.print("Deseja adicionar departamentos á faculdade?\n");
            String resposta;
            resposta=sc.nextLine();

            if (resposta.matches("Sim") || resposta.matches("sim") || resposta.matches("s")){
                System.out.println("1-Criar novo departamento" +
                        "2-Adicionar departamentos ja existentes");

                switch (sc.nextInt()) {
                    case 1:

                        System.out.println("oi");

                    case 2:

                        for (int i = 0; i < databaseInt.getDepartamento().size(); i++) {
                            departamentoInt = (DepartamentoInt) getRegistry(databaseInt.getDepartamento(i)));
                            System.out.printf(i + "-" + departamentoInt.getNome());

                        }
                        r1 = sc.nextInt();

                        while (r1 < 0 || r1 > databaseInt.getDepartamento().size() - 1) {
                            System.out.print("Por favor insira um número de departamento válido.\n");
                        }

                        departamentoInt = (DepartamentoInt) getRegistry(databaseInt.getDepartamento(r1)));
                        if (databaseInt.getDepartamento().isEmpty()) {
                            System.out.print("Não existem departamentos\n");
                            return false;
                        }

                }


            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;


    }

    public static boolean newDepartamento(Database databaseInt) throws RemoteException{
        Scanner sc = new Scanner(System.in);
        try{
            int r1;
            FaculdadeInt faculdadeInt;
            DepartamentoInt departamentoInt;
            departamentoInt = (DepartamentoInt) getRegistry(databaseInt.newDepartamento(11)));
            System.out.println("Insira um nome.\n");
            sc.nextLine();

            while (!departamentoInt.setNome(sc.nextLine()))
                System.out.print("Por favor insira um nome correto.\n");

            System.out.print("Indique a faculdade a que pertence");
            for (int i=0;i<databaseInt.getFaculdades().size();i++){
                faculdadeInt =( FaculdadeInt)LocateRegistry.getRegistry((ip,port).lookup(String.valueOf(databaseInt.getFaculdade(i)));
                System.out.println(i + "-" + faculdadeInt.getNome());
            }
            r1 = sc.nextInt();

            while (r1 < 0 || r1 > databaseInt.getDepartamento().size() - 1) {
                System.out.print("Por favor insira um número de departamento válido.\n");
            }

            faculdadeInt = (Faculdade) getRegistry(databaseInt.getFaculdade(r1)));

            if (databaseInt.getDepartamento().isEmpty()) {
                System.out.print("Não existem departamentos\n");
                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            DatabaseInt databaseInt = (DatabaseInt) LocateRegistry.getRegistry((ip, port).lookup("1");
            Scanner sc = new Scanner(System.in);
            int r1;
            while (true) {
                System.out.print("Que fazer?\n" +
                        "1 - Nova Pessoas\n" +
                        "2 - Editar Pessoas\n" +
                        "3 - Apagar Pessoas\n" +
                        "4 - Criar Faculdade\n"+
                        "5 - Criar Departamento\n ");

                while (!Arrays.toString(new int[]{1, 2}).contains(String.valueOf((r1 = sc.nextInt()))))
                    System.out.print("Por favor insira um número correspondente a uma das opções disponíveis.\n");

                switch (r1) {
                    case 1:
                        newPessoa(databaseInt);
                        break;

                    case 2:
                        editPessoa(esolhePessoa(databaseInt));
                        break;

                    case 3:
                        ApagarPessoas(databaseInt);
                        break;


                    case 4:
                        newFaculdade(databaseInt);
                        break;

                    case 5:
                        newDepartamento(databaseInt);
                        break;;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
