package puyopuyo.client.panel.map.subpanel.ground;

import puyopuyo.resource.GameImageIcon;

import javax.swing.*;

/**
 * 시각적으로 뿌요 UI를 제작하는 클래스
 *
 * PuyoMap의 Index를 기반으로 출력될 위치를 배치한다.
 *
 * 뿌요 색상은 0.초록 1.빨강 2.노랑 3.파랑 4.보라 5.방해이다.
 */
public class Puyo extends JLabel {
    /**
     * 위치를 지정하지 않고 생성하는 생성자
     *
     * @param color 생성할 뿌요 색
     */
    public Puyo(int color){
        super();

        setSize(60,60);

        // 관련 이미지는 puyopuyo/resource/GameImageIcon.java 및 ../res/*Puyo.png를 참고할 것
        setIcon(GameImageIcon.puyoIcon[color]);
    }

    /**
     * 위치를 지정하고 생성하는 생성자
     *
     * @param color 생성할 뿌요 색
     * @param indexX 배치할 뿌요의 위치
     * @param indexY 배치할 뿌요의 위치
     */
    public Puyo(int color, int indexX, int indexY){
        super();

        pos(indexX, indexY);
        setSize(60,60);
        
        // 관련 이미지는 puyopuyo/resource/GameImageIcon.java 및 ../res/*Puyo.png를 참고할 것
        setIcon(GameImageIcon.puyoIcon[color]);

        setVisible(true);
    }

    /**
     * 뿌요의 새로운 위치를 지정한다.
     * 
     * @param x x축 인덱스
     * @param y y축 인덱스
     */
    public void pos(int x, int y) {
        setLocation(20+x*60, 10+y*60);
    }

    /**
     * 뿌요 이미지에 대한 책임을 유지하기 위한 함수로, GameImageIcon을 전달한다.
     *
     * TODO: 수정해도 괜찮을듯
     */
    public static ImageIcon[] getPuyoIcon() {return GameImageIcon.puyoIcon;}
}