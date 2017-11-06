package rmi;

import java.net.Socket;

public class Pinger extends Communicator {

    private Communicator communicator;

    public Pinger(Socket socket, Communicator communicator) {
        super(socket);
        this.communicator = communicator;
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
           try {
                send("ping");
                sleep(1000);
           } catch (Exception e) {
                flag = false;
                e.printStackTrace();
           }
        }
    }
}
