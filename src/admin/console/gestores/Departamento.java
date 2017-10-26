package admin.console.gestores;

import admin.console.AdminConsole;
import interfaces.DatabaseInt;
import interfaces.organizacoes.DepartamentoInt;
import interfaces.organizacoes.FaculdadeInt;

import java.rmi.RemoteException;

import static admin.console.AdminConsole.*;
/**
 * Created by Carlos on 24-10-2017.
 */
public class Departamento {

    private static DepartamentoInt departamentoInt;

    private static boolean gerir(DepartamentoInt departamentoInt, FaculdadeInt faculdadeInt){
        try{
            int pos = r1;
            getProperty( departamentoInt.print() +
                            "O que pretende fazer?:\n" +
                            "1 - Editar "+
                            "2 - Apagar\n" +
                            "3 - Voltar\n",
                    "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                    () -> !contains(new int[]{1, 2}, (r1 = sc.nextInt())));

            switch (r1) {
                case 1:
                    edit(departamentoInt);
                    break;

                case 2:
                    faculdadeInt.deleteDepartamento(departamentoInt.getId()) ;
                    break;

                case 3:
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static DepartamentoInt escolhe(FaculdadeInt faculdadeInt){
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

    public static boolean novo(FaculdadeInt faculdadeInt) {
        try {
            departamentoInt = (DepartamentoInt) getRegistry(faculdadeInt.newDepartamento());

            getProperty("Insira o Nome: ",
                    "Por favor insira um nome só com letras",
                    () -> {
                        try {
                            return departamentoInt.setNome(sc.nextLine());
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
            getProperty(departamentoInt.print() + "\nPor favor insira a propriedade a ediar: ",
                    "Por favor insira uma característica correspondente a uma das disponíveis.\n",
                    () -> contains(new String[]{"nome", "pessoas"}, r2 = sc.nextLine()));

            switch (r2) {
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
                    Pessoa.gerir(Pessoa.escolhe(departamentoInt), departamentoInt);
                    break;

            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
