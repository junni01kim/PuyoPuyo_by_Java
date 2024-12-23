package puyopuyo.client.panel.map.subpanel.socket_command;

/**
 * RedrawCommand를 생성하는 Factory 클래스
 *
 * Factory Pattern을 적용하였다.
 * 
 * TODO: 변경 예정
 */
public class SocketFactory {
    public static SocketCommand getDrawCommand(int type) {
        switch (type) {
            case 0:
                System.out.println("redraw: player2 Puyo Map(Just Put)");
                return Player2DrawPuyoMapCommand.getInstance();

            case 1:
                System.out.println("redraw: player1 Puyo Map(Just Put)");
                return Player1DrawPuyoMapCommand.getInstance();
            case 2:
                System.out.println("redraw: LR Puyo");
                return DrawLRPuyoCommand.getInstance();

            case 3:
                System.out.println("redraw: player1 splash");
                return Player1Splash.getInstance();

            case 4:
                System.out.println("redraw: player2 splash");
                return Player2Splash.getInstance();

            case 5:
                System.out.println("redraw: Timer");
                return TimerCommand.getInstance();

            case 6:
                System.out.println("redraw: player2 NextPuyo");
                return Player2NextPuyo.getInstance();

            case 7:
                System.out.println("redraw: player1 NextPuyo");
                return Player1NextPuyo.getInstance();

            case 8:
                System.out.println("redraw: player2 win");

                // TODO: player2 승리모션
                // TODO: player1 패배모션
                return ClearCommand.getInstance();

            case 9:
                System.out.println("redraw: player1 win");

                // TODO: player1 승리모션
                // TODO: player2 패배모션
                return ClearCommand.getInstance();

            case 10:
                System.out.println("Game End");
                return EndCommand.getInstance();

            case 11:
                System.out.println("print: Player2 Score");
                return Player2ScoreCommand.getInstance();

            case 12:
                System.out.println("print: Player1 Score");
                return Player1ScoreCommand.getInstance();

            case 13:
                System.out.println("print: Player2 Garbage Puyo");
                return Player2GarbagePuyo.getInstance();

            case 14:
                System.out.println("print: Player1 Garbage Puyo");
                return Player1GarbagePuyo.getInstance();

            default:
                System.out.println("redraw: Message");
                return MessageCommand.getInstance();
        }
    }
}
