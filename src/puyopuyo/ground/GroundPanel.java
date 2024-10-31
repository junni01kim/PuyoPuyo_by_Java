package puyopuyo.ground;

import javax.swing.*;
import java.awt.*;

import puyopuyo.puyo.GameImageIcon;

public class GroundPanel extends JPanel {
    private final GroundService groundService = new GroundService(this);
    
    // 추가: 좌우 배경 이미지를 위한 변수 선언
    private final Image leftBackgroundImage = GameImageIcon.GroundPanelIcon.getImage();
    private final Image rightBackgroundImage = GameImageIcon.GroundPanelIcon.getImage();

    public GroundPanel() {
        groundService.setUi();
    }

    public GroundService getGroundServices() { return groundService; }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;


        // 파란색 배경 대신 이미지를 추가 - 왼쪽과 오른쪽에 각각 그리기
        int sidePanelWidth = (int) (this.getWidth() * 0.3); // 좌우 영역 너비 비율 설정
        int centerStartX = (this.getWidth() - sidePanelWidth) / 2;

        // 왼쪽 이미지 그리기
        g2d.drawImage(leftBackgroundImage, 0, 0, sidePanelWidth, this.getHeight(), this);

        // 오른쪽 이미지 그리기
        g2d.drawImage(rightBackgroundImage, centerStartX + sidePanelWidth, 0, sidePanelWidth, this.getHeight(), this);

        // 가운데 GroundPanelIcon 이미지 그리기
        g2d.drawImage(GameImageIcon.GroundPanelIcon.getImage(), centerStartX, 0, sidePanelWidth, this.getHeight(), this);
    }
}
