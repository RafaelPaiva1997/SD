package servidor.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {

    private static Socket socket;
    private static String ip;
    private static int port;

    public static void send(String string) {
        try {
            PrintWriter bos = new PrintWriter(socket.getOutputStream());
            bos.write(string);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String receive() {
        try {
            BufferedReader bos = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return bos.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            ip = args[0];
            port = Integer.parseInt(args[1]);
            Scanner sc = new Scanner(System.in);
            String username;
            String password;
            String s;
            int r;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port));
                System.out.print(receive());
                send("Terminal de Voto Ligado.\n");
                if (receive().split(";")[0].split("|")[1].equals("false")) {
                    do {
                        System.out.print("Insira o username: ");
                        username = sc.nextLine();
                        System.out.print("Insira a password: ");
                        password = sc.nextLine();
                        send("type|login;username|" + username + ";password|" + password);
                    } while (!receive().split("|")[1].equals("true"));
                }
                s = receive();
                if (s.split(";")[0].split("|")[1].equals("item_list"))
                    for (int i = 0; i < Integer.parseInt(s.split(";")[1].split("|")[1]); i++)
                        System.out.print(s.split(";")[1 + i].split("|")[1] + "\n");
                System.out.print("Insira o seu voto: ");
                while ((r = sc.nextInt()) < 0 || r < Integer.parseInt(s.split(";")[1].split("|")[1]))
                    System.out.print("Por favor insira uma das opções válidas.\n");

                send("type|vote;key|" + r);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
