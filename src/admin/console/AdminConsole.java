package admin.console;

import admin.console.gestores.*;
import interfaces.DatabaseInt;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.function.BooleanSupplier;

/**
 * Created by Carlos on 19-10-2017.
 */
public class AdminConsole {

    public static String ip = "192.168.56.1";
    public static int port = 7000;
    public static Scanner sc = new Scanner(System.in);
    public static int r1;
    public static String r2;
    public static DatabaseInt databaseInt;

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

    public static boolean gerir(String s1, String s2, int[] a, BooleanSupplier[] c) {
        while(true) {
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

    public static boolean gerirFaculdades(DatabaseInt databaseInt) {
        try {
            return admin.console.gestores.Faculdade.gerir(databaseInt);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean gerirEleicoes(DatabaseInt databaseInt) {
        try {
            return admin.console.gestores.Eleicao.gerir(databaseInt);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean gerirBaseDados(DatabaseInt databaseInt) {
        try {
            gerir("O que pretende fazer?:\n" +
                            "1 - Gerir Faculdades\n" +
                            "2 - Gerir Eleições\n" +
                            "3 - Adicionar Voto\n" +
                            "4 - Voltar\n",
                    "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                    new int[]{1, 2, 3, 4},
                    new BooleanSupplier[]{
                            () -> gerirFaculdades(databaseInt),
                            () -> gerirEleicoes(databaseInt),
                            () -> Voto.novo(databaseInt),
                    });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        try {
            databaseInt = (DatabaseInt) getRegistry(1);
            gerir("O que pretende fazer?:\n" +
                            "1 - Gerir Base de Dados\n" +
                            "2 - Sair\n",
                    "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                    new int[]{1, 2},
                    new BooleanSupplier[]{
                            () -> gerirBaseDados(databaseInt),
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}