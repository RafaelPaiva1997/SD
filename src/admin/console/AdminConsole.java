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

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Carlos on 19-10-2017.
 */
public class AdminConsole {

    private static String ip = "192.168.1.67";
    private static int port = 8000;

    private static boolean newPessoa(DatabaseInt databaseInt) {
        Scanner sc = new Scanner(System.in);
        try {
            if (databaseInt.getFaculdades().isEmpty()) {
                System.out.print("Não Existem Faculdades. Por favor insira uma.");
                sc.nextLine();
                return false;
            }



            FaculdadeInt faculdadeInt;
            DepartamentoInt departamentoInt;
            PessoaInt pessoaInt;
            DataInt dataInt;
            AlunoInt alunoInt;
            DocenteInt docenteInt;
            FuncionarioInt funcionarioInt;
            int r1;



            System.out.print("Escolha a faculdade:\n");

            for(int i = 0; i < databaseInt.getFaculdades().size(); i++) {
                faculdadeInt = (FaculdadeInt) LocateRegistry.getRegistry(ip, port).lookup(String .valueOf(databaseInt.getFaculdade(i)));
                System.out.print(i + " - " + faculdadeInt.getNome() + "\n");
            }

            r1 = sc.nextInt();

            while (r1 < 0 || r1 > databaseInt.getFaculdades().size() - 1) {
                System.out.print("Por favor insira um número de faculdade válido.\n");
                r1 = sc.nextInt();
            }

            faculdadeInt = (FaculdadeInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(databaseInt.getFaculdade(r1)));



            if (faculdadeInt.getDepartamentos().isEmpty()) {
                System.out.print("Esta faculdade não tem departamentos. Por favor insira um.");
                return false;
            }



            System.out.print("Escolha o departamento:\n");

            for(int i = 0; i < faculdadeInt.getDepartamentos().size(); i++) {
                departamentoInt = (DepartamentoInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(faculdadeInt.getDepartamento(i)));
                System.out.print(i + " - " + departamentoInt.getNome() + "\n");
            }

            r1 = sc.nextInt();

            while (r1 < 0 || r1 > faculdadeInt.getDepartamentos().size() - 1) {
                System.out.print("Por favor insira um número de departamento válido.\n");
            }

            departamentoInt = (DepartamentoInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(faculdadeInt.getDepartamento(r1)));



            System.out.print(
                    "Escolha o tipo de pessoa a inserir:\n" +
                            "1 - Aluno\n" +
                            "2 - Docente\n" +
                            "3 - Funcionário\n"
            );

            pessoaInt = null;

            while(!Arrays.toString(new int[]{1,2,3}).contains(String.valueOf((r1 = sc.nextInt()))))
                System.out.print("Por favor insira um número correspondente a um dos tipos disponíveis.\n");

            switch (r1) {
                case 1:
                    pessoaInt = (PessoaInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(departamentoInt.newAluno(11)));
                    break;

                case 2:
                    pessoaInt = (PessoaInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(departamentoInt.newDocente(11)));
                    break;

                case 3:
                    pessoaInt = (PessoaInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(departamentoInt.newFuncionario(11)));
                    break;
            }
            System.out.print("Insira o Nome: ");
            sc.nextLine();


            while (!pessoaInt.setNome(sc.nextLine()))
                System.out.print("Por favor insira um nome só com letras.\n");

            System.out.print("Insira o Username: ");
            while (!pessoaInt.setUsername(sc.nextLine()))
                System.out.print("Por favor insira um username com entre 8 a 20 caracteres.\n");

            System.out.print("Insira a Password: ");
            while (!pessoaInt.setPassword(sc.nextLine()))
                System.out.print("Por favor insira uma password com entre 8 a 20 caracteres.\n");

            System.out.print("Insira o número de telemovél: ");
            while (!pessoaInt.setTelemovel(sc.nextLine()))
                System.out.print("Por favor insira um número de telemóvel com apenas 9 dígitos.\n");

            System.out.print("Insira a Morada: ");
            while (!pessoaInt.setMorada(sc.nextLine()))
                System.out.print("Por favor insira pelo menos 1 caracter na morada.\n");

            System.out.print("Insira Código Postal: ");
            while (!pessoaInt.setCodigoPostal(sc.nextLine()))
                System.out.print("Por favor insira um código postal neste formato '0000-000'.\n");

            System.out.print("Insira Localidade: ");
            while (!pessoaInt.setLocalidade(sc.nextLine()))
                System.out.print("Por favor insira uma localidade com pelo menos 1 caractér.\n");

            System.out.print("Insira o número do Cartão de Cidadão: ");
            while (!pessoaInt.setNumeroCC(sc.nextLine()))
                System.out.print("Por favor insira um número de cartão de cidadão com apenas 8 dígitos.\n");

            if (pessoaInt.isAluno()) {
                alunoInt = (AlunoInt) pessoaInt;
                System.out.print("Insira o Número de Aluno: ");
                while (!alunoInt.setNumeroAluno(sc.nextLine()))
                    System.out.print("Por favor insira um número de aluno com apenas 10 digitos.\n");

                System.out.print("Insira um Curso: ");
                while (!alunoInt.setCurso(sc.nextLine()))
                    System.out.print("Por favora insira o nome do curso usando apenas letras.\n");
            }

            if (pessoaInt.isDocente()) {
                docenteInt = (DocenteInt) pessoaInt;
                System.out.print("Insira o Cargo: ");
                while (!docenteInt.setCargo(sc.nextLine()))
                    System.out.print("Por favor insira um cargo com pelo menos 1 caractér.\n");
            }

            if (pessoaInt.isFuncionario()) {
                funcionarioInt = (FuncionarioInt)  pessoaInt;
                System.out.print("Insira a Função: ");
                while(!funcionarioInt.setFuncao(sc.nextLine()))
                    System.out.print("Por favor insira uma função com pelo menos 1 caractér.\n");
            }


            dataInt = (DataInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(pessoaInt.getValidadeCC()));
            r1 = 0;
            do {
                if(r1++ != 0) System.out.print("Por favor insira valores válidos para a validade do cartão de cidadão.\n");
                System.out.print("Insira o ano da validade do cartão de cidadão: ");
                dataInt.setAno(sc.nextInt());
                System.out.print("Insira o mês da validade do cartão de cidadão: ");
                dataInt.setMes(sc.nextInt());
                System.out.print("Insira o dia da validade do cartão de cidadão: ");
                dataInt.setDia(sc.nextInt());
            } while(!dataInt.test());

            dataInt = (DataInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(pessoaInt.getDataNascimento()));
            r1 = 0;
            do {
                if(r1++ != 0) System.out.print("Por favor insira valores válidos para a data de nascimento.\n");
                System.out.print("Insira o ano da data de nascimento: ");
                dataInt.setAno(sc.nextInt());
                System.out.print("Insira o mês da data de nascimento: ");
                dataInt.setMes(sc.nextInt());
                System.out.print("Insira o dia da data de nascimento: ");
                dataInt.setDia(sc.nextInt());
            } while(!dataInt.test());

            System.out.print(
                    "Escolha um Género:\n" +
                            "1 - Masculino\n" +
                            "2 - Femenino\n" +
                            "3 - Outro\n"
            );

            while(!Arrays.toString(new int[]{1,2,3}).contains(String.valueOf((r1 = sc.nextInt()))))
                System.out.print("Por favor insira um número correspondente a um dos géneros disponíveis.\n");

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
        return true;
    }

    private static String editProperty(Scanner sc, String p, String v) {
        System.out.print(p + " Antigo: " + v +
                "\n" + p + " Novo: ");
        return sc.nextLine();
    }

    private static boolean editPessoa(PessoaInt pessoaInt) {
        Scanner sc = new Scanner(System.in);
        try {
            AlunoInt alunoInt;
            DocenteInt docenteInt;
            FuncionarioInt funcionarioInt;

            int r1;
            String r2;

            System.out.print(pessoaInt.print() + "\nPor favor insira a propriedade a editar: ");

            while (!(Arrays.toString(new String[]{
                    "nome",
                    "username",
                    "password",
                    "departamento",
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
            }).contains(String.valueOf((r2 = sc.nextLine()).toLowerCase()).toLowerCase()) ||
                    pessoaInt.isAluno() && Arrays.toString(new String[]{
                            "nºaluno",
                            "no aluno",
                            "curso"
                    }).contains(String.valueOf((r2)).toLowerCase()) ||
                    pessoaInt.isFuncionario() && Arrays.toString(new String[]{
                            "função",
                            "funçao",
                            "funcão",
                            "funcao"
                    }).contains(String.valueOf((r2)).toLowerCase()) ||
                    pessoaInt.isDocente() && "cargo".equals(String.valueOf((r2)).toLowerCase())))
                System.out.print("Por favor insira uma característica correspondente a uma das disponíveis.\n");

            switch (r2.toLowerCase()) {
                case "nome":
                    while (!pessoaInt.setNome(editProperty(sc, "Nome", pessoaInt.getNome())))
                        System.out.print("Por favor insira um nome só com letras.\n");
                    break;
                case "username":
                    while (!pessoaInt.setUsername(editProperty(sc, "Username", pessoaInt.getUsername())))
                        System.out.print("Por favor insira um username com entre 8 a 20 caracteres.\n");
                    break;
                case "password":
                    while (!pessoaInt.setPassword(editProperty(sc, "Password", pessoaInt.getPassword())))
                        System.out.print("Por favor insira uma password com entre 8 a 20 caracteres.\n");
                    break;
                case "departamento":
                    editDepartamento((DepartamentoInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(pessoaInt.getDepartamentoInt())));
                    break;
                case "nº telemóvel":
                case "nº telemovel":
                case "no telemóvel":
                case "no telemovel":
                    while (!pessoaInt.setTelemovel(editProperty(sc, "Nº Telemóvel", String.valueOf(pessoaInt.getTelemovel()))))
                        System.out.print("Por favor insira um número de telemóvel com apenas 9 dígitos.\n");
                    break;
                case "morada":
                    while (!pessoaInt.setMorada(editProperty(sc, "Morada", pessoaInt.getMorada())))
                        System.out.print("Por favor insira pelo menos 1 caracter na morada.\n");
                    break;
                case "código postal":
                case "codigo postal":
                    while (!pessoaInt.setCodigoPostal(editProperty(sc, "Código Postal", pessoaInt.getCodigoPostal())))
                        System.out.print("Por favor insira um código postal neste formato '0000-000'.\n");
                    break;
                case "localidade":
                    while (!pessoaInt.setLocalidade(editProperty(sc, "Localidade", pessoaInt.getLocalidade())))
                        System.out.print("Por favor insira uma localidade com pelo menos 1 caractér.\n");
                    break;
                case "número c.c.":
                case "numero c.c.":
                case "número cc":
                case "numero cc":
                    while (!pessoaInt.setNumeroCC(editProperty(sc, "Número C.C.", String.valueOf(pessoaInt.getNumeroCC()))))
                        System.out.print("Por favor insira um número de cartão de cidadão com apenas 8 dígitos.\n");
                    break;
                case "validade c.c.":
                case "validade cc":
                    editData((DataInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(pessoaInt.getValidadeCC())));
                    break;
                case "género":
                case "genero":
                    System.out.print(
                            "Género Antigo: " + pessoaInt.getGenero() +
                                    "Escolha um Género:\n" +
                                    "1 - Masculino\n" +
                                    "2 - Femenino\n" +
                                    "3 - Outro\n"
                    );

                    while (!Arrays.toString(new int[]{1, 2, 3}).contains(String.valueOf((r1 = sc.nextInt()))))
                        System.out.print("Por favor insira um número correspondente a um dos géneros disponíveis.\n");

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
                    break;
                case "data nascimento":
                    editData((DataInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(pessoaInt.getDataNascimento())));
                    break;
                case "nº aluno":
                case "no aluno":
                    alunoInt = (AlunoInt) pessoaInt;
                    while (!alunoInt.setNumeroAluno(editProperty(sc, "Nº Aluno", String.valueOf(alunoInt.getNumeroAluno()))))
                        System.out.print("Por favor insira um número de aluno com apenas 10 digitos.\n");
                    break;
                case "curso":
                    alunoInt = (AlunoInt) pessoaInt;
                    while (!alunoInt.setCurso(editProperty(sc, "Curso", alunoInt.getCurso())))
                        System.out.print("Por favora insira o nome do curso usando apenas letras.\n");
                    break;
                case "cargo":
                    docenteInt = (DocenteInt) pessoaInt;
                    while (!docenteInt.setCargo(editProperty(sc, "Cargo", docenteInt.getCargo())))
                        System.out.print("Por favor insira um cargo com pelo menos 1 caractér.\n");
                    break;
                case "função":
                case "funçao":
                case "funcão":
                case "funcao":
                    funcionarioInt = (FuncionarioInt) pessoaInt;
                    while (!funcionarioInt.setFuncao(editProperty(sc, "Função", funcionarioInt.getFuncao())))
                        System.out.print("Por favor insira uma função com pelo menos 1 caractér.\n");
                    break;
            }

            System.out.print("Quer editar mais alguma Propriedade?\n" +
                    "1 - Sim\n" +
                    "2 - Não\n");

            while (!Arrays.toString(new int[]{1, 2}).contains(String.valueOf((r1 = sc.nextInt()))))
                System.out.print("Por favor insira um número correspondente a uma das opções disponíveis.\n");

            switch (r1) {
                case 1:
                    editPessoa(pessoaInt);
                    break;

                case 2:
                    return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
                faculdadeInt = (FaculdadeInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(databaseInt.getFaculdade(i)));
                System.out.print(i + " - " + faculdadeInt.getNome() + "\n");
            }

            r1 = sc.nextInt();

            while (r1 < 0 || r1 > databaseInt.getFaculdades().size() - 1) {
                System.out.print("Por favor insira um número de faculdade válido.\n");
                r1 = sc.nextInt();
            }

            faculdadeInt = (FaculdadeInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(databaseInt.getFaculdade(r1)));


            if (faculdadeInt.getDepartamentos().isEmpty()) {
                System.out.print("Esta faculdade não tem departamentos. Por favor insira um.");
                return null;
            }


            System.out.print("Escolha o departamento:\n");

            for (int i = 0; i < faculdadeInt.getDepartamentos().size(); i++) {
                departamentoInt = (DepartamentoInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(faculdadeInt.getDepartamento(i)));
                System.out.print(i + " - " + departamentoInt.getNome() + "\n");
            }

            r1 = sc.nextInt();

            while (r1 < 0 || r1 > faculdadeInt.getDepartamentos().size() - 1) {
                System.out.print("Por favor insira um número de departamento válido.\n");
            }

            departamentoInt = (DepartamentoInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(faculdadeInt.getDepartamento(r1)));

            if (faculdadeInt.getDepartamentos().isEmpty()) {
                System.out.print("Este departamento não tem Pessoas, por favor insira uma..");
                return null;
            }


            System.out.print("Escolha a Pessoa a Editar:\n");

            for (int i = 0; i < departamentoInt.getPessoas().size(); i++) {
                pessoaInt = (PessoaInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(departamentoInt.getPessoa(i)));
                System.out.print(i + " - " + pessoaInt.getNome() + "\n");
            }

            r1 = sc.nextInt();

            while (r1 < 0 || r1 > departamentoInt.getPessoas().size() - 1) {
                System.out.print("Por favor insira um número de pessoa válido.\n");
            }

            return (PessoaInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(departamentoInt.getPessoa(r1)));

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
                DepartamentoInt pessoaInt = (DepartamentoInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(departamentoInt.getPessoa(i)));
                System.out.print(i + " - " + pessoaInt.getNome() + "\n");
            }
            r1 = sc.nextInt();

            while (r1 < 0 || r1 > departamentoInt.getPessoas().size() - 1) {
                System.out.print("Por favor insira um número de pessoa válido.\n");
            }

            return (PessoaInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(departamentoInt.delete(departamentoInt.delete(departamentoInt.getPessoa(r1)))));

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
                faculdadeInt = (FaculdadeInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(databaseInt.getFaculdade(i)));
                System.out.print(i + " - " + faculdadeInt.getNome() + "\n");
            }

            r1 = sc.nextInt();

            while (r1 < 0 || r1 > databaseInt.getFaculdades().size() - 1) {
                System.out.print("Por favor insira um número de faculdade válido.\n");
                r1 = sc.nextInt();
            }

            faculdadeInt = (FaculdadeInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(databaseInt.getFaculdade(r1)));
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
                departamentoInt = (DepartamentoInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(faculdadeInt.getDepartamento(i)));
                System.out.print(i + " - " + departamentoInt.getNome() + "\n");
            }

