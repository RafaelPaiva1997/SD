package servidor.tcp;

import admin.console.gestores.Lista;
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
    private boolean logged;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        busy = false;
        logged = false;
    }

    @Override
    public void run() {
        String s;
        String username = "";
        String password = "";
        StringBuilder sb;
        while (true) {
            try {
                countDownLatch = new CountDownLatch(1);
                send("Terminal de Voto Ligado.\n");
                System.out.print(receive());
                countDownLatch.await();
                do {
                    s = receive();
                    if (s.split(";")[0].split("|")[1].equals("login")) {
                        username = s.split(";")[1].split("|")[1];
                        password = s.split(";")[2].split("|")[1];
                        logged = true;
                    }
                    send("return|" + logged);
                } while (!logged);
                sb = new StringBuilder("type|item_list;item_count|" + eleicaoInt.getListas().size() + 2 + ";");
                for (int i = 0; i < eleicaoInt.getListas().size(); i++)
                    sb.append("item_" + i + "_name|" + eleicaoInt.getListas().get(i).inLinePrint() + ";");
                sb.append("item_" + eleicaoInt.getListas().size() + "_name|Voto em Branco;");
                sb.append("item_" + eleicaoInt.getListas().size() + 1 + "_name|Voto Nulo;");
                send(sb.toString());
                s = receive();
            } catch (Exception e) {
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
