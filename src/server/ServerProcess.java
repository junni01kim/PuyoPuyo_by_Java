package server;

import client.ClientProcess;
import server.game.GameThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 1. 게임 참가 여부 확인
 * 2. 플레이어 할당
 * 3. 라운드 정보 전달
 * 4. ScorePanel 관리
 * 5. 맵 정보 받기
 */
public class ServerProcess {
    private static ServerProcess instance;

    public synchronized static ServerProcess getInstance() {
        if (instance == null) {
            instance = new ServerProcess();
        }
        return instance;
    }

    private final ArrayList<BufferedReader> ins = new ArrayList<>(2);
    private final ArrayList<BufferedWriter> outs = new ArrayList<>(2);
    private ServerSocket listener;
    private final ArrayList<Socket> sockets = new ArrayList<>(2);

    public ServerProcess() {
        try {
            setupConnection();
        } catch (IOException e) {
            handleError(e.getMessage());
        }
    }

    private void setupConnection() throws IOException {
        listener = new ServerSocket(9999);

        // 첫 번째 클라이언트 접속 처리
        Socket player1Socket = listener.accept();
        sockets.add(player1Socket);
        ins.add(new BufferedReader(new InputStreamReader(player1Socket.getInputStream())));
        outs.add(new BufferedWriter(new OutputStreamWriter(player1Socket.getOutputStream())));
        outs.get(0).write("player1 Access Complete\n");
        outs.get(0).flush();

        // 두 번째 클라이언트 접속 처리
        Socket player2Socket = listener.accept();
        sockets.add(player2Socket);
        ins.add(new BufferedReader(new InputStreamReader(player2Socket.getInputStream())));
        outs.add(new BufferedWriter(new OutputStreamWriter(player2Socket.getOutputStream())));
        outs.get(1).write("player2 Access Complete\n");
        outs.get(1).flush();

        System.out.println("All Player Access Complete. Waitting Game Start...");

        // TODO: 게임 시작 (GameThread 진행)

        while (true) {
            for (int i = 0; i < ins.size(); i++) {
                String message = readMessage(i);
                if (message != null) {
                    System.out.println("Player" + (i + 1) + ": " + message);

                    // 각 플레이어에게 메시지 전달
                    broadcastMessage("Player" + (i + 1) + ": " + message);
                }
            }
        }
    }

    private String readMessage(int playerIndex) {
        try {
            if (ins.get(playerIndex).ready()) {
                return ins.get(playerIndex).readLine();
            }
        } catch (IOException e) {
            handleError("Player" + (playerIndex + 1) + " 연결 오류: " + e.getMessage());
        }
        return null;
    }

    private void broadcastMessage(String message) {
        for (BufferedWriter out : outs) {
            try {
                out.write(message + "\n");
                out.flush();
            } catch (IOException e) {
                System.out.println("메시지 전송 실패: " + e.getMessage());
            }
        }
    }

    private static void handleError(String string) {
        System.out.println(string);
        System.exit(1);
    }
}
