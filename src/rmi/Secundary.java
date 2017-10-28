package rmi;

import java.net.Socket;

public class Secundary extends Communicator {

    public Secundary(Socket socket) {
        super(socket);
    }

    @Override
    public void run() {
        Object o;
        try {
            send("Secundaty says: connected to primary!\n");
            System.out.print((String) receive());
            Pinger p = new Pinger(socket, this);
            while (!Thread.currentThread().isInterrupted()) {
                if ((o = receive()) == null || !o.equals("ping") || --pings > 5) {
                    System.out.print("Primary Server appears to be down.\n" +
                            "Taking over as primary\n");
                    RMIServer.init(Thread.currentThread());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
