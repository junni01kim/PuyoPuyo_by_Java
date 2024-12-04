package puyopuyo.Panel.map;

import server.game.GameService;
import puyopuyo.Panel.map.subpanel.ground.Puyo;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static puyopuyo.resource.Constants.*;

/**
 * 게임의 전체 조작을 관리하는 KeyListener이다.
 *
 * 사용자의 요청 관리 책임을 가지며, MVC 패턴의 Controller의 역할을 한다.
 */
public class ControlPuyoKeyListener extends KeyAdapter {
    private MapService mapService = MapService.getInstance();

    synchronized public void keyPressed(KeyEvent e) {
        var groundService1P = mapService.getGroundPanel(1).getGroundService();
        var groundService2P = mapService.getGroundPanel(2).getGroundService();

        var puyoMap1P = groundService1P.getPuyoMap();
        var puyoMap2P = groundService2P.getPuyoMap();

        var leftPuyo1P = groundService1P.getLeftPuyo();
        var rightPuyo1P = groundService1P.getRightPuyo();
        var leftPuyo2P = groundService2P.getLeftPuyo();
        var rightPuyo2P = groundService2P.getRightPuyo();

        switch(e.getKeyCode()) {
            /** Player1에 대한 키입력 */
            case KeyEvent.VK_W:
                //두 뿌요의 위치에 따라서 뿌요를 회전시킨다.
                switch(Rotate.calcRotate(leftPuyo1P, rightPuyo1P)) {
                    case RIGHT -> {
                        // 예외처리: 뿌요가 바닥에서 회전하여 맵을 뚫고 나가는 경우
                        if(leftPuyo1P.y()== Y_MAX
                                // 예외처리: 뿌요가 다른 뿌요 위에서 회전하여 다른 뿌요와 겹치는 경우
                                ||(leftPuyo1P.y()< Y_MAX &&puyoMap1P[leftPuyo1P.x()][leftPuyo1P.y()+MOVE]!=null)) {
                            leftPuyo1P.pos(leftPuyo1P.x(), leftPuyo1P.y()-MOVE);
                            rightPuyo1P.pos(leftPuyo1P.x(), leftPuyo1P.y()+MOVE);
                        }
                        rightPuyo1P.pos(leftPuyo1P.x(), leftPuyo1P.y()+1);
                    }
                    case LEFT -> {
                        rightPuyo1P.pos(leftPuyo1P.x(), leftPuyo1P.y()-1);
                    }
                    case UP -> {
                        // 예외처리: 뿌요가 벽에서 붙은 채 회전하여 맵을 뚫고 나가는 경우
                        if(leftPuyo1P.x()== X_MIN
                                // 예외처리: 뿌요가 다른 뿌요 옆에서 회전하여 다른 뿌요와 겹치는 경우
                                ||(leftPuyo1P.x()> X_MIN &&puyoMap1P[leftPuyo1P.x()-MOVE][leftPuyo1P.y()]!=null)) {
                            // 예외처리: 회전하려는 장소에 이미 뿌요가 막혀 있는 경우
                            if(puyoMap1P[leftPuyo1P.x()+MOVE][leftPuyo1P.y()]!=null)
                                break;
                            leftPuyo1P.pos(leftPuyo1P.x()+MOVE, leftPuyo1P.y());
                            rightPuyo1P.pos(leftPuyo1P.x()-MOVE, leftPuyo1P.y());
                        }
                        rightPuyo1P.pos(leftPuyo1P.x()-MOVE, leftPuyo1P.y());
                    }
                    case DOWN -> {
                        // 예외처리: 뿌요가 벽에서 붙은 채 회전하여 맵을 뚫고 나가는 경우
                        if(leftPuyo1P.x()== X_MAX
                                // 예외처리: 뿌요가 다른 뿌요 옆에서 회전하여 다른 뿌요와 겹치는 경우
                                ||(leftPuyo1P.x()< X_MAX &&puyoMap1P[leftPuyo1P.x()+MOVE][leftPuyo1P.y()]!=null)) {
                            // 예외처리: 회전하려는 장소에 이미 뿌요가 막혀 있는 경우
                            if(puyoMap1P[leftPuyo1P.x()-MOVE][leftPuyo1P.y()]!=null)
                                break;

                            leftPuyo1P.pos(leftPuyo1P.x()-MOVE, leftPuyo1P.y());
                            rightPuyo1P.pos(leftPuyo1P.x()+MOVE, leftPuyo1P.y());
                        }
                        rightPuyo1P.pos(leftPuyo1P.x()+MOVE, leftPuyo1P.y());
                    }
                }
                break;
            case KeyEvent.VK_S:
                GameService.getInstance().getRoundThread(1).getRoundService().downPuyo();
                break;
            case KeyEvent.VK_A:
                //예외처리: 왼쪽에 벽이 있는데 좌측키를 누르는 경우
                if(leftPuyo1P.x()<= X_MIN ||rightPuyo1P.x()<= X_MIN)
                    break;
                //예외처리: 왼쪽에 블록이 있는데 좌측키를 누르는 경우
                if(puyoMap1P[leftPuyo1P.x()-MOVE][leftPuyo1P.y()]!=null
                        ||puyoMap1P[rightPuyo1P.x()-MOVE][rightPuyo1P.y()]!=null)
                    break;

                leftPuyo1P.pos(leftPuyo1P.x()-MOVE,leftPuyo1P.y());
                rightPuyo1P.pos(rightPuyo1P.x()-MOVE,rightPuyo1P.y());
                break;
            case KeyEvent.VK_D:
                //예외처리: 오른쪽에 블록 혹은 벽이 있는데 우측키를 누르는 경우
                if(leftPuyo1P.x()>= X_MAX ||rightPuyo1P.x()>= X_MAX)
                    break;
                //예외처리: 오른쪽에 블록이 있는데 우측키를 누르는 경우
                if(puyoMap1P[leftPuyo1P.x()+MOVE][leftPuyo1P.y()]!=null
                        ||puyoMap1P[rightPuyo1P.x()+MOVE][rightPuyo1P.y()]!=null)
                    break;
                leftPuyo1P.pos(leftPuyo1P.x()+MOVE,leftPuyo1P.y());
                rightPuyo1P.pos(rightPuyo1P.x()+MOVE,rightPuyo1P.y());
                break;
            /** Player2에 대한 키입력 */
            case KeyEvent.VK_UP:
                //두 뿌요의 위치에 따라서 뿌요를 회전시킨다.
                // 경우1. 좌우 배치 - (뿌요1)(뿌요2)
                switch(Rotate.calcRotate(leftPuyo2P, rightPuyo2P)) {
                    case RIGHT -> {
                        // 예외처리: 뿌요가 바닥에서 회전하여 맵을 뚫고 나가는 경우
                        if(leftPuyo2P.y()== Y_MAX
                                // 예외처리: 뿌요가 다른 뿌요 위에서 회전하여 다른 뿌요와 겹치는 경우
                                ||(leftPuyo2P.y()< Y_MAX &&puyoMap2P[leftPuyo2P.x()][leftPuyo2P.y()+MOVE]!=null)) {
                            leftPuyo2P.pos(leftPuyo2P.x(), leftPuyo2P.y()-MOVE);
                            rightPuyo2P.pos(leftPuyo2P.x(), leftPuyo2P.y()+MOVE);
                        }
                        rightPuyo2P.pos(leftPuyo2P.x(), leftPuyo2P.y()+MOVE);
                    }
                    case LEFT -> {
                        rightPuyo2P.pos(leftPuyo2P.x(), leftPuyo2P.y()-MOVE);
                    }
                    case UP -> {
                        // 예외처리: 뿌요가 벽에서 붙은 채 회전하여 맵을 뚫고 나가는 경우
                        if(leftPuyo2P.x()== X_MIN
                                // 예외처리: 뿌요가 다른 뿌요 옆에서 회전하여 다른 뿌요와 겹치는 경우
                                ||(leftPuyo2P.x()> X_MIN &&puyoMap2P[leftPuyo2P.x()-MOVE][leftPuyo2P.y()]!=null)) {
                            // 예외처리: 회전하려는 장소에 이미 뿌요가 막혀 있는 경우
                            if(puyoMap2P[leftPuyo2P.x()+MOVE][leftPuyo2P.x()]!=null)
                                break;
                            leftPuyo2P.pos(leftPuyo2P.x()+MOVE, leftPuyo2P.y());
                            rightPuyo2P.pos(leftPuyo2P.x()-MOVE, leftPuyo2P.y());
                        }
                        rightPuyo2P.pos(leftPuyo2P.x()-MOVE, leftPuyo2P.y());
                    }
                    case DOWN -> {
                        // 예외처리: 뿌요가 벽에서 붙은 채 회전하여 맵을 뚫고 나가는 경우
                        if(leftPuyo2P.x()== X_MAX
                                // 예외처리: 뿌요가 다른 뿌요 옆에서 회전하여 다른 뿌요와 겹치는 경우
                                ||(leftPuyo2P.x()< X_MAX &&puyoMap2P[leftPuyo2P.x()+MOVE][leftPuyo2P.y()]!=null)) {
                            // 예외처리: 회전하려는 장소에 이미 뿌요가 막혀 있는 경우
                            if(puyoMap2P[leftPuyo2P.x()-MOVE][leftPuyo2P.y()]!=null)
                                break;
                            leftPuyo2P.pos(leftPuyo2P.x()-MOVE, leftPuyo2P.y());
                            rightPuyo2P.pos(leftPuyo2P.x()+MOVE, leftPuyo2P.y());
                        }
                        rightPuyo2P.pos(leftPuyo2P.x()+MOVE, leftPuyo2P.y());
                    }
                }
                break;
            case KeyEvent.VK_DOWN:
                GameService.getInstance().getRoundThread(2).getRoundService().downPuyo();
                break;
            case KeyEvent.VK_LEFT:
                //예외처리: 왼쪽에 블록 혹은 벽이 있는데 좌측키를 누르는 경우
                if(leftPuyo2P.x()<= X_MIN ||rightPuyo2P.x()<= X_MIN)
                    break;
                //예외처리: 왼쪽에 블록이 있는데 좌측키를 누르는 경우
                if(puyoMap2P[leftPuyo2P.x()-MOVE][leftPuyo2P.y()]!=null
                        ||puyoMap2P[rightPuyo2P.x()-MOVE][rightPuyo2P.y()]!=null)
                    break;

                leftPuyo2P.pos(leftPuyo2P.x()-MOVE,leftPuyo2P.y());
                rightPuyo2P.pos(rightPuyo2P.x()-MOVE,rightPuyo2P.y());
                break;
            case KeyEvent.VK_RIGHT:
                //예외처리: 오른쪽에 블록 혹은 벽이 있는데 우측키를 누르는 경우
                if(leftPuyo2P.x()>= X_MAX ||rightPuyo2P.x()>= X_MAX)
                    break;
                //예외처리: 오른쪽에 블록이 있는데 우측키를 누르는 경우
                if(puyoMap2P[leftPuyo2P.x()+MOVE][leftPuyo2P.y()]!=null
                        ||puyoMap2P[rightPuyo2P.x()+MOVE][rightPuyo2P.y()]!=null)
                    break;

                leftPuyo2P.pos(leftPuyo2P.x()+MOVE,leftPuyo2P.y());
                rightPuyo2P.pos(rightPuyo2P.x()+MOVE,rightPuyo2P.y());
                break;
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
        public static Rotate calcRotate(Puyo l, Puyo r) {
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
