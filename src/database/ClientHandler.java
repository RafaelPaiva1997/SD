package database;

import rmi.Communicator;

import java.net.Socket;

public class ClientHandler extends Communicator {
    public ClientHandler(Socket socket) {
        super(socket);
    }

    @Override
    public void run() {
        String s;
        System.out.print("Client recieved.\n");
        boolean flag = true;
        while (flag) {
            try {
                if ((s = (String) receive()).equals("set")) {
                    synchronized (socket) {
                        ObjectFile.database = (Database) receive();
                        ObjectFile.write();
                        System.out.print("Database Received\n");
                    }
                } else if (s.equals("get")) {
                    synchronized (socket) {
                        ObjectFile.read();
                        send(ObjectFile.database);
                        System.out.print("Database Sent\n");
                    }
                }

            } catch (Exception e) {
                flag = false;
            }
        }
    }
}
