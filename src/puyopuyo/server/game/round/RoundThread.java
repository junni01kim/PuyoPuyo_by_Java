package puyopuyo.server.game.round;

public class RoundThread extends Thread{
    private final RoundService roundService;

    public RoundThread(int player) {
        roundService = new RoundService(player);
    }

    public RoundService getRoundService() {
        return roundService;
    }

    @Override
    public void run() {
        roundService.newRound();
    }
}
