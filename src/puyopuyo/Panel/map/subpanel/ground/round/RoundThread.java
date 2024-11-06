package puyopuyo.Panel.map.subpanel.ground.round;

public class RoundThread extends Thread{
    private final RoundService roundService;

    public RoundThread(int player) {
        roundService = new RoundService(player);
        roundService.nextPuyo();
    }

    public RoundService getRoundService() {
        return roundService;
    }

    // TODO: 위치 조정 필요
    public void setGarbagePuyo(int plusScore) {
        roundService.setGarbagePuyo(plusScore);
    }

    // TODO: 위치 조정 필요
    public void changeOneWin() {
        roundService.changeOneWin();
    }

    // TODO: 위치 조정 필요
    public void setEnd(boolean state) {
        roundService.setEnd(state);
    }

    @Override
    public void run() {
        roundService.newRound();
    }
}
