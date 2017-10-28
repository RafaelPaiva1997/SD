package servidor.tcp;

import interfaces.eleicoes.EleicaoInt;
import interfaces.pessoas.PessoaInt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class ClientHandler extends Thread{

    private Socket socket;
    private boolean busy;
    private CountDownLatch countDownLatch;
    private PessoaInt pessoaInt;
    private EleicaoInt eleicaoInt;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        busy = false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                countDownLatch = new CountDownLatch(1);
                send("Terminal de Voto Ligado.\n");
                System.out.print(receive());
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isBusy() {
        return busy;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setPessoaInt(PessoaInt pessoaInt) {
        this.pessoaInt = pessoaInt;
    }

    public void setEleicaoInt(EleicaoInt eleicaoInt) {
        this.eleicaoInt = eleicaoInt;
    }

    public void send(String string) {
        try {
            PrintWriter bos = new PrintWriter(socket.getOutputStream());
            bos.write(string);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String receive() {
        try {
            BufferedReader bos = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return bos.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
