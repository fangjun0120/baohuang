package jfang.games.baohuang.Adapter;

import jfang.games.baohuang.common.util.DateUtil;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.po.GamePO;

import java.util.stream.Collectors;

/**
 * @author jfang
 */
public class GameConverter {

    public static GamePO convertGameToPO(Game game) {
        GamePO gamePO = new GamePO();
        gamePO.setId(game.getId());
        gamePO.setRoomId(game.getRoomId());
        if (game.getPlayers() != null) {
            gamePO.setPlayerList(game.getPlayers().stream()
                    .map(p -> p.getUserId().toString())
                    .collect(Collectors.joining(","))
            );
            gamePO.setRankList(game.getPlayers().stream()
                    .map(p -> String.valueOf(p.getRank()))
                    .collect(Collectors.joining(","))
            );
        }
        if (game.getKing() != null) {
            gamePO.setKing(game.getPlayerByIndex(game.getKing()).getUserId());
        }
        if (game.getAgent() != null) {
            gamePO.setAgent(game.getPlayerByIndex(game.getAgent()).getUserId());
        }
        if (game.getAgentCard() != null) {
            gamePO.setAgentCard(game.getAgentCard().toDisplayString());
        }
        gamePO.setHasRevolution(game.isHasRevolution());
        gamePO.setIsKingOverFourPublic(game.isKingOverFourPublic());
        gamePO.setCreated(DateUtil.localDateTimeToLong(game.getCreatedTime()));
        gamePO.setUpdated(DateUtil.localDateTimeToLong(game.getUpdatedTime()));
        return gamePO;
    }
}
