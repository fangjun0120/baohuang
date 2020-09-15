package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.common.message.PlayerOptionEnum;
import jfang.games.baohuang.common.message.PlayerOptions;
import jfang.games.baohuang.domain.constant.GameStageEnum;
import jfang.games.baohuang.domain.constant.GuideMessage;
import jfang.games.baohuang.domain.constant.PlayerStatus;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.entity.Player;
import jfang.games.baohuang.domain.repo.RepoUtil;

/**
 * 是否明牌一打四，是否起义
 *
 * @author jfang
 */
public class RevolutionStage implements GameStage {

    @Override
    public void run(Game game) {
        for (Player player: game.getPlayers()) {
            if (game.getKing().equals(player.getIndex())) {
                if (game.isKingOverFour()) {
                    player.setStatus(PlayerStatus.PLAYING);
                    game.updatePlayerInfo(player.getIndex(), PlayerOptions.of(PlayerOptionEnum.ONE_OVER_FOUR, null));
                } else {
                    player.setStatus(PlayerStatus.WAITING);
                    game.updatePlayerInfo(player.getIndex(), null);
                }
            } else if (game.getAgent().equals(player.getIndex())) {
                player.setStatus(PlayerStatus.WAITING);
                game.updatePlayerInfo(player.getIndex(), null);
            } else {
                player.setStatus(PlayerStatus.PLAYING);
                game.updatePlayerInfo(player.getIndex(), PlayerOptions.of(PlayerOptionEnum.REVOLUTION, null));
            }
        }
        RepoUtil.messageRepo.broadcastRoom(game.getRoomId(), GuideMessage.REVOLUTION);
    }

    @Override
    public void onPlayerMessage(Game game, MessageDTO messageDTO) {
        Long userId = messageDTO.getPlayerCallback().getUserId();
        Player player = game.getPlayerByUserId(userId);
        player.setStatus(PlayerStatus.WAITING);
        if (player.getIndex().equals(game.getKing())) {
            if (Boolean.TRUE.equals(messageDTO.getPlayerOptionResponse().getResponse())) {
                game.setKingOverFourPublic(true);
                game.updatePlayerInfo();
                RepoUtil.messageRepo.broadcastRoom(game.getRoomId(),
                        String.format(GuideMessage.REVOLUTION_KING, player.getDisplayName()));
            }
        } else {
            if (Boolean.TRUE.equals(messageDTO.getPlayerOptionResponse().getResponse())) {
                player.setHasRevolution(true);
                game.updatePlayerInfo();
                RepoUtil.messageRepo.broadcastRoom(game.getRoomId(),
                        String.format(GuideMessage.REVOLUTION_OTHER, player.getDisplayName()));
            }
        }
        if (game.getPlayers().stream().noneMatch(p -> p.getStatus() == PlayerStatus.PLAYING)) {
            if (game.getPlayers().stream()
                    .filter(p -> !p.getIndex().equals(game.getKing()) && !p.getIndex().equals(game.getAgent()))
                    .noneMatch(p -> Boolean.FALSE.equals(p.getHasRevolution()))) {
                game.setHasRevolution(true);
            }
            nextStage(game);
        }
    }

    private void nextStage(Game game) {
        game.setGameStage(new RunningStage());
        game.getGameStage().run(game);
    }

    @Override
    public int getValue() {
        return GameStageEnum.REVOLUTION.getValue();
    }
}
