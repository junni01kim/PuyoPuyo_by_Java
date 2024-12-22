<<<<<<<< HEAD:src/puyopuyo/Panel/map/game/GameThread.java
package puyopuyo.Panel.map.game;
========
package puyopuyo.server.game;
>>>>>>>> main:src/puyopuyo/server/game/GameThread.java

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
