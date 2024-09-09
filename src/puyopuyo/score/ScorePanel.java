package puyopuyo.score;

import javax.swing.*;

public class ScorePanel extends JPanel {
    private final ScoreService scoreService = new ScoreService(this);

    public ScorePanel() {
        scoreService.setUi();
    }
}