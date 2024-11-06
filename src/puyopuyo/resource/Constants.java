package puyopuyo.resource;

/**
 * 게임 점수 로직을 상수로 지정해둔 클래스이다.
 */
public final class Constants {
    /**
     * 연속적으로 뿌요를 폭발 시켰을 시 증가하는 보너스 점수이다.
     */
    public static final int[] COMBO_BONUS = {0,0,8,16,32,64,96,128,160,192,224,256,288,320,352,384,416,448,480,512};

    /**
     * 한번에 폭발하는 뿌요를 통해 증가하는 보너스 점수이다.
     */
    public static final int[] CONNECT_BONUS = {0,0,0,0,0,2,3,4,5,6,7,10};

    /**
     * 총 폭발에서 사용된 뿌요 색상 수를 통해 증가하는 보너스 점수이다.
     */
    public static final int[] COLOR_BONUS = {0,3,6,12,24};
}