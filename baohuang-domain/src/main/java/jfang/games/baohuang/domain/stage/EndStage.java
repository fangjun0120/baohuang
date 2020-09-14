package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.common.message.PlayerOptionEnum;
import jfang.games.baohuang.common.message.PlayerOptions;
import jfang.games.baohuang.common.message.PlayerRank;
import jfang.games.baohuang.domain.constant.GameStageEnum;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.entity.Player;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author jfang
 */
public class EndStage implements GameStage {

    private Set<Integer> nextSet = new HashSet<>();

    @Override
    public void run(Game game) {
        // 检查下最终状态
        for (Player player: game.getPlayers()) {
            if (player.getRank() == null) {
                game.addCompletedPlayer(player.getIndex(), false);
            }
        }
        game.buildRank();
        List<PlayerRank> rankList = game.getPlayers().stream().map(p -> {
            PlayerRank playerRank = new PlayerRank();
            playerRank.setUsername(p.getDisplayName());
            playerRank.setScore(p.getScore());
            return playerRank;
        }).collect(Collectors.toList());
        for (Player player: game.getPlayers()) {
            game.updatePlayerInfo(player.getIndex(), PlayerOptions.of(PlayerOptionEnum.ENDING, rankList));
        }
    }

    @Override
    public GameControl onPlayerMessage(Game game, MessageDTO messageDTO) {
        GameControl gameControl = new GameControl();
        Long userId = messageDTO.getPlayerCallback().getUserId();
        Player player = game.getPlayerByUserId(userId);
        if (Boolean.TRUE.equals(messageDTO.getPlayerOptionResponse().getResponse())) {
            nextSet.add(player.getIndex());
            if (nextSet.size() == 5) {
                gameControl.setRestart(true);
            }
        } else {
            gameControl.setPlayerToRemove(player);
        }
        return gameControl;
    }

    @Override
    public int getValue() {
        return GameStageEnum.END.getValue();
    }
}
