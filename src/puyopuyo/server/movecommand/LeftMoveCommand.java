package puyopuyo.server.movecommand;

public class LeftMoveCommand implements MoveCommand {
    private static LeftMoveCommand instance;

    private LeftMoveCommand() {}

    public static synchronized LeftMoveCommand getInstance() {
        if(instance == null) {
            instance = new LeftMoveCommand();
        }

        return instance;
    }

    @Override
    public void execute(int player) {

    }
}
