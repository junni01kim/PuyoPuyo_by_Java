package puyopuyo.client.panel.map.subpanel;

import com.google.gson.reflect.TypeToken;
import puyopuyo.client.ClientProcess;
import puyopuyo.client.panel.map.MapService;
import puyopuyo.dto.SendDTO;
import puyopuyo.server.game.round.PuyoS;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DrawFactory {
    public static void redraw(SendDTO sendDTO) {
        System.out.println("redraw");
        switch (sendDTO.getType()) {
            case 0:
                // Type 0: 메시지 처리 (추후 구현 가능)
                System.out.println("Message: " + sendDTO.getData());
                break;

            case 1:
                // Type 1: 뿌요 맵 처리
                try {
                    // JSON을 ArrayList<PuyoS[][]>로 역직렬화
                    Type type = new TypeToken<ArrayList<PuyoS[][]>>() {}.getType();
                    ArrayList<PuyoS[][]> puyoMaps = ClientProcess.getGson().fromJson(sendDTO.getData(), type);

                    // 각 플레이어의 맵을 그림
                    for (int player = 0; player < puyoMaps.size(); player++) {
                        MapService.getInstance()
                                .getGroundPanel(player)
                                .drawPuyoMap(puyoMaps.get(player));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Failed to deserialize data for type 1.");
                }
                break;

            default:
                System.err.println("Unknown type: " + sendDTO.getType());
                break;
        }
    }
}
