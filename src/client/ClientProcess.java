package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientProcess {
    public ClientProcess() {
        BufferedReader in;
        BufferedWriter out;
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket("localhost", 9999);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (true)
            {
                System.out.print("보내기>>");
                String outputMessage = scanner.nextLine();
                if (outputMessage.equalsIgnoreCase("bye")) {
                    out.write(outputMessage+"\n");
                    out.flush();
                    break;
                }
                out.write(outputMessage + "\n");
                out.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                scanner.close();
                if(socket != null) socket.close();
            } catch (IOException e) {
                System.out.println("서버와 채팅중 오류가 발생했습니다.");
            }
        }
    }
}
