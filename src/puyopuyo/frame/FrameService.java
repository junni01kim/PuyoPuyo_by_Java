package puyopuyo.frame;

import javax.swing.*;

public class FrameService {
    FrameRepository frameRepository;

    public FrameService(Frame frame) {
        frameRepository = new FrameRepository(frame);
    }

    public void setUi() {
        var frame = frameRepository.getFrame();

        frame.setTitle("뿌요뿌요");
        frame.setSize(1280, 870);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
