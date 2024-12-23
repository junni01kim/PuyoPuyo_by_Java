package puyopuyo.client.panel.map.subpanel.socket_command;

import com.google.gson.reflect.TypeToken;
import puyopuyo.client.ClientProcess;
import puyopuyo.client.panel.map.MapService;

import java.lang.reflect.Type;

public class Player2NextPuyo implements SocketCommand {
    private static Player2NextPuyo instance;

    private Player2NextPuyo() {}

    public static synchronized Player2NextPuyo getInstance() {
        if(instance == null) {
            instance = new Player2NextPuyo();
        }
        return instance;
    }

    @Override
    public void execute(String json) {
        Type type6 = new TypeToken<int[]>() {}.getType();
        int[] nextPuyo6 = ClientProcess.getGson().fromJson(json, type6);

        MapService.getInstance()
                .getScorePanel()
                .getScoreService()
                .drawNextPuyo(1, nextPuyo6);
    }
}
