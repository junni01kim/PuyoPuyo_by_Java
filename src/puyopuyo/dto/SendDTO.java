package puyopuyo.dto;

/**
 * 서버와 클라이언트의 통신을 위한 클래스
 *
 * [서버] 클라이언트에게 플레이어의 방향키 정보를 받는다.
 * [클라이언트] type에 맞는 동작 수행 SocketFactory 참고
 * 
 * @param
 */
public class SendDTO {
    private final int player;
    private final int type;
    private final String data;

    public SendDTO(int player, int type, String data) {
        this.player = player;
        this.type = type;
        this.data = data;
    }

    public int getPlayer() {
        return player;
    }

    public int getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}
