package puyopuyo.score;

import puyopuyo.puyo.Puyo;

import javax.swing.*;
import java.awt.*;

public class ScoreService {
    private final ScoreRepository scoreRepository = new ScoreRepository();

    public JLabel getRoundCountLabel() {
        return scoreRepository.getRoundCountLabel();
    }

    public JLabel getScoreLabel(int player) {
        if(player == 1) return scoreRepository.getScoreLabel1P();
        else return scoreRepository.getScoreLabel2P();
    }

    public JLabel getNumberOfGarbagePuyoLabel(int player) {
        if(player == 1) return scoreRepository.getNumberOfGarbagePuyoLabel1P();
        else return scoreRepository.getNumberOfGarbagePuyoLabel2P();
    }

    public Puyo getNextLeftPuyo(int iAm) {
        if(iAm == 1) return scoreRepository.getNextLeftPuyo1P();
        else return scoreRepository.getNextLeftPuyo2P();
    }

    public Puyo getNextRightPuyo(int iAm) {
        if(iAm == 1) return scoreRepository.getNextRightPuyo1P();
        else return scoreRepository.getNextRightPuyo2P();
    }
}
