package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.common.message.PlayerOptionEnum;
import jfang.games.baohuang.common.message.PlayerOptions;
import jfang.games.baohuang.common.message.PlayerRank;
import jfang.games.baohuang.domain.constant.GameStageEnum;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.entity.Player;
import jfang.games.baohuang.domain.repo.RepoUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jfang
 */
public class EndStage implements GameStage {

    @Override
    public void run(Game game) {
        // 检查下最终状态
        for (Player player: game.getPlayers()) {
            if (player.getRank() == null) {
                game.addCompletedPlayer(player.getIndex(), false);
            }
        }
        game.buildRank();
        RepoUtil.gameRepo.saveGame(game);
        List<PlayerRank> rankList = game.getPlayers().stream().map(p -> {
            PlayerRank playerRank = new PlayerRank();
            playerRank.setUsername(p.getDisplayName());
            playerRank.setScore(p.getScore());
            return playerRank;
        }).collect(Collectors.toList());
        for (Player player: game.getPlayers()) {
            game.updatePlayerInfo(player.getIndex(), PlayerOptions.of(PlayerOptionEnum.ENDING, rankList));
        }
        game.setCompleted(true);
    }

    @Override
    public void onPlayerMessage(Game game, MessageDTO messageDTO) {
    }

    @Override
    public int getValue() {
        return GameStageEnum.END.getValue();
    }
}
