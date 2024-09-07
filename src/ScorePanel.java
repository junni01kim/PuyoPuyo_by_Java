import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	/** @property nextLeftControlPuyo1P 다음 왼쪽 뿌요의 색상 */
	Puyo nextLeftControlPuyo1P = new Puyo(0);

	/** @property nextRightControlPuyo1P 다음 오른쪽 뿌요의 색상 */
	Puyo nextRightControlPuyo1P = new Puyo(0);

	/** @property nextLeftControlPuyo2P 다음 왼쪽 뿌요의 색상 */
	Puyo nextLeftControlPuyo2P = new Puyo(0);

	/** @property nextRightControlPuyo2P 다음 오른쪽 뿌요의 색상 */
	Puyo nextRightControlPuyo2P = new Puyo(0);

	/** 외부에서 참조하는 Label 목록 (동적인 값을 가진다) */
	private final JLabel roundCountLabel = new JLabel("1");
    private final JLabel scoreLabel1P = new JLabel("0");
	private final JLabel scoreLabel2P = new JLabel("0");
	private final JLabel numberOfGarbagePuyoLabel1P = new JLabel("임시");
	private final JLabel numberOfGarbagePuyoLabel2P = new JLabel("임시");

    /** next0000ControlPuyo0P getter */
	public Puyo getNextLeftControlPuyo1P() {return nextLeftControlPuyo1P;}
	public Puyo getNextRightControlPuyo1P() {return nextRightControlPuyo1P;}
	public Puyo getNextLeftControlPuyo2P() {return nextLeftControlPuyo2P;}
	public Puyo getNextRightControlPuyo2P() {return nextRightControlPuyo2P;}

	/** scoreLabel0P getter */
	public JLabel getScoreLabel1P() {return scoreLabel1P;}
	public JLabel getScoreLabel2P() {return scoreLabel2P;}

	/** numberOfGarbagePuyoLabel0P getter */
	public JLabel getNumberOfGarbagePuyoLabel1P() {return numberOfGarbagePuyoLabel1P;}
	public JLabel getNumberOfGarbagePuyoLabel2P() {return numberOfGarbagePuyoLabel2P;}

	/**
	 * 게임 라운드 내 인게임 정보를 나타내는 화면이다.
	 * <p>
	 * 1. 기본세팅: 키보드 값을 받기위한 리스너가 존재한다. <br>
	 * 2. 다음 뿌요 생성기 제작
	 * 3. 각종 텍스트 생성
	 * 4. 라운드 표시
	 * 5. 점수 표시
	 * 6. 타이머 표시
	 * 7. 전달할 방해뿌요 개수 표시
	 */
	ScorePanel() {
		setBackground(Color.YELLOW);
		setSize(300, 750);
		setLayout(null);

		// 다음 뿌요 색상 1P
		nextLeftControlPuyo1P.setLocation(12+this.getWidth()/4-60,100);
		nextRightControlPuyo1P.setLocation(12+this.getWidth()/4,100);
		add(nextLeftControlPuyo1P);
		add(nextRightControlPuyo1P);
		
		// 다음 뿌요 색상 2P
		nextLeftControlPuyo2P.setLocation(this.getWidth()/4*3-60-12,100);
		nextRightControlPuyo2P.setLocation(this.getWidth()/4*3-12,100);
		add(nextLeftControlPuyo2P);
		add(nextRightControlPuyo2P);

		// 라운드 표시 (변동되는 숫자)
        roundCountLabel.setFont(new Font("Serif", Font.BOLD, 27));
		roundCountLabel.setLocation(this.getWidth()/2-75, 43);
		roundCountLabel.setSize(300, 30);
		add(roundCountLabel);

		// 라운드 표시 (고정 텍스트)
        JLabel roundLabel = new JLabel("R O U N D");
        roundLabel.setFont(new Font("Serif", Font.BOLD, 27));
		roundLabel.setLocation(this.getWidth()/2-50, 43);
		roundLabel.setSize(300, 30);
		add(roundLabel);

		// 점수 표시 (타이틀)
        JLabel scoreTextLabel = new JLabel("S C O R E");
        scoreTextLabel.setFont(new Font("Serif", Font.BOLD, 15));
		scoreTextLabel.setLocation(this.getWidth()/2-29, 175);
		scoreTextLabel.setSize(100, 30);
		add(scoreTextLabel);

		// 점수 표시 1P (변동되는 숫자)
		setLabel(scoreLabel1P, this.getWidth()/2, 205);
		add(scoreLabel1P);

		// 점수 표시 2P (변동되는 숫자)
		setLabel(scoreLabel1P, this.getWidth()/2, 235);
		add(scoreLabel2P);

		// 타이머 표시 (타이틀)
        JLabel timerTextLabel = new JLabel("T I M E");
		setLabel(timerTextLabel, this.getWidth()/2-20, 360);
		add(timerTextLabel);

		// 타이머 표시 (변동되는 숫자)
        JLabel timerLabel = new JLabel("0");
		setLabel(timerLabel, this.getWidth()/2, 400);
		add(timerLabel);

		// 전달할 방해뿌요 표시 1P (변동되는 숫자)
		// TODO: 넘길 방해 뿌요 개발 완료 시 함께 수정
		numberOfGarbagePuyoLabel1P.setLocation(this.getWidth()/2-75, 600);
		numberOfGarbagePuyoLabel1P.setSize(100,30);
		add(numberOfGarbagePuyoLabel1P);

		// 전달할 방해뿌요 표시 2P (변동되는 숫자)
		// TODO: 넘길 방해 뿌요 개발 완료 시 함께 수정
		numberOfGarbagePuyoLabel2P.setLocation(this.getWidth()/2+50, 600);
		numberOfGarbagePuyoLabel2P.setSize(100,30);
		add(numberOfGarbagePuyoLabel2P);

		// TODO: 넘길 방해 뿌요 수 그림
		// TODO: 남은 라운드 현재 라운드 수
		// TODO: 자신의 점수
	}

	/**
	 * scorePanel 생성자의 내용이 너무 많아서 공통되는 작업 모듈화
	 *
	 * @param label 설정할 Label 객체
	 * @param x 배치 시킬 x좌표
	 * @param y 배치 시킬 y좌표
	 */
	void setLabel(JLabel label, int x, int y) {
		label.setFont(new Font("Serif", Font.BOLD, 15));
		label.setLocation(this.getWidth()/2-20, 360);
		label.setSize(100, 30);
	}
}
