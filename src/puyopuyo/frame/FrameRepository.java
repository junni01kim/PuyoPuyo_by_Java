package puyopuyo.frame;

import puyopuyo.menu.MenuPanel;

public class FrameRepository {
    /** 자기 자신 */
    private final Frame frame;
    /** 화면 전환을 위한 Ui */
    private final MenuPanel menuPanel = new MenuPanel();

    public FrameRepository(Frame frame) {
        this.frame = frame;
    }

    public Frame getFrame() {return frame;}

    public MenuPanel getMenuPanel() {return menuPanel;}
}
