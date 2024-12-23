package puyopuyo.client.panel.map.subpanel.socket_command;

import com.google.gson.reflect.TypeToken;
import puyopuyo.client.ClientProcess;
import puyopuyo.client.panel.map.MapService;
import puyopuyo.server.game.round.PuyoS;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DrawLRPuyoCommand implements SocketCommand {
    private static DrawLRPuyoCommand instance;

    private DrawLRPuyoCommand() {}

    public static synchronized DrawLRPuyoCommand getInstance() {
        if(instance == null) {
            instance = new DrawLRPuyoCommand();
        }
        return instance;
    }

    @Override
    public void execute(String json) {
        // Left/Right Puyo 처리
        Type type2 = new TypeToken<ArrayList<PuyoS[]>>() {}.getType();
        ArrayList<PuyoS[]> lrPuyo = ClientProcess.getGson().fromJson(json, type2);

        for (int player = 0; player < lrPuyo.size(); player++) {
            // LR Puyo 지우기
            MapService.getInstance()
                    .getGroundPanel(player)
                    .clearLrPuyo();

            // LR Puyo 그리기
            MapService.getInstance()
                    .getGroundPanel(player)
                    .drawLrPuyo(lrPuyo.get(player));
        }
    }
}
