package puyopuyo.server.movecommand;

public class UpMoveCommand implements MoveCommand {
    private static UpMoveCommand instance;

    private UpMoveCommand() {}

    public static synchronized UpMoveCommand getInstance() {
        if(instance == null) {
            instance = new UpMoveCommand();
        }

        return instance;
    }

    @Override
    public void execute(int player) {

    }
}
