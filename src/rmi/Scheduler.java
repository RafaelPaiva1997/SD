package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Scheduler {

    public static Registry registry;

    public static void main(String[] args) {
        try {
            registry = LocateRegistry.createRegistry(7000);
            while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
