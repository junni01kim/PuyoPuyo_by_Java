package puyopuyo.client.panel.map.subpanel.socket_command;

import puyopuyo.client.panel.map.MapService;

public class ClearCommand implements SocketCommand {
    private static ClearCommand instance;

    private ClearCommand() {}

    public static synchronized ClearCommand getInstance() {
        if(instance == null) {
            instance = new ClearCommand();
        }
        return instance;
    }

    @Override
    public void execute(String json) {
        for(int player = 0; player < 2; player++) {
            MapService.getInstance()
                    .getGroundPanel(player)
                    .clearPuyoMap();

            MapService.getInstance()
                    .getGroundPanel(player)
                    .clearLrPuyo();
        }
    }
}
