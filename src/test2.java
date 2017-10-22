import interfaces.DatabaseInt;
import interfaces.organizacoes.DepartamentoInt;
import interfaces.organizacoes.FaculdadeInt;
import interfaces.pessoas.PessoaInt;

import java.rmi.registry.LocateRegistry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Carlos on 19-10-2017.
 */
public class test2 {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("^[ A-Za-z]+$");
        Matcher m = p.matcher("aaaaab 2");
        boolean b = m.matches();
        System.out.print(b);
        /*try {
            DatabaseInt databaseInt = (DatabaseInt) LocateRegistry.getRegistry("192.168.85.1", 8000).lookup("1");
            FaculdadeInt faculdadeInt = (FaculdadeInt) LocateRegistry.getRegistry("192.168.85.1", 8000).lookup(String.valueOf(databaseInt.getFaculdade(0)));
            DepartamentoInt departamentoInt = (DepartamentoInt) LocateRegistry.getRegistry("192.168.85.1", 8000).lookup(String.valueOf(faculdadeInt.getDepartamento(0)));
            PessoaInt pessoaInt = (PessoaInt) LocateRegistry.getRegistry("192.168.85.1", 8000).lookup(String.valueOf(departamentoInt.getPessoa(0)));
            System.out.print(pessoaInt.getNome());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
