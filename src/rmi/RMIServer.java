package rmi;

import database.Database;

import java.net.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.CountDownLatch;

public class RMIServer {

    public static Registry registry;
    public static Database database;
    public static ServerSocket serverSocket;
    public static Socket socket;
    public static String ipDatabase;
    public static String ipTCP;
    public static int portRMI;
    public static int portTCP;
    public static int portDatabase;

    public static void init(Thread thread) {
        init();
        thread.interrupt();
    }

    public static void init() {
        try {
            serverSocket = new ServerSocket(portTCP);
            registry = LocateRegistry.createRegistry(portRMI);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ipDatabase, portDatabase));
            CountDownLatch countDownLatch = new CountDownLatch(1);
            DatabaseHandler databaseHandler = new DatabaseHandler(socket, countDownLatch);
            databaseHandler.start();
            countDownLatch.await();
            if (!database.put())
                System.out.print("database put error\n");
            DataChecker dataChecker = new DataChecker();
            dataChecker.start();
            Primary primary = new Primary(serverSocket);
            primary.start();
            System.out.print("Server is Up!.\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length == 5) {
            ipTCP = args[0];
            portRMI = Integer.parseInt(args[1]);
            portTCP = Integer.parseInt(args[2]);
            ipDatabase = args[3];
            portDatabase = Integer.parseInt(args[4]);
            socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(ipTCP, portTCP), 5000);
                Secundary secundary = new Secundary(socket);
                secundary.start();
            } catch (Exception e) {
                init();
            }
        }
    }
}
