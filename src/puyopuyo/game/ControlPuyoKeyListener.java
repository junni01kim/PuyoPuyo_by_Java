package puyopuyo.game;

import puyopuyo.round.RoundService;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/** GamePanel의 키 입력 로직 */
// TODO: 개인적으로 굳이 픽셀 단위로 하는 것 보다 인덱스 단위로 하는게 더 편할거 같긴한데, 나중에 기회되면 바꿔보기
public class ControlPuyoKeyListener extends KeyAdapter {
    private final GameService gameService;

    ControlPuyoKeyListener(GameService gameService) {
        this.gameService = gameService;
    }

    synchronized public void keyPressed(KeyEvent e) {
        var gameGround1PService = gameService.getGameGround1P().getService();
        var gameGround2PService = gameService.getGameGround2P().getService();

        var playerThread1P = roundService.getPlayerThread1P();
        var playerThread2P = roundService.getPlayerThread2P();

        var puyoMap1P = playerThread1P.getPlayerThreadService().getPuyoMap();
        var puyoMap2P = playerThread2P.getPlayerThreadService().getPuyoMap();

        var leftPuyo1P = gameGround1PService.getLeftPuyo();
        var rightPuyo1P = gameGround1PService.getRightPuyo();
        var leftPuyo2P = gameGround2PService.getLeftPuyo();
        var rightPuyo2P = gameGround2PService.getRightPuyo();

        switch(e.getKeyCode()) {
            /** Player1에 대한 키입력 */
            case KeyEvent.VK_W:
                //두 뿌요의 위치에 따라서 뿌요를 회전시킨다.
                // 경우1. 좌우 배치 - (뿌요1)(뿌요2)
                if(leftPuyo1P.getX()<=rightPuyo1P.getX()&&leftPuyo1P.getY()==rightPuyo1P.getY()) {
                    // 예외처리: 뿌요가 바닥에서 회전하여 맵을 뚫고 나가는 경우
                    if(leftPuyo1P.PixelYToindex()==11
                            // 예외처리: 뿌요가 다른 뿌요 위에서 회전하여 다른 뿌요와 겹치는 경우
                            ||(leftPuyo1P.PixelYToindex()<11&&puyoMap1P[leftPuyo1P.PixelXToindex()][leftPuyo1P.PixelYToindex()+1]!=null)) {
                        leftPuyo1P.setLocation(leftPuyo1P.getX(), leftPuyo1P.getY()-60);
                        rightPuyo1P.setLocation(leftPuyo1P.getX(), leftPuyo1P.getY()+60);
                    }
                    rightPuyo1P.setLocation(leftPuyo1P.getX(), leftPuyo1P.getY()+60);
                    break;
                }
                // 경우2. 좌우 배치 - (뿌요2)(뿌요1)
                if(leftPuyo1P.getX()>rightPuyo1P.getX()&&leftPuyo1P.getY()==rightPuyo1P.getY()) {
                    rightPuyo1P.setLocation(leftPuyo1P.getX(), leftPuyo1P.getY()-60);
                    break;
                }
                // 경우3. 상하 배치 - (뿌요1)(뿌요2)
                if(leftPuyo1P.getX()==rightPuyo1P.getX()&&leftPuyo1P.getY()<=rightPuyo1P.getY()) {
                    // 예외처리: 뿌요가 벽에서 붙은 채 회전하여 맵을 뚫고 나가는 경우
                    if(leftPuyo1P.PixelXToindex()==0
                            // 예외처리: 뿌요가 다른 뿌요 옆에서 회전하여 다른 뿌요와 겹치는 경우
                            ||(leftPuyo1P.PixelXToindex()>0&&puyoMap1P[leftPuyo1P.PixelXToindex()-1][leftPuyo1P.PixelYToindex()]!=null)) {
                        // 예외처리: 회전하려는 장소에 이미 뿌요가 막혀 있는 경우
                        if(puyoMap1P[leftPuyo1P.PixelXToindex()+1][leftPuyo1P.PixelYToindex()]!=null)
                            break;
                        leftPuyo1P.setLocation(leftPuyo1P.getX()+60, leftPuyo1P.getY());
                        rightPuyo1P.setLocation(leftPuyo1P.getX()-60, leftPuyo1P.getY());
                    }
                    rightPuyo1P.setLocation(leftPuyo1P.getX()-60, leftPuyo1P.getY());
                    break;
                }
                // 경우4. 상하 배치 - (뿌요2)(뿌요1)
                if(leftPuyo1P.getX()==rightPuyo1P.getX()&&leftPuyo1P.getY()>rightPuyo1P.getY()) {
                    // 예외처리: 뿌요가 벽에서 붙은 채 회전하여 맵을 뚫고 나가는 경우
                    if(leftPuyo1P.PixelXToindex()==5
                            // 예외처리: 뿌요가 다른 뿌요 옆에서 회전하여 다른 뿌요와 겹치는 경우
                            ||(leftPuyo1P.PixelXToindex()<5&&puyoMap1P[leftPuyo1P.PixelXToindex()+1][leftPuyo1P.PixelYToindex()]!=null)) {
                        // 예외처리: 회전하려는 장소에 이미 뿌요가 막혀 있는 경우
                        if(puyoMap1P[leftPuyo1P.PixelXToindex()-1][leftPuyo1P.PixelYToindex()]!=null)
                            break;

                        leftPuyo1P.setLocation(leftPuyo1P.getX()-60, leftPuyo1P.getY());
                        rightPuyo1P.setLocation(leftPuyo1P.getX()+60, leftPuyo1P.getY());
                    }
                    rightPuyo1P.setLocation(leftPuyo1P.getX()+60, leftPuyo1P.getY());
                    break;
                }
                break;
            case KeyEvent.VK_S:
                playerThread1P.dropPuyo();
                break;
            case KeyEvent.VK_A:
                //예외처리: 왼쪽에 벽이 있는데 좌측키를 누르는 경우
                if(leftPuyo1P.PixelXToindex()<=0||rightPuyo1P.PixelXToindex()<=0)
                    break;
                //예외처리: 왼쪽에 블록이 있는데 좌측키를 누르는 경우
                if(puyoMap1P[leftPuyo1P.PixelXToindex()-1][leftPuyo1P.PixelYToindex()]!=null
                        ||puyoMap1P[rightPuyo1P.PixelXToindex()-1][rightPuyo1P.PixelYToindex()]!=null)
                    break;

                leftPuyo1P.setLocation(leftPuyo1P.getX()-60,leftPuyo1P.getY());
                rightPuyo1P.setLocation(rightPuyo1P.getX()-60,rightPuyo1P.getY());
                break;
            case KeyEvent.VK_D:
                //예외처리: 오른쪽에 블록 혹은 벽이 있는데 우측키를 누르는 경우
                if(leftPuyo1P.PixelXToindex()>=5||rightPuyo1P.PixelXToindex()>=5)
                    break;
                //예외처리: 오른쪽에 블록이 있는데 우측키를 누르는 경우
                if(puyoMap1P[leftPuyo1P.PixelXToindex()+1][leftPuyo1P.PixelYToindex()]!=null
                        ||puyoMap1P[rightPuyo1P.PixelXToindex()+1][rightPuyo1P.PixelYToindex()]!=null)
                    break;
                leftPuyo1P.setLocation(leftPuyo1P.getX()+60,leftPuyo1P.getY());
                rightPuyo1P.setLocation(rightPuyo1P.getX()+60,rightPuyo1P.getY());
                break;
            /** Player2에 대한 키입력 */
            case KeyEvent.VK_UP:
                //두 뿌요의 위치에 따라서 뿌요를 회전시킨다.
                // 경우1. 좌우 배치 - (뿌요1)(뿌요2)
                if(leftPuyo2P.getX()<=rightPuyo2P.getX()&&leftPuyo2P.getY()==rightPuyo2P.getY()) {
                    // 예외처리: 뿌요가 바닥에서 회전하여 맵을 뚫고 나가는 경우
                    if(leftPuyo2P.PixelYToindex()==11
                            // 예외처리: 뿌요가 다른 뿌요 위에서 회전하여 다른 뿌요와 겹치는 경우
                            ||(leftPuyo2P.PixelYToindex()<11&&puyoMap2P[leftPuyo2P.PixelXToindex()][leftPuyo2P.PixelYToindex()+1]!=null)) {
                        leftPuyo2P.setLocation(leftPuyo2P.getX(), leftPuyo2P.getY()-60);
                        rightPuyo2P.setLocation(leftPuyo2P.getX(), leftPuyo2P.getY()+60);
                    }
                    rightPuyo2P.setLocation(leftPuyo2P.getX(), leftPuyo2P.getY()+60);
                    break;
                }
                // 경우2. 좌우 배치 - (뿌요2)(뿌요1)
                if(leftPuyo2P.getX()>rightPuyo2P.getX()&&leftPuyo2P.getY()==rightPuyo2P.getY()) {
                    rightPuyo2P.setLocation(leftPuyo2P.getX(), leftPuyo2P.getY()-60);
                    break;
                }
                // 경우3. 상하 배치 - (뿌요1)(뿌요2)
                if(leftPuyo2P.getX()==rightPuyo2P.getX()&&leftPuyo2P.getY()<=rightPuyo2P.getY()) {
                    // 예외처리: 뿌요가 벽에서 붙은 채 회전하여 맵을 뚫고 나가는 경우
                    if(leftPuyo2P.PixelXToindex()==0
                            // 예외처리: 뿌요가 다른 뿌요 옆에서 회전하여 다른 뿌요와 겹치는 경우
                            ||(leftPuyo2P.PixelXToindex()>0&&puyoMap2P[leftPuyo2P.PixelXToindex()-1][leftPuyo2P.PixelYToindex()]!=null)) {
                        // 예외처리: 회전하려는 장소에 이미 뿌요가 막혀 있는 경우
                        if(puyoMap2P[leftPuyo2P.PixelXToindex()+1][leftPuyo2P.PixelYToindex()]!=null)
                            break;
                        leftPuyo2P.setLocation(leftPuyo2P.getX()+60, leftPuyo2P.getY());
                        rightPuyo2P.setLocation(leftPuyo2P.getX()-60, leftPuyo2P.getY());
                    }
                    rightPuyo2P.setLocation(leftPuyo2P.getX()-60, leftPuyo2P.getY());
                    break;
                }
                // 경우4. 상하 배치 - (뿌요2)(뿌요1)
                if(leftPuyo2P.getX()==rightPuyo2P.getX()&&leftPuyo2P.getY()>rightPuyo2P.getY()) {
                    // 예외처리: 뿌요가 벽에서 붙은 채 회전하여 맵을 뚫고 나가는 경우
                    if(leftPuyo2P.PixelXToindex()==5
                            // 예외처리: 뿌요가 다른 뿌요 옆에서 회전하여 다른 뿌요와 겹치는 경우
                            ||(leftPuyo2P.PixelXToindex()<5&&puyoMap2P[leftPuyo2P.PixelXToindex()+1][leftPuyo2P.PixelYToindex()]!=null)) {
                        // 예외처리: 회전하려는 장소에 이미 뿌요가 막혀 있는 경우
                        if(puyoMap2P[leftPuyo2P.PixelXToindex()-1][leftPuyo2P.PixelYToindex()]!=null)
                            break;
                        leftPuyo2P.setLocation(leftPuyo2P.getX()-60, leftPuyo2P.getY());
                        rightPuyo2P.setLocation(leftPuyo2P.getX()+60, leftPuyo2P.getY());
                    }
                    rightPuyo2P.setLocation(leftPuyo2P.getX()+60, leftPuyo2P.getY());
                    break;
                }
                break;
            case KeyEvent.VK_DOWN:
                playerThread2P.dropPuyo();
                break;
            case KeyEvent.VK_LEFT:
                //예외처리: 왼쪽에 블록 혹은 벽이 있는데 좌측키를 누르는 경우
                if(leftPuyo2P.PixelXToindex()<=0||rightPuyo2P.PixelXToindex()<=0)
                    break;
                //예외처리: 왼쪽에 블록이 있는데 좌측키를 누르는 경우
                if(puyoMap2P[leftPuyo2P.PixelXToindex()-1][leftPuyo2P.PixelYToindex()]!=null
                        ||puyoMap2P[rightPuyo2P.PixelXToindex()-1][rightPuyo2P.PixelYToindex()]!=null)
                    break;

                leftPuyo2P.setLocation(leftPuyo2P.getX()-60,leftPuyo2P.getY());
                rightPuyo2P.setLocation(rightPuyo2P.getX()-60,rightPuyo2P.getY());
                break;
            case KeyEvent.VK_RIGHT:
                //예외처리: 오른쪽에 블록 혹은 벽이 있는데 우측키를 누르는 경우
                if(leftPuyo2P.PixelXToindex()>=5||rightPuyo2P.PixelXToindex()>=5)
                    break;
                //예외처리: 오른쪽에 블록이 있는데 우측키를 누르는 경우
                if(puyoMap2P[leftPuyo2P.PixelXToindex()+1][leftPuyo2P.PixelYToindex()]!=null
                        ||puyoMap2P[rightPuyo2P.PixelXToindex()+1][rightPuyo2P.PixelYToindex()]!=null)
                    break;

                leftPuyo2P.setLocation(leftPuyo2P.getX()+60,leftPuyo2P.getY());
                rightPuyo2P.setLocation(rightPuyo2P.getX()+60,rightPuyo2P.getY());
                break;
        }
    }
}
