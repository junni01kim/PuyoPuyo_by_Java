package puyopuyo.client.panel.map.subpanel.socket_command;

import puyopuyo.client.panel.map.MapService;

public class Player1ScoreCommand implements SocketCommand {
    private static Player1ScoreCommand instance;

    private Player1ScoreCommand() {}

    public static synchronized Player1ScoreCommand getInstance() {
        if(instance == null) {
            instance = new Player1ScoreCommand();
        }
        return instance;
    }

    @Override
    public void execute(String json) {
        MapService.getInstance().getScorePanel().getScoreService().setScore(0, Integer.parseInt(json));
    }
}
