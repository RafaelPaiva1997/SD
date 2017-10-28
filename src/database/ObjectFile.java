package database;

import java.io.*;
import java.rmi.RemoteException;

public class ObjectFile {

    private Database database;

    public void read() {
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

    public void write() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("database.ser")));
            oos.writeObject(database);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Database getDatabase() {
        return database;
    }
}
