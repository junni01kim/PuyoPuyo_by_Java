package puyopuyo.dto;

public class SendDTO<T> {
    private int player;
    private T data;

    public SendDTO(int player, T data) {
        this.player = player;
        this.data = data;
    }

    public int getPlayer() {
        return player;
    }

    public T getData() {
        return data;
    }
}
