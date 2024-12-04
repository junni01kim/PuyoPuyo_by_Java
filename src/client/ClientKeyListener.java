package client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientKeyListener extends KeyAdapter {
    private ClientProcess clientProcess = ClientProcess.getInstance();
    synchronized public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            /** Player1에 대한 키입력 */
            case KeyEvent.VK_W:
                clientProcess.send("Up");
                break;
            case KeyEvent.VK_S:
                clientProcess.send("Down");
                break;
            case KeyEvent.VK_A:
                clientProcess.send("Left");
                break;
            case KeyEvent.VK_D:
                clientProcess.send("Right");
                break;
            /** Player2에 대한 키입력 */
            case KeyEvent.VK_UP:
                clientProcess.send("Up");
                break;
            case KeyEvent.VK_DOWN:
                clientProcess.send("Down");
                break;
            case KeyEvent.VK_LEFT:
                clientProcess.send("Left");
                break;
            case KeyEvent.VK_RIGHT:
                clientProcess.send("Right");
                break;
        }
    }
}
