package puyopuyo.client.frame;

import puyopuyo.client.panel.PanelState;

import javax.swing.*;

/**
 * 화면을 구성하는 틀을 제공하는 클래스
 *
 * 프로젝트에서 윈도우 화면은 하나만 구현되므로, 싱글톤 패턴을 이용하여 개발했다.
 */
public class Frame extends JFrame {
    private static Frame instance;

    private PanelState child;

    /**
     * Frame 객체를 반환한다. <br>
     *
     * 객체 생성 시기는 프로젝트 상 최소 함수 호출 시 이다.
     */
    public synchronized static Frame getInstance() {
        if (instance == null) {
            instance = new Frame();
        }
        return instance;
    }

    /**
     * 화면을 구성하는 유일한 프레임이다. <br>
     *
     * 게임 시작화면은 GameMenuPanel로 구성된다.
     */
    public Frame() {
        setUi();
    }

    /**
     * 화면 설정을 하는 함수이다.<br>
     *
     * 1. 화면 창의 이름은 'Puyo Puyo'이다.<br>
     * 2. 화면 사이즈: 1280, 870<br>
     * 3. 윈도우 창 종료 시 프로세스가 종료된다.
     */
    public void setUi() {
        this.setTitle("Puyo Puyo");
        this.setSize(1280, 870);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 화면에 생성되는 최상단 패널을 변경한다.<br>
     *
     * 기존의 패널은 삭제되며, 다음 패널이 출력된다.
     */
    public void changePanel(PanelState panel) {
        if(child != null) {
            child.close(this);
        }

        child = panel;
        child.open(this);
    }
}