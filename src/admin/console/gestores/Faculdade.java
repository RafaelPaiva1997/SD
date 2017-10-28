package admin.console.gestores;

import admin.console.AdminConsole;
import interfaces.DatabaseInt;
import interfaces.organizacoes.FaculdadeInt;

import java.rmi.RemoteException;
import java.util.function.BooleanSupplier;

import static admin.console.AdminConsole.*;
/**
 * Created by Carlos on 24-10-2017.
 */
public class Faculdade {

    private static FaculdadeInt faculdadeInt;

    public static FaculdadeInt escolhe(DatabaseInt databaseInt) {
        try {
            if ((r1 = AdminConsole.escolhe(
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

    public static boolean gerir(DatabaseInt databaseInt) throws RemoteException {
        try {
            AdminConsole.gerir(databaseInt.printFaculdades() +
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
            switch (r2) {
                case "nome":
                    getProperty("Por favor insira um nome só com letras.\n",
                            () -> {
                                try {
                                    return !faculdadeInt.setNome(editProperty("Nome", faculdadeInt.getNome()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return false;
                            });
                    break;

                case "departamentos":
                    Departamento.gerir(faculdadeInt);
                    break;

            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean remove(DatabaseInt databaseInt) {
        try {
            if ((faculdadeInt = escolhe(databaseInt)).hasReferences()) {
                getProperty("Esta faculdade contem as seguintes referências:\n"
                                + faculdadeInt.printReferences() +
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
                databaseInt.deleteFaculdade(faculdadeInt.getId());
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}
