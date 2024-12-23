package puyopuyo.server.move_command;

import puyopuyo.server.game.GameService;

public class DownMoveCommand implements MoveCommand {
    private static DownMoveCommand instance;

    private DownMoveCommand() {}

    public static synchronized DownMoveCommand getInstance() {
        if(instance == null) {
            instance = new DownMoveCommand();
        }

        return instance;
    }

    @Override
    public void execute(int player) {
        GameService.getInstance()
                .getRoundThread(player)
                .getRoundService()
                .downPuyo();
    }
}
