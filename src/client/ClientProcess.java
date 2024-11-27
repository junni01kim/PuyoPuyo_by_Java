package client;

import java.io.*;
import java.net.Socket;

public class ClientProcess {
    private static ClientProcess instance;

    private final InputStream inputStream = System.in; // 표준 입력
    private BufferedReader in;
    private BufferedWriter out;
    private Socket socket;

    public synchronized static ClientProcess getInstance() {
        if (instance == null) {
            instance = new ClientProcess();
        }
        return instance;
    }

    public ClientProcess() {
        try {
            socket = new Socket("localhost", 9999);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Error1: " + e.getMessage());
        }
    }

    public void closeSocket() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.out.println("Error closing socket.");
        }
    }

    public void send(String message) {
        try {
            out.write(message + "\n");
            out.flush();
        } catch (Exception e) {
            System.out.println("Error2: " + e.getMessage());
        }
    }
}
