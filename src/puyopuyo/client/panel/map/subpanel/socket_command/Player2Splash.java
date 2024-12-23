package puyopuyo.client.panel.map.subpanel.socket_command;

import com.google.gson.reflect.TypeToken;
import puyopuyo.client.ClientProcess;
import puyopuyo.client.panel.map.MapService;
import puyopuyo.server.game.round.PuyoS;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Player2Splash implements SocketCommand {
    private static Player2Splash instance;

    private Player2Splash() {}

    public static synchronized Player2Splash getInstance() {
        if(instance == null) {
            instance = new Player2Splash();
        }
        return instance;
    }

    @Override
    public void execute(String json) {
        // Type 1: 뿌요 맵 처리
        Type type4 = new TypeToken<ArrayList<PuyoS[][]>>() {}.getType();
        ArrayList<PuyoS[][]> puyoMaps = ClientProcess.getGson().fromJson(json, type4);

        // LR Puyo 지우기
        MapService.getInstance()
                .getGroundPanel(0)
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
