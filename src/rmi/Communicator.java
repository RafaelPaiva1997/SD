package rmi;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Communicator extends Thread {

    protected ServerSocket serverSocket;
    protected Socket socket;
    protected ObjectInputStream ois;
    protected ObjectOutputStream oos;
    protected int pings;

    public Communicator() {
    }

    public Communicator(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        pings = 0;
    }

    public Communicator(Socket socket) {
        this.socket = socket;
        pings = 0;
    }

    @Override
    public abstract void run();

    public void send(Object object) throws Exception {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(object);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    public Object receive() {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            return ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }
}
