package puyopuyo.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientKeyListener extends KeyAdapter {
    private ClientProcess clientProcess = ClientProcess.getInstance();

    synchronized public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            /** Player1에 대한 키입력 */
            case KeyEvent.VK_W:
                clientProcess.toServer("Up");
                break;
            case KeyEvent.VK_S:
                clientProcess.toServer("Down");
                break;
            case KeyEvent.VK_A:
                clientProcess.toServer("Left");
                break;
            case KeyEvent.VK_D:
                clientProcess.toServer("Right");
                break;
            /** Player2에 대한 키입력 */
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
