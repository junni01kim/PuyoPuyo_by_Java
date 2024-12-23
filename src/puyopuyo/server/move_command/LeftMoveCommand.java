package puyopuyo.server.move_command;

import puyopuyo.server.game.GameService;

import static puyopuyo.resource.Constants.MOVE;
import static puyopuyo.resource.Constants.X_MIN;

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
        System.out.println("Left Move Command");
        var roundService = GameService.getInstance()
                .getRoundThread(player)
                .getRoundService();

        var puyoMap = roundService.getPuyoMap();
        var leftPuyo = roundService.getLeftPuyo();
        var rightPuyo = roundService.getRightPuyo();

        //예외처리: 왼쪽에 블록 혹은 벽이 있는데 좌측키를 누르는 경우
        if(leftPuyo.x()<= X_MIN ||rightPuyo.x()<= X_MIN)
            return;
        //예외처리: 왼쪽에 블록이 있는데 좌측키를 누르는 경우
        if(puyoMap[leftPuyo.x()-MOVE][leftPuyo.y()]!=null
                ||puyoMap[rightPuyo.x()-MOVE][rightPuyo.y()]!=null)
            return;

        leftPuyo.pos(leftPuyo.x()-MOVE,leftPuyo.y());
        rightPuyo.pos(rightPuyo.x()-MOVE,rightPuyo.y());
    }
}
