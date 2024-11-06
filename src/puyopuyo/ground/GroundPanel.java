package puyopuyo.ground;

import javax.swing.*;
import java.awt.*;

import puyopuyo.puyo.GameImageIcon;

public class GroundPanel extends JPanel {
    private final GroundService groundService = new GroundService(this);

    public GroundPanel() {
        groundService.setUi();
    }

    private final Image RightgroundImage = GameImageIcon.RightPanelIcon.getImage(); // 배경 이미지 설정
    private final Image LeftgroundImage = GameImageIcon.LeftPanelIcon.getImage(); // 배경 이미지 설정


    public GroundService getGroundServices() {return groundService;}


    
    /**
     * 
     * @param graphics
     */

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;


        // 패널의 너비를 반으로 나눠서 좌우 영역 설정
        int halfWidth = this.getWidth() / 2;

        // 왼쪽 이미지 배경 그리기
        g2d.drawImage(LeftgroundImage, 0, 0, halfWidth, this.getHeight(), this);

        // 오른쪽 이미지 배경 그리기
        g2d.drawImage(RightgroundImage, halfWidth, 0, halfWidth, this.getHeight(), this);
    }
}
