package puyopuyo.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 클라이언트가 이용할 KeyListener
 *
 * 해당 정보는 String으로 player정보와 함게 서버에 전송된다.
 */
public class ClientKeyListener extends KeyAdapter {
    private ClientProcess clientProcess = ClientProcess.getInstance();

    synchronized public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            /** Player에 대한 키입력 */
            case KeyEvent.VK_UP:
                clientProcess.toServer("Up");
                break;
            case KeyEvent.VK_DOWN:
                clientProcess.toServer("Down");
                break;
            case KeyEvent.VK_LEFT:
                clientProcess.toServer("Left");
                break;
            case KeyEvent.VK_RIGHT:
                clientProcess.toServer("Right");
                break;
        }
    }
}
