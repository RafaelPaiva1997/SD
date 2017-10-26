package admin.console.gestores;

import admin.console.AdminConsole;
import interfaces.DatabaseInt;
import interfaces.organizacoes.FaculdadeInt;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Scanner;

import static admin.console.AdminConsole.*;
/**
 * Created by Carlos on 24-10-2017.
 */
public class Faculdade {

    private static FaculdadeInt faculdadeInt;


    public static boolean gerir(FaculdadeInt faculdadeInt,DatabaseInt databaseInt) throws RemoteException {
        try{
            int pos = r1;
            getProperty( faculdadeInt.print() +
                            "O que pretende fazer?:\n" +
                            "1 - Editar "+
                            "2 - Apagar\n" +
                            "3 - Voltar\n",
                    "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                    () -> !contains(new int[]{1, 2}, (r1 = sc.nextInt())));

            switch (r1) {
                case 1:
                    edit(faculdadeInt);
                    break;

                case 2:
                    databaseInt.deleteFaculdade(faculdadeInt.getId());
                    break;

                case 3:
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }

    }


    public static FaculdadeInt escolhe(DatabaseInt databaseInt) {
        try {
            if ((r1 = AdminConsole.escolhe(
                    "Não Existem Faculdades. Por favor insira uma.\n",
                    "Escolha a faculdade:\n" + databaseInt.printFaculdades(),
                    "Por favor insira um número de faculdade válido.\n",
                    databaseInt.getFaculdades())) == -1)
                return null;
            return  (FaculdadeInt) getRegistry(databaseInt.getFaculdade(r1));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean novo(DatabaseInt databaseInt) {
        try {
            faculdadeInt = (FaculdadeInt) getRegistry(databaseInt.newFaculdade());

            getProperty("Insira o Nome: ",
                    "Por favor insira um nome só com letras",
                    () -> {
                        try {
                            return faculdadeInt.setNome(sc.nextLine());
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

    public static boolean edit(FaculdadeInt fi) {
        faculdadeInt = fi;
        try {
            getProperty(faculdadeInt.print() + "\nPor favor insira a propriedade a ediar: ",
                    "Por favor insira uma característica correspondente a uma das disponíveis.\n",
                    () -> contains(new String[]{"nome", "departamentos"}, r2 = sc.nextLine()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
