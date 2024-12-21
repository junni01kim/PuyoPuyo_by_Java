package puyopuyo.server.movecommand;

public class RightMoveCommand implements MoveCommand {
    private static RightMoveCommand instance;

    private RightMoveCommand() {}

    public static synchronized RightMoveCommand getInstance() {
        if(instance == null) {
            instance = new RightMoveCommand();
        }

        return instance;
    }

    @Override
    public void execute(int player) {

    }
}
