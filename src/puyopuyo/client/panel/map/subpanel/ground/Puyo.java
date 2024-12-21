package puyopuyo.client.panel.map.subpanel.ground;

import puyopuyo.resource.GameImageIcon;

import javax.swing.*;

public class Puyo extends JLabel {
    /** 뿌요는 0.초록 1.빨강 2.노랑 3.파랑 4.보라 5.방해이다. */
    private int color;

    public int x() {
        return (getX()-20)/60;
    }

    public int y() {
        return (getY()-10)/60;
    }

    public void pos(int x, int y) {
        setLocation(20+x*60, 10+y*60);
    }

    public void setColor(int color) {this.color = color;}
    public int getColor() {return color;}
    
    public static ImageIcon[] getPuyoIcon() {return GameImageIcon.puyoIcon;}

    /**
     * 위치를 지정하지 않는 생성자
     *
     * @param color 생성할 뿌요 색
     */
    public Puyo(int color){
        super();

        this.color = color;

        setSize(60,60);
        setIcon(GameImageIcon.puyoIcon[color]);
    }

    /**
     * 위치를 생성하는 지정자
     *
     * @param color 생성할 뿌요 색
     * @param indexX 배치할 뿌요의 위치
     * @param indexY 배치할 뿌요의 위치
     */
    public Puyo(int color, int indexX, int indexY){
        super();

        this.color = color;
        pos(indexX, indexY);
        setSize(60,60);
        setIcon(GameImageIcon.puyoIcon[color]);

        setVisible(true);
    }
}