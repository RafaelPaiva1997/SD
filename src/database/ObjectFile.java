package database;

import java.io.*;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class ObjectFile {

    private static ServerSocket serverSocket;
    public static Database database;
    private static int port;
    private static LinkedList<ClientHandler> clientHandlers;

    public static void read() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("database.ser"));
            database = (Database) ois.readObject();
            ois.close();
        } catch (Exception e) {
            try {
                database = new Database(1);
            } catch (RemoteException re) {
                re.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public static void write() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("database.ser")));
            oos.writeObject(database);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Database getDatabase() {
        return database;
    }

    public static void setDatabase(Database database) {
        ObjectFile.database = database;
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                read();
                clientHandlers = new LinkedList<>();
                port = Integer.parseInt(args[0]);
                serverSocket = new ServerSocket(port);
                System.out.print("Base de Dados Operacional.\n");
                while (true) {
                    clientHandlers.add(new ClientHandler(serverSocket.accept()));
                    clientHandlers.getLast().start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
