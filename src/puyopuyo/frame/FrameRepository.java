package puyopuyo.frame;

import puyopuyo.menu.MenuPanel;

public class FrameRepository {
    private final Frame frame;
    private final MenuPanel menuPanel = new MenuPanel();

    public FrameRepository(Frame frame) {
        this.frame = frame;
    }

    public Frame getFrame() {return frame;}
}
