package puyopuyo.client.panel.map.subpanel.socket_command;

import puyopuyo.client.panel.map.MapService;

public class TimerCommand implements SocketCommand {
    private static TimerCommand instance;

    private TimerCommand() {}

    public static synchronized TimerCommand getInstance() {
        if(instance == null) {
            instance = new TimerCommand();
        }
        return instance;
    }

    @Override
    public void execute(String json) {
        MapService.getInstance()
                .getScorePanel()
                .getScoreService()
                .setTimer(Integer.parseInt(json));
    }
}
