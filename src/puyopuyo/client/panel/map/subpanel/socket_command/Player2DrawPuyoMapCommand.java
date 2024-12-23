package puyopuyo.client.panel.map.subpanel.socket_command;

import com.google.gson.reflect.TypeToken;
import puyopuyo.client.ClientProcess;
import puyopuyo.client.panel.map.MapService;
import puyopuyo.server.game.round.PuyoS;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Player2DrawPuyoMapCommand implements SocketCommand {
    private static Player2DrawPuyoMapCommand instance;

    private Player2DrawPuyoMapCommand() {}

    public static synchronized Player2DrawPuyoMapCommand getInstance() {
        if(instance == null) {
            instance = new Player2DrawPuyoMapCommand();
        }
        return instance;
    }

    @Override
    public void execute(String json) {
        // Type 1: 뿌요 맵 처리
        Type type0 = new TypeToken<ArrayList<PuyoS[][]>>() {}.getType();
        ArrayList<PuyoS[][]> puyoMaps = ClientProcess.getGson().fromJson(json, type0);

        // 각 플레이어의 맵을 그림
        // Puyo Map 지우기
        MapService.getInstance()
                .getGroundPanel(1)
                .clearPuyoMap();

        // Puyo Map 그리기
        MapService.getInstance()
                .getGroundPanel(1)
                .drawPuyoMap(puyoMaps.get(1));
    }
}
