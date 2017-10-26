package rmi;

import database.Database;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {

    public static Registry registry;
    public static Database database;

    public static void main(String[] args) {
        try {
            registry = LocateRegistry.createRegistry(7000);
            database = new Database();
            database.setId(1);
            if (!database.put())
                System.out.print("database put error\n");

        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }
}
