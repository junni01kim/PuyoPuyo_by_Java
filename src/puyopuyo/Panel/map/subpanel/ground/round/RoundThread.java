package puyopuyo.Panel.map.subpanel.ground.round;

public class RoundThread extends Thread{
    private final RoundService2 roundService;

    public RoundThread(int player) {
        roundService = new RoundService2(player);
    }

    public RoundService2 getRoundService() {
        return roundService;
    }

    @Override
    public void run() {
        roundService.newRound();
    }
}
