package puyopuyo.client.panel.map.subpanel.socket_command;

import puyopuyo.client.panel.map.MapService;

public class Player1GarbagePuyo implements SocketCommand {
    private static Player1GarbagePuyo instance;

    private Player1GarbagePuyo() {}

    public static synchronized Player1GarbagePuyo getInstance() {
        if(instance == null) {
            instance = new Player1GarbagePuyo();
        }
        return instance;
    }

    @Override
    public void execute(String json) {
        MapService.getInstance().getScorePanel().getScoreService().setGarbagePuyoCount(0, Integer.parseInt(json));
    }
}
