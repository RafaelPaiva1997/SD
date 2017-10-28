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
       while (true) {
           try {
               send("ping");
               sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }
}
