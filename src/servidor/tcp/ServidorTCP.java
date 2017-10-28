package servidor.tcp;

import java.net.ServerSocket;
import java.util.LinkedList;

public class ServidorTCP {

    private static ServerSocket serverSocket;
    private static LinkedList<ClientHandler> clientHandlers;

    public static void main(String[] args) {
        boolean flag = true;
        try {
            serverSocket = new ServerSocket(8000);
            while (flag) {
                clientHandlers.add(new ClientHandler(serverSocket.accept()));
                clientHandlers.getLast().run();
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
    }
}
