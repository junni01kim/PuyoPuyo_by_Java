package puyopuyo.frame;

import javax.swing.*;

public class Frame extends JFrame {
    private final FrameService frameService = new FrameService(this);

    /**
     * 화면을 구성하는 유일한 프레임이다. <br>
     * 게임 시작화면은 GameMenuPanel로 구성된다.
     */
    public Frame() {
        frameService.setUi();

        frameService.openMenuPanel();
    }
}