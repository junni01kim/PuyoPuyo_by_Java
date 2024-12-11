package puyopuyo.resource;

import javax.swing.*;

/**
 * 게임 ImageIcon을 모듈화 하고 재사용성을 높이기 위한 클래스
 */
public class GameImageIcon {
    /** @property gameMenuPanelIcon <p> GameMenuPanel을 구성하기 위한 백그라운드 디자인 */
    public static final ImageIcon gameMenuPanelIcon = new ImageIcon("res/GameMenuPanel.jpg");
    /** @property gamePanelIcon <p> GamePanel을 구성하기 위한 백그라운드 디자인 */
    public static final ImageIcon gamePanelIcon = new ImageIcon("res/GamePanel.jpg");

    /** @property GroundPanel을 구성하기 위한 백그라운드 이미지 */
    public static final ImageIcon GroundPanelIcon = new ImageIcon("res/blackpixelbackground.png");

    public static final ImageIcon RightPanelIcon = new ImageIcon("res/RightBackGround.png");
    public static final ImageIcon LeftPanelIcon = new ImageIcon("res/LeftBackGround.png");

    /** @property startGameButtonImage <p> startGameButton에 들어갈 이미지 */
    public static final ImageIcon startGameButtonImage = new ImageIcon("res/StartGameButton.png");
    /** @property startGameButtonImage <p> expalinGameButton에 들어갈 이미지 */
    public static final ImageIcon explainGameButtonImage = new ImageIcon("res/Explain.png");

    /* 승리 패배시 GroundPanel에 출력될 이미지 */
    public static final ImageIcon WINImage = new ImageIcon("res/Win.png");
    public static final ImageIcon LOSERImage = new ImageIcon("res/Loser.png");

    /* 플레이어 승리시 ScorePanel에 출력될 승리 횟수 이미지  */
    public static final ImageIcon WINCountIcon = new ImageIcon("res/WinCount.png");
    public static final ImageIcon WInBaseIcon = new ImageIcon("res/WinCountBase.png");
    public static final ImageIcon WINCountedIcon = new ImageIcon("res/WinCounted.png");
    
    private static final ImageIcon greenPuyo = new ImageIcon("res/GreenPuyo.png");
    private static final ImageIcon redPuyo = new ImageIcon("res/RedPuyo.png");
    private static final ImageIcon yelloPuyo = new ImageIcon("res/YellowPuyo.png");
    private static final ImageIcon bluePuyo = new ImageIcon("res/BluePuyo.png");
    private static final ImageIcon purplePuyo = new ImageIcon("res/PurplePuyo.png");
    private static final ImageIcon garbagePuyo =  new ImageIcon("res/GarbagePuyo.png");
    /** @property puyoIcon <p> 각 뿌요 색상을 지정해 둔 배열 <br> 0.초록, 1.빨강, 2.노랑, 3.파랑 4.보라, 5.방해 */
    public static final ImageIcon[] puyoIcon = {greenPuyo,redPuyo,yelloPuyo,bluePuyo,purplePuyo,garbagePuyo};

    public static final ImageIcon player1Ground = new ImageIcon("res/Player1Ground.png");
    public static final ImageIcon player2Ground = new ImageIcon("res/Player2Ground.png");
}
