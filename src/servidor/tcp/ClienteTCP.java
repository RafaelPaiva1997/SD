package servidor.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

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
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port));
                System.out.print(receive());
                send("Terminal de Voto Ligado.\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
