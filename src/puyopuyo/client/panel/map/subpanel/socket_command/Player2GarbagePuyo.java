package puyopuyo.client.panel.map.subpanel.socket_command;

import puyopuyo.client.panel.map.MapService;

public class Player2GarbagePuyo implements SocketCommand {
    private static Player2GarbagePuyo instance;

    private Player2GarbagePuyo() {}

    public static synchronized Player2GarbagePuyo getInstance() {
        if(instance == null) {
            instance = new Player2GarbagePuyo();
        }
        return instance;
    }

    @Override
    public void execute(String json) {
        MapService.getInstance().getScorePanel().getScoreService().setGarbagePuyoCount(1, Integer.parseInt(json));
    }
}
