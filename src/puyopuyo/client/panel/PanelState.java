package puyopuyo.client.panel;

import puyopuyo.client.frame.Frame;

/**
 * Frame의 Panel 객체를 관리하기 위한 인터페이스 <br>
 *
 * 게임 플레이 화면을 이루는 최상위 패널의 필수적인 구성 요소를 명시한다. <br>
 *
 * 상태 패턴(state pattern)을 적용하여 개발했다.
 */
public interface PanelState {
    /**
     * 패널의 UI를 관리하는 함수이다. <br>
     * 구현 후 공통적으로 생성자 내부에 적용 시키는 것을 권장한다.
     */
    void setUi();

    /**
     * 해당 패널로의 화면 전환을 위한 로직을 작성하는 함수이다.<br>
     *
     * 상위 컨테이너인 Frame.changePanel()에 변경 로직이 작성되어있다.
     */
    void open(Frame frame);

    /**
     * 해당 패널의 삭제를 위한 로직을 작성하는 함수이다.<br>
     *
     * 상위 컨테이너인 Frame.changePanel()에 변경 로직이 작성되어있다.
     */
    void close(Frame frame);
}
