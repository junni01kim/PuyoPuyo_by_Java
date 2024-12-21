package puyopuyo.dto;

/**
 * 0: 메세지
 * 1: 뿌요 맵
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