            r1 = sc.nextInt();

            while (r1 < 0 || r1 > faculdadeInt.getDepartamentos().size() - 1) {
                System.out.print("Por favor insira um número de departamento válido.\n");
            }

            departamentoInt = (DepartamentoInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(faculdadeInt.getDepartamento(r1)));
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
            faculdadeInt = (FaculdadeInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(databaseInt.newFaculdade(11)));
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
                            departamentoInt = (DepartamentoInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(databaseInt.getDepartamento(i)));
                            System.out.printf(i + "-" + departamentoInt.getNome());

                        }
                        r1 = sc.nextInt();

                        while (r1 < 0 || r1 > databaseInt.getDepartamento().size() - 1) {
                            System.out.print("Por favor insira um número de departamento válido.\n");
                        }

                        departamentoInt = (DepartamentoInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(databaseInt.getDepartamento(r1)));
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
            departamentoInt = (DepartamentoInt) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(databaseInt.newDepartamento(11)));
            System.out.println("Insira um nome.\n");
            sc.nextLine();

            while (!departamentoInt.setNome(sc.nextLine()))
                System.out.print("Por favor insira um nome correto.\n");

            System.out.print("Indique a faculdade a que pertence");
            for (int i=0;i<databaseInt.getFaculdades().size();i++){
                faculdadeInt =( FaculdadeInt)LocateRegistry.getRegistry(ip,port).lookup(String.valueOf(databaseInt.getFaculdade(i)));
                System.out.println(i + "-" + faculdadeInt.getNome());
            }
            r1 = sc.nextInt();

            while (r1 < 0 || r1 > databaseInt.getDepartamento().size() - 1) {
                System.out.print("Por favor insira um número de departamento válido.\n");
            }

            faculdadeInt = (Faculdade) LocateRegistry.getRegistry(ip, port).lookup(String.valueOf(databaseInt.getFaculdade(r1)));

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
            DatabaseInt databaseInt = (DatabaseInt) LocateRegistry.getRegistry(ip, port).lookup("1");
            Scanner sc = new Scanner(System.in);
            int r1;
            while (true) {
                System.out.print("Que fazer?\n" +
                        "1 - Nova Pessoa\n" +
                        "2 - Editar Pessoa\n" +
                        "3 - Apagar Pessoa\n" +
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
