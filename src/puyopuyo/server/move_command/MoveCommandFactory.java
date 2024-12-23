package puyopuyo.server.move_command;

/**
 * MoveCommand를 생성하는 Factory 클래스
 * Factory Pattern을 적용하였다.
 */
public class MoveCommandFactory {
    public static MoveCommand getMoveCommand(String direction) {
        return switch (direction) {
            case "Up" -> UpMoveCommand.getInstance();
            case "Down" -> DownMoveCommand.getInstance();
            case "Left" -> LeftMoveCommand.getInstance();
            default -> RightMoveCommand.getInstance();
//            case "Right" -> RightMoveCommand.getInstance();
//            default -> null;
        };
    }
}
