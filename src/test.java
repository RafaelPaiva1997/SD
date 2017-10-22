import interfaces.DatabaseInt;
import interfaces.organizacoes.DepartamentoInt;
import interfaces.organizacoes.FaculdadeInt;
import models.organizacoes.Faculdade;

import java.rmi.registry.LocateRegistry;
import java.util.Arrays;

/**
 * Created by Carlos on 19-10-2017.
 */
public class test {
    public static void main(String[] args) {
        try {
            DatabaseInt databaseInt = (DatabaseInt) LocateRegistry.getRegistry("192.168.85.1", 7000).lookup("1");
            FaculdadeInt faculdadeInt = (FaculdadeInt) LocateRegistry.getRegistry("192.168.85.1", 7000).lookup(String.valueOf(databaseInt.newFaculdade(3)));
            faculdadeInt.setNome("FCTUC");
            DepartamentoInt departamentoInt = (DepartamentoInt) LocateRegistry.getRegistry("192.168.85.1", 7000).lookup(String.valueOf(faculdadeInt.newDepartamento(4)));
            departamentoInt.setNome("DEI");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
