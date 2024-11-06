package puyopuyo.frame;

import javax.swing.*;

public class Frame extends JFrame {
    private static Frame instance;

    private PanelState child;

    public synchronized static Frame getInstance() {
        if (instance == null) {
            instance = new Frame();
        }
        return instance;
    }

    /**
     * 화면을 구성하는 유일한 프레임이다. <br>
     * 게임 시작화면은 GameMenuPanel로 구성된다.
     */
    public Frame() {
        setUi();
    }

    public void setUi() {
        this.setTitle("Puyo Puyo");
        this.setSize(1280, 870);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void changePanel(PanelState panel) {
        if(child != null) child.close();

        child = panel;
        child.open();
    }
}