package puyopuyo.client.panel.map.subpanel.socket_command;

import com.google.gson.reflect.TypeToken;
import puyopuyo.client.ClientProcess;
import puyopuyo.client.panel.map.MapService;

import java.lang.reflect.Type;

public class Player1NextPuyo implements SocketCommand {
    private static Player1NextPuyo instance;

    private Player1NextPuyo() {}

    public static synchronized Player1NextPuyo getInstance() {
        if(instance == null) {
            instance = new Player1NextPuyo();
        }
        return instance;
    }

    @Override
    public void execute(String json) {
        Type type7 = new TypeToken<int[]>() {}.getType();
        int[] nextPuyo7 = ClientProcess.getGson().fromJson(json, type7);

        MapService.getInstance()
                .getScorePanel()
                .getScoreService()
                .drawNextPuyo(0, nextPuyo7);
    }
}
