package admin.console.gestores;

import interfaces.DataInt;

import static admin.console.AdminConsole.*;

/**
 * Created by Carlos on 24-10-2017.
 */
public class Data {

    private static int r1;

    public static void editData(String s1, DataInt dataInt) {
        try {
            r1 = 0;
            do {
                if (r1++ != 0) System.out.print("Por favor insira valores válidos para " + s1 + ".\n");
                System.out.print("Insira o ano d" + s1 + ": ");
                dataInt.setAno(sc.nextInt());
                System.out.print("Insira o mês d" + s1 + ": ");
                dataInt.setMes(sc.nextInt());
                System.out.print("Insira o dia d" + s1 + ": ");
                dataInt.setDia(sc.nextInt());
            } while (!dataInt.test());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editHora(String s1, DataInt dataInt) {
        try {
            r1 = 0;
            do {
                if (r1++ != 0) System.out.print("Por favor insira valores válidos para " + s1 + ".\n");
                System.out.print("Insira as horas d" + s1 + ": ");
                dataInt.setHora(sc.nextInt());
                System.out.print("Insira os minutos d" + s1 + ": ");
                dataInt.setMinuto(sc.nextInt());
                System.out.print("Insira os segundo d" + s1 + ": ");
                dataInt.setSegundo(sc.nextInt());
            } while (!dataInt.test());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void edit(String s1, DataInt dataInt) {
        editData(s1, dataInt);
        editHora(s1, dataInt);
    }
}
