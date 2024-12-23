package puyopuyo.client;

import com.google.gson.Gson;
import puyopuyo.client.panel.map.subpanel.socket_command.SocketFactory;
import puyopuyo.dto.SendDTO;

import java.io.*;
import java.net.Socket;

public class ClientProcess {
    private int player = 0;
    private static ClientProcess instance;

    private BufferedReader in; // 서버로부터의 입력
    private BufferedWriter out; // 서버로의 출력
    private Socket socket;
    private static final Gson gson = new Gson();

    /**
     * 싱글톤으로 제작하기 위함
     * @return
     */
    public synchronized static ClientProcess getInstance() {
        if (instance == null) {
            instance = new ClientProcess();
        }
        return instance;
    }

    /**
     * 생성자.
     * 서버와 통신하는 Input과 Output을 추가하고, readThread에서 서버의 정보를 지속적으로 반환 받는다.
     */
    public ClientProcess() {
        try {
            socket = new Socket("localhost", 9999);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // 서버 응답 수신을 위한 별도 스레드 실행
            new Thread(this::fromServer).start();

        } catch (IOException e) {
            System.err.println("Error connecting to puyopuyo.server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 메세지를 전송하는 함수. ClientKeyListener를 통해서 방향키 정보를 전달할 예정이다.
     * @param message
     */
    public void toServer(String message) {
        try {
            var sendDTO = new SendDTO(player, 0, message);
            var json = gson.toJson(sendDTO);
            out.write(json+"\n");
            out.flush();
            System.out.println("To Server: " + json);
        } catch (Exception e) {
            System.err.println("Error while sending message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 스레드를 통해 반복적으로 서버의 결과를 받는 함수이다.
     * TODO: 두 플레이어의 puyoMap을 지속적으로 받을 것.
     */
    private void fromServer() {
        String serverMessage;
        SendDTO sendDTO;
        try {
            while ((serverMessage = in.readLine()) != null) {
                // TODO: observer 설정
                sendDTO = gson.fromJson(serverMessage, SendDTO.class);
                SocketFactory.getDrawCommand(sendDTO.getType()).execute(sendDTO.getData());
            }
        } catch (IOException e) {
            System.err.println("Error while reading puyopuyo.server message: " + e.getMessage());
        }
    }

    /**
     * 게임이 끝난 후 화면을 나가면서 사용하는 함수
     * TODO: 추가할 것
     */
    public void closeSocket() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Gson getGson() {
        return gson;
    }

    public void setPlayer(int player) {
        this.player = player;
    }
}
