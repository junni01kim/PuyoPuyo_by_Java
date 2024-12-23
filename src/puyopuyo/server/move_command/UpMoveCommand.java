package puyopuyo.server.move_command;

import puyopuyo.server.game.GameService;
import puyopuyo.server.game.round.PuyoS;

import static puyopuyo.resource.Constants.*;
import static puyopuyo.resource.Constants.MOVE;

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
        System.out.println("Up Move Command");
        var roundService = GameService.getInstance()
                .getRoundThread(player)
                .getRoundService();

        var puyoMap = roundService.getPuyoMap();
        var leftPuyo = roundService.getLeftPuyo();
        var rightPuyo = roundService.getRightPuyo();

        //두 뿌요의 위치에 따라서 뿌요를 회전시킨다.
        // 경우1. 좌우 배치 - (뿌요1)(뿌요2)
        switch(Rotate.calcRotate(leftPuyo, rightPuyo)) {
            case RIGHT -> {
                // 예외처리: 뿌요가 바닥에서 회전하여 맵을 뚫고 나가는 경우
                if(leftPuyo.y()== Y_MAX
                        // 예외처리: 뿌요가 다른 뿌요 위에서 회전하여 다른 뿌요와 겹치는 경우
                        ||(leftPuyo.y()< Y_MAX &&puyoMap[leftPuyo.x()][leftPuyo.y()+MOVE]!=null)) {
                    leftPuyo.pos(leftPuyo.x(), leftPuyo.y()-MOVE);
                    rightPuyo.pos(leftPuyo.x(), leftPuyo.y()+MOVE);
                }
                rightPuyo.pos(leftPuyo.x(), leftPuyo.y()+MOVE);
            }
            case LEFT -> {
                rightPuyo.pos(leftPuyo.x(), leftPuyo.y()-MOVE);
            }
            case UP -> {
                // 예외처리: 뿌요가 벽에서 붙은 채 회전하여 맵을 뚫고 나가는 경우
                if(leftPuyo.x()== X_MIN
                        // 예외처리: 뿌요가 다른 뿌요 옆에서 회전하여 다른 뿌요와 겹치는 경우
                        ||(leftPuyo.x()> X_MIN &&puyoMap[leftPuyo.x()-MOVE][leftPuyo.y()]!=null)) {
                    // 예외처리: 회전하려는 장소에 이미 뿌요가 막혀 있는 경우
                    if(puyoMap[leftPuyo.x()+MOVE][leftPuyo.x()]!=null)
                        break;
                    leftPuyo.pos(leftPuyo.x()+MOVE, leftPuyo.y());
                    rightPuyo.pos(leftPuyo.x()-MOVE, leftPuyo.y());
                }
                rightPuyo.pos(leftPuyo.x()-MOVE, leftPuyo.y());
            }
            case DOWN -> {
                // 예외처리: 뿌요가 벽에서 붙은 채 회전하여 맵을 뚫고 나가는 경우
                if(leftPuyo.x()== X_MAX
                        // 예외처리: 뿌요가 다른 뿌요 옆에서 회전하여 다른 뿌요와 겹치는 경우
                        ||(leftPuyo.x()< X_MAX &&puyoMap[leftPuyo.x()+MOVE][leftPuyo.y()]!=null)) {
                    // 예외처리: 회전하려는 장소에 이미 뿌요가 막혀 있는 경우
                    if(puyoMap[leftPuyo.x()-MOVE][leftPuyo.y()]!=null)
                        break;
                    leftPuyo.pos(leftPuyo.x()-MOVE, leftPuyo.y());
                    rightPuyo.pos(leftPuyo.x()+MOVE, leftPuyo.y());
                }
                rightPuyo.pos(leftPuyo.x()+MOVE, leftPuyo.y());
            }
        }
    }

    /**
     * 조작하는 뿌요의 회전 상태를 열거형으로 표현하기 위한 클래스
     */
    private enum Rotate {
        RIGHT,
        LEFT,
        UP,
        DOWN,
        ERROR;

        /**
         * l의 기준으로 r이 있는 방향
         */
        public static Rotate calcRotate(PuyoS l, PuyoS r) {
            // 경우1. 좌우 배치 - (뿌요1)(뿌요2)
            if(l.x()<=r.x()&&l.y()==r.y()) return RIGHT;
                // 경우2. 좌우 배치 - (뿌요2)(뿌요1)
            else if(l.x()>r.x()&&l.y()==r.y()) return LEFT;
                // 경우3. 상하 배치 - (뿌요1)(뿌요2)
            else if(l.x()==r.x()&&l.y()<=r.y()) return UP;
                // 경우4. 상하 배치 - (뿌요2)(뿌요1)
            else if(l.x()==r.x()&&l.y()>r.y()) return DOWN;

            return ERROR;
        }

    }
}
