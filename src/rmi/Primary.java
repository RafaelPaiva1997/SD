package rmi;

import java.net.ServerSocket;

public class Primary extends Communicator {
    public Primary(ServerSocket serverSocket) {
        super(serverSocket);
    }

    @Override
    public void run() {
        boolean flag;
        Object o;
        try {
            while (true) {
                flag = true;
                socket = serverSocket.accept();
                System.out.print((String) receive());
                send("Primary says: connected to secundary!\n");
                Pinger p = new Pinger(socket, this);
                p.start();
                while (flag) {
                    if ((o = receive()) == null || !o.equals("ping") || pings > 5) {
                        pings = 0;
                        System.out.print("Secundary server appears to be down\n");
                        flag = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
