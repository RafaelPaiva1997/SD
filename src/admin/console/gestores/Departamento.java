package admin.console.gestores;

import admin.console.AdminConsole;
import interfaces.organizacoes.DepartamentoInt;
import interfaces.organizacoes.FaculdadeInt;

import java.rmi.RemoteException;
import java.util.function.BooleanSupplier;

import static admin.console.AdminConsole.*;
/**
 * Created by Carlos on 24-10-2017.
 */
public class Departamento {

    private static DepartamentoInt departamentoInt;

    public static DepartamentoInt escolhe(FaculdadeInt faculdadeInt){
        try {
            if ((r1 = AdminConsole.escolhe(
                    "Não Existem departamentos. Por favor insira um.\n",
                    "Escolha o departamento:\n" + faculdadeInt.printDepartamentos(),
                    "Por favor insira um número de Departamento válido.\n",
                    faculdadeInt.getDepartamentos())) == -1)
                return null;
            return  (DepartamentoInt) getRegistry(faculdadeInt.getDepartamento(r1));
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean gerir(FaculdadeInt faculdadeInt){
        try {
            AdminConsole.gerir(faculdadeInt.printDepartamentos() +
                            "O que pretende fazer?:\n" +
                            "1 - Adicionar\n" +
                            "2 - Editar\n" +
                            "3 - Remover\n" +
                            "4 - Voltar\n",
                    "Por favor insira um número correspondente a uma das opcções disponíveis.\n",
                    new int[]{1, 2, 3, 4},
                    new BooleanSupplier[]{
                            () -> novo(faculdadeInt),
                            () -> edit(escolhe(faculdadeInt)),
                            () -> remove(faculdadeInt),
                    });
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean novo(FaculdadeInt faculdadeInt) {
        try {
            departamentoInt = (DepartamentoInt) getRegistry(faculdadeInt.newDepartamento());

            sc.nextLine();

            getProperty("Insira o Nome: ",
                    "Por favor insira um nome só com letras",
                    () -> {
                        try {
                            return !departamentoInt.setNome(sc.nextLine());
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

    public static boolean edit(DepartamentoInt di) {
        departamentoInt = di;
        try {
            sc.nextLine();
            getProperty(departamentoInt.print() + "\nPor favor insira a propriedade a editar: ",
                    "Por favor insira uma característica correspondente a uma das disponíveis.\n",
                    () -> !contains(new String[]{"nome", "pessoas"}, r2 = sc.nextLine()));

            switch (r2.toLowerCase()) {
                case "nome":
                    getProperty("Por favor insira um nome só com letras.\n",
                            () -> {
                                try {
                                    return !departamentoInt.setNome(editProperty("Nome", departamentoInt.getNome()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return false;
                            });
                    break;

                case "pessoas":
                    Pessoa.gerir(departamentoInt);
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
                    edit(departamentoInt);
                    break;

                case 2:
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean remove(FaculdadeInt faculdadeInt) {
        try {
            if ((departamentoInt = escolhe(faculdadeInt)).hasReferences()) {
                getProperty("Este departamento contem as seguintes referências:\n"
                                + departamentoInt.printReferences() +
                                "\nPretende apagá-lo na mesma? ",
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
                faculdadeInt.deleteDepartamento(departamentoInt.getId());
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}
