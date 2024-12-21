package puyopuyo.dto;

/**
 * 0: 메세지
 * 1: 뿌요 맵
 * 
 * @param <T>
 */
public class SendDTO<T> {
    private final int player;
    private final int type;
    private final T data;

    public SendDTO(int player, int type, T data) {
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

    public T getData() {
        return data;
    }
}
