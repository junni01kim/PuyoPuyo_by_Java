package puyopuyo.client.panel.map.subpanel.socket_command;

import com.google.gson.reflect.TypeToken;
import puyopuyo.client.ClientProcess;
import puyopuyo.client.panel.map.MapService;
import puyopuyo.server.game.round.PuyoS;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Player1Splash implements SocketCommand {
    private static Player1Splash instance;

    private Player1Splash() {}

    public static synchronized Player1Splash getInstance() {
        if(instance == null) {
            instance = new Player1Splash();
        }
        return instance;
    }

    @Override
    public void execute(String json) {
        // Type 1: 뿌요 맵 처리
        Type type3 = new TypeToken<ArrayList<PuyoS[][]>>() {}.getType();
        ArrayList<PuyoS[][]> puyoMaps = ClientProcess.getGson().fromJson(json, type3);

        // LR Puyo 지우기
        MapService.getInstance()
                .getGroundPanel(1)
                .clearLrPuyo();

        // 각 플레이어의 맵을 그림
        for (int player = 0; player < puyoMaps.size(); player++) {
            // Puyo Map 지우기
            MapService.getInstance()
                    .getGroundPanel(player)
                    .clearPuyoMap();

            // Puyo Map 그리기
            MapService.getInstance()
                    .getGroundPanel(player)
                    .drawPuyoMap(puyoMaps.get(player));
        }
    }
}
