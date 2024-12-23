package puyopuyo.server;

import com.google.gson.Gson;
import puyopuyo.dto.SendDTO;
import puyopuyo.server.game.GameService;
import puyopuyo.server.game.GameThread;
import puyopuyo.server.move_command.*;

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

    private final Gson gson = new Gson();

    public synchronized static ServerProcess getInstance() {
        if (instance == null) {
            instance = new ServerProcess();
        }
        return instance;
    }

    /**
     * 변수 선언
     *
     */
    private final ArrayList<BufferedReader> ins = new ArrayList<>(2);
    private final ArrayList<BufferedWriter> outs = new ArrayList<>(2);
    private ServerSocket listener;
    private final ArrayList<Socket> sockets = new ArrayList<>(2);

    /**
     * 생성자
     *
     */
    public ServerProcess() {
        try {
            setupConnection();
        } catch (IOException e) {
            handleError(e.getMessage());
        }
    }

    /**
     * 연결 로직
     *
     * @throws IOException
     */
    private void setupConnection() throws IOException {
        listener = new ServerSocket(9999);

        // 첫 번째 클라이언트 접속 처리
        Socket player1Socket = listener.accept();
        sockets.add(player1Socket);
        ins.add(new BufferedReader(new InputStreamReader(player1Socket.getInputStream())));
        outs.add(new BufferedWriter(new OutputStreamWriter(player1Socket.getOutputStream())));
        toClient(0, -1, "0"); // DrawFactory.Default

        // 두 번째 클라이언트 접속 처리
        Socket player2Socket = listener.accept();
        sockets.add(player2Socket);
        ins.add(new BufferedReader(new InputStreamReader(player2Socket.getInputStream())));
        outs.add(new BufferedWriter(new OutputStreamWriter(player2Socket.getOutputStream())));
        toClient(1, -1, "1"); // DrawFactory.Default

        System.out.println("All Player Access Complete. Waiting Game Start...");

        // TODO: 게임 시작 (GameThread 진행)
        GameThread.getInstance().start();

        startMessageObserverThread();
    }

    private void startMessageObserverThread() {
        Thread messageObserverThread = new Thread(() -> {
            System.out.println("Message Observer Thread Started");
            messageObserver();
        });

        messageObserverThread.setDaemon(true); // 데몬 스레드 설정 (메인 스레드 종료 시 함께 종료됨)
        messageObserverThread.start();
    }

    /**
     * 반복적으로 클라이언트의 메세지를 받는 함수
     *
     */
    private void messageObserver() {
        while (true) {
            for (int i = 0; i < ins.size(); i++) {
                String message = fromClient(i);
                var sendDTO = gson.fromJson(message, SendDTO.class);

                if (message != null) {
                    // 클라이언트는 sendDTO로 String을 무조건 반환한다.
                    controlPuyo(sendDTO.getPlayer(), sendDTO.getData());

                    // 조작된 결과 전송
                    toAllClient(2, gson.toJson(GameService.getInstance().getLRPuyo()));
                }
            }
        }
    }

    /**
     * 방향 명령을 기반으로 뿌요를 조작
     * @param player
     * @param direction
     */
    private void controlPuyo(int player, String direction) {
        MoveCommandFactory.getMoveCommand(direction).execute(player);
    }

    /**
     * 클라이언트의 메세지를 조회한다.
     * @param playerIndex
     * @return
     */
    private String fromClient(int playerIndex) {
        try {
            if (ins.get(playerIndex).ready()) {
                return ins.get(playerIndex).readLine();
            }
        } catch (IOException e) {
            handleError("Player" + (playerIndex + 1) + " 연결 오류: " + e.getMessage());
        }
        return null;
    }

    /**
     * 모든 클라이언트에게 동일한 메세지를 전달한다.
     * @param message
     */
    public void toAllClient(int type, String message) {
        for (int player = 0; player < ins.size(); player++) {
            try {
                toClient(player, type, message);
            } catch (IOException e) {
                System.out.println("Message Send Error: " + e.getMessage());
            }
        }
    }

    /**
     * 특정 클라이언트에게 메세지를 전달한다.
     * @param player
     * @param message
     * @throws IOException
     */
    private void toClient(int player, int type, String message) throws IOException {
        var sendDTO = new SendDTO(player, type, message);
        var json = gson.toJson(sendDTO);

        outs.get(player).write(json+"\n");
        outs.get(player).flush();
    }

    /**
     * 예외를 처리하는 함수
     * @param string
     */
    private static void handleError(String string) {
        System.out.println(string);
        System.exit(1);
    }

    public Gson getGson() {
        return gson;
    }
}
