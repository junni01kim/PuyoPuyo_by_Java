package puyopuyo.client.panel.map.subpanel;

import com.google.gson.reflect.TypeToken;
import puyopuyo.client.ClientProcess;
import puyopuyo.client.panel.map.MapService;
import puyopuyo.client.panel.map.subpanel.score.ScorePanel;
import puyopuyo.dto.SendDTO;
import puyopuyo.server.game.round.PuyoS;

import java.lang.reflect.Type;
import java.util.ArrayList;

// TODO: 커멘드 패턴으로 변경하기
public class DrawFactory {
    public static void redraw(SendDTO sendDTO) {
        switch (sendDTO.getType()) {
            case 0:
                System.out.println("redraw: Message");
                // Type 0: 메시지 처리 (추후 구현 가능)
                ClientProcess.getInstance().setPlayer(Integer.parseInt(sendDTO.getData()));
                break;

            case 1:
                System.out.println("redraw: Puyo Map");
                // Type 1: 뿌요 맵 처리
                Type type1 = new TypeToken<ArrayList<PuyoS[][]>>() {}.getType();
                ArrayList<PuyoS[][]> puyoMaps = ClientProcess.getGson().fromJson(sendDTO.getData(), type1);

                // 각 플레이어의 맵을 그림
                for (int player = 0; player < puyoMaps.size(); player++) {
                    // Puyo Map 지우기
                    MapService.getInstance()
                            .getGroundPanel(player)
                            .clearPuyoMap();

                    // Puyo Map 그리기
                    MapService.getInstance()
                            .getGroundPanel(player)
                            .drawPuyoMap(puyoMaps.get(player));
                }
                break;
            case 2:
                System.out.println("redraw: LR Puyo");
                // Left/Right Puyo 처리
                Type type2 = new TypeToken<ArrayList<PuyoS[]>>() {}.getType();
                ArrayList<PuyoS[]> lrPuyo = ClientProcess.getGson().fromJson(sendDTO.getData(), type2);

                for (int player = 0; player < lrPuyo.size(); player++) {
                    // LR Puyo 지우기
                    MapService.getInstance()
                            .getGroundPanel(player)
                            .clearLrPuyo();
                    
                    // LR Puyo 그리기
                    MapService.getInstance()
                            .getGroundPanel(player)
                            .drawLrPuyo(lrPuyo.get(player));
                }
                break;
            case 3:
                System.out.println("redraw: player1 splash");

                // Type 1: 뿌요 맵 처리
                Type type3 = new TypeToken<ArrayList<PuyoS[][]>>() {}.getType();
                puyoMaps = ClientProcess.getGson().fromJson(sendDTO.getData(), type3);

                // LR Puyo 지우기
                MapService.getInstance()
                        .getGroundPanel(1)
                        .clearLrPuyo();

                // 각 플레이어의 맵을 그림
                for (int player = 0; player < puyoMaps.size(); player++) {
                    // Puyo Map 지우기
                    MapService.getInstance()
                            .getGroundPanel(player)
                            .clearPuyoMap();

                    // Puyo Map 그리기
                    MapService.getInstance()
                            .getGroundPanel(player)
                            .drawPuyoMap(puyoMaps.get(player));
                }
                break;

            case 4:
                System.out.println("redraw: player2 splash");

                // Type 1: 뿌요 맵 처리
                Type type4 = new TypeToken<ArrayList<PuyoS[][]>>() {}.getType();
                puyoMaps = ClientProcess.getGson().fromJson(sendDTO.getData(), type4);

                // LR Puyo 지우기
                MapService.getInstance()
                        .getGroundPanel(0)
                        .clearLrPuyo();

                // 각 플레이어의 맵을 그림
                for (int player = 0; player < puyoMaps.size(); player++) {
                    // Puyo Map 지우기
                    MapService.getInstance()
                            .getGroundPanel(player)
                            .clearPuyoMap();

                    // Puyo Map 그리기
                    MapService.getInstance()
                            .getGroundPanel(player)
                            .drawPuyoMap(puyoMaps.get(player));
                }
                break;

            case 5:
                System.out.println("redraw: Timer");
                MapService.getInstance()
                        .getScorePanel()
                        .getScoreService()
                        .setTimer(Integer.parseInt(sendDTO.getData()));
                break;

            case 6:
                System.out.println("redraw: player1 NextPuyo");
                break;

            case 7:
                System.out.println("redraw: player2 NextPuyo");
                break;

            default:
                System.err.println("Unknown type: " + sendDTO.getType());
                break;
        }
    }
}
