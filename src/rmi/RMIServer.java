package rmi;

import database.Database;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {

    public static Registry registry;
    public static Database database;
    public static String ip = "localhost";
    public static int port = 7000;

    public static void main(String[] args) {
        try {
            registry = LocateRegistry.createRegistry(port);
            database = new Database(1);
            database.setId(1);
            if (!database.put())
                System.out.print("database put error\n");

        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }
}
