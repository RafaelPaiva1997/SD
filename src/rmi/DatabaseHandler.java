package rmi;

import database.Database;

import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.CountDownLatch;

public class DatabaseHandler extends Communicator {

    private CountDownLatch countDownLatch;

    public DatabaseHandler(Socket socket, CountDownLatch countDownLatch) {
        super(socket);
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            send("get");
            RMIServer.database = (Database) receive();
            System.out.print("Database received\n");
            countDownLatch.countDown();

            while (true) {
                send("set");
                send(RMIServer.database);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
