package problem;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Date:2020/11/11
 * Description: optional class description
 **/

class Client implements Runnable {

    private byte[] targetIp;
    private int port;

    Client(byte[] ip, int port) {
        this.targetIp = ip;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            InetAddress inetAddress = InetAddress.getByAddress(targetIp);
            Socket socket = new Socket(inetAddress, port);
            System.out.println("client");
            BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter socketOutput = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            int i = 0;
            String NAME = "Client";
            while (true) {
                socketOutput.write("This msg from " + NAME + " msg id is " + i);
                socketOutput.write("\n");
                i++;
                socketOutput.flush();
                System.out.println("here");
                String str = null;
                if (!(str = socketInput.readLine()).equals("\n")) {
                    System.out.println(str);
                }

            }

            /*socket.shutdownInput();
            socket.shutdownOutput();*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class Server implements Runnable {

    private int port;

    Server(int port) {
        this.port = port;
    }
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            InetAddress inetAddress = serverSocket.getInetAddress();
            System.out.println("server" + inetAddress.getHostAddress());
            Socket socket = serverSocket.accept();
            BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter socketOutput = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            int i = 0;
            while (true) {
                String str = null;
                if (!(str = socketInput.readLine()).equals("\n")) System.out.println(str);
                System.out.println("server");

                String NAME = "Server";
                socketOutput.write("This msg from " + NAME + " msg num is " + i + " reply to " + str);
                socketOutput.write("\n");
                i++;
                socketOutput.flush();
            }

//            socket.shutdownInput();
//            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
public class SocketTest {
    public static void main(String[] args) {
        byte[] ip = {127, 0, 0, 1};
        int port = 27149;
        Thread server = new Thread(new Server(port), "server");
        server.start();
        Thread client = new Thread(new Client(new byte[]{0,0,0,0}, 27149));
        client.start();
    }
}
