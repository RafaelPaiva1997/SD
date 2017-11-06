package admin.console.gestores;

import admin.console.AdminConsole;
import interfaces.DatabaseInt;
import interfaces.ListaInt;
import interfaces.MesaDeVotoInt;
import interfaces.VotoInt;
import interfaces.eleicoes.EleicaoInt;
import interfaces.pessoas.PessoaInt;
import models.*;
import models.listas.*;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.function.BooleanSupplier;

import static admin.console.AdminConsole.*;
/**
 * Created by Johny on 28/10/2017.
 */
public class Voto {
    public static VotoInt escolhe(EleicaoInt eleicaoInt){

        try {
            if ((r1 = AdminConsole.escolhe(
                    "Não Existem votos. Por favor insira um.\n",
                    "Escolha o voto:\n" + eleicaoInt.printVotos(),
                    "Por favor insira um número de voto válido.\n",
                    eleicaoInt.getVotos())) == -1)
                return null;
            return (VotoInt)  getRegistry(eleicaoInt.getVoto(r1));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static VotoInt escolhe(ListaInt listaInt){

        try {
            if ((r1 = AdminConsole.escolhe(
                    "Não Existem votos. Por favor insira um.\n",
                    "Escolha o voto:\n" + listaInt.printVotos(),
                    "Por favor insira um número de voto válido.\n",
                    listaInt.getVotos())) == -1)
                return null;
            return (VotoInt)  getRegistry(listaInt.getVoto(r1));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static VotoInt escolhe(MesaDeVotoInt mesaDeVotoInt){

        try {
            if ((r1 = AdminConsole.escolhe(
                    "Não Existem votos. Por favor insira um.\n",
                    "Escolha o voto:\n" + mesaDeVotoInt.printVotos(),
                    "Por favor insira um número de voto válido.\n",
                    mesaDeVotoInt.getVotos())) == -1)
                return null;
            return (VotoInt)  getRegistry(mesaDeVotoInt.getVoto(r1));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static VotoInt escolhe(PessoaInt pessoaInt){

        try {
            if ((r1 = AdminConsole.escolhe(
                    "Não Existem votos. Por favor insira um.\n",
                    "Escolha o voto:\n" + pessoaInt.printVotos(),
                    "Por favor insira um número de voto válido.\n",
                    pessoaInt.getVotos())) == -1)
                return null;
            return (VotoInt)  getRegistry(pessoaInt.getVoto(r1));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean gerir(EleicaoInt eleicaoInt) {
        try {
            AdminConsole.gerir(eleicaoInt.printVotos() +
                            "O que pretende fazer?:\n" +
                            "1 - Remover\n" +
                            "2 - Voltar\n",
                    "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                    new int[]{1, 2},
                    new BooleanSupplier[]{
                            () -> remove(eleicaoInt)
                    });
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean gerir(ListaInt listaInt) {
        try {
            AdminConsole.gerir(listaInt.printVotos() +
                            "O que pretende fazer?:\n" +
                            "1 - Remover\n" +
                            "2 - Voltar\n",
                    "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                    new int[]{1, 2},
                    new BooleanSupplier[]{
                            () -> remove(listaInt)
                    });
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean gerir(MesaDeVotoInt mesaDeVotoInt) {
        try {
            AdminConsole.gerir(mesaDeVotoInt.printVotos() +
                            "O que pretende fazer?:\n" +
                            "1 - Remover\n" +
                            "2 - Voltar\n",
                    "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                    new int[]{1, 2},
                    new BooleanSupplier[]{
                            () -> remove(mesaDeVotoInt)
                    });
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean gerir(PessoaInt pessoaInt) {
        try {
            AdminConsole.gerir(pessoaInt.printVotos() +
                            "O que pretende fazer?:\n" +
                            "2 - Remover\n" +
                            "3 - Voltar\n",
                    "Por favor insira um número correspondente a um dos géneros disponíveis.\n",
                    new int[]{1, 2},
                    new BooleanSupplier[]{
                            () -> remove(pessoaInt)
                    });
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean remove(EleicaoInt eleicaoInt) {
        try {
            escolhe(eleicaoInt).delete();
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean remove(ListaInt listaInt) {
        try {
            escolhe(listaInt).delete();
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean remove(MesaDeVotoInt mesaDeVotoint) {
        try {
            escolhe(mesaDeVotoint).delete();
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean remove(PessoaInt pessoaInt) {
        try {
            escolhe(pessoaInt).delete();
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean novo(DatabaseInt databaseInt) {
        try {
            PessoaInt pessoaInt = Pessoa.escolhe(Departamento.escolhe(Faculdade.escolhe(databaseInt)));
            EleicaoInt eleicaoInt = Eleicao.escolhe(databaseInt);
            /*if (eleicaoInt.isFinish()) {
                System.out.print("Eleição já terminou!.\n");
                return false;
            }*/
            int n = eleicaoInt.getListas().size();
            System.out.print("Escolha o seu voto:\n" +
                    eleicaoInt.printListas() +
                     n + "->Voto em Branco" +
                    "\n" + (n + 1) + "->Voto Nulo" +
                    "\n" + "Insira a sua opção: ");
            while ((r1 = sc.nextInt()) < 0 || r1 > n + 1)
                System.out.print("Por favor insira um número correspondente a uma das opcções disponíveis.\n");
            VotoInt v = (VotoInt) getRegistry(databaseInt.newVoto());
            if (r1 >= 0 && r1 < n)
                v.novo(pessoaInt.getId(), eleicaoInt.getId(), ((ListaInt) getRegistry(eleicaoInt.getLista(r1))).getId(), -1);
            else if (r1 == n)
                v.novoBranco(pessoaInt.getId(), eleicaoInt.getId(), -1);
            else
                v.novoNulo(pessoaInt.getId(), eleicaoInt.getId(), -1);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return true;
    }
}
