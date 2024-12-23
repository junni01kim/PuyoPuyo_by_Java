package puyopuyo.client.panel.map.subpanel.socket_command;

import puyopuyo.client.panel.map.MapService;

public class Player2ScoreCommand implements SocketCommand {
    private static Player2ScoreCommand instance;

    private Player2ScoreCommand() {}

    public static synchronized Player2ScoreCommand getInstance() {
        if(instance == null) {
            instance = new Player2ScoreCommand();
        }
        return instance;
    }

    @Override
    public void execute(String json) {
        MapService.getInstance().getScorePanel().getScoreService().setScore(1, Integer.parseInt(json));
    }
}
