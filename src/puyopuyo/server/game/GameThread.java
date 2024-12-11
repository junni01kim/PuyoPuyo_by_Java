package puyopuyo.server.game;

public class GameThread extends Thread {
    private static GameThread instance;

    public synchronized static GameThread getInstance() {
        if (instance == null) {
            instance = new GameThread();
        }
        return instance;
    }

    @Override
    public void run() {
        super.run();
        var gameService = GameService.getInstance();

        gameService.newGame();
    }
}
