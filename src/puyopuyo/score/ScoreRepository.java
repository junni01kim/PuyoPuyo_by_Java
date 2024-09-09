package puyopuyo.score;

public class ScoreRepository {
    /** 자기 자신 */
    private final ScorePanel scorePanel;



    public ScoreRepository(ScorePanel scorePanel) {
        this.scorePanel = scorePanel;
    }

    public ScorePanel getScorePanel() {return scorePanel;}
}
