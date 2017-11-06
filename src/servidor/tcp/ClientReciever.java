package servidor.tcp;

import interfaces.eleicoes.EleicaoInt;
import interfaces.pessoas.PessoaInt;

import java.net.ServerSocket;
import java.util.LinkedList;

public class ClientReciever extends Thread {
    private ServerSocket serverSocket;
    private int port;
    private final LinkedList<ClientHandler> clientHandlers;

    public ClientReciever(int port) {
        this.port = port;
        clientHandlers = new LinkedList<>();
    }

    @Override
    public void run() {
        boolean flag = true;
        try {
            serverSocket = new ServerSocket(port);
            while (flag) {
                clientHandlers.add(new ClientHandler(serverSocket.accept()));
                clientHandlers.getLast().start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
    }

    public boolean hasFreeHandler() {
        for (ClientHandler clientHandler : clientHandlers)
            if (!clientHandler.isBusy())
                return true;
        return false;
    }

    public ClientHandler getFirstFree() {
        for (ClientHandler clientHandler : clientHandlers)
            if (!clientHandler.isBusy())
                return clientHandler;
        return null;
    }

    public void newVoter(PessoaInt pessoaInt, EleicaoInt eleicaoInt) {
        ClientHandler clientHandler;
        (clientHandler = getFirstFree()).setPessoaInt(pessoaInt);
        clientHandler.setEleicaoInt(eleicaoInt);
        clientHandler.getCountDownLatch().countDown();
    }
}
