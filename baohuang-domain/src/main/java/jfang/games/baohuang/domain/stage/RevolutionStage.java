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
            if (player.getIndex().equals(game.getKing())) {
                if (game.isKingOverFour()) {
                    player.setStatus(PlayerStatus.PLAYING);
                    RepoUtil.messageRepo.broadcastRoom(game.getRoomId(), GuideMessage.REVOLUTION);
                    game.updatePlayerInfo(player.getIndex(), PlayerOptions.of(PlayerOptionEnum.ONE_OVER_FOUR, null));
                } else {
                    player.setStatus(PlayerStatus.WAITING);
                    RepoUtil.messageRepo.sendMessage(player.getDisplayName(), GuideMessage.REVOLUTION_WAIT);
                    game.updatePlayerInfo(player.getIndex(), null);
                }
            } else if (player.getIndex().equals(game.getAgent())) {
                player.setStatus(PlayerStatus.WAITING);
                RepoUtil.messageRepo.sendMessage(player.getDisplayName(), GuideMessage.REVOLUTION_WAIT);
                game.updatePlayerInfo(player.getIndex(), null);
            } else {
                player.setStatus(PlayerStatus.PLAYING);
                RepoUtil.messageRepo.sendMessage(player.getDisplayName(), GuideMessage.REVOLUTION);
                game.updatePlayerInfo(player.getIndex(), PlayerOptions.of(PlayerOptionEnum.REVOLUTION, null));
            }
        }
    }

    @Override
    public void onPlayerMessage(Game game, MessageDTO messageDTO) {
        Long userId = messageDTO.getPlayerCallback().getUserId();
        Player player = game.getPlayerByUserId(userId);
        player.setStatus(PlayerStatus.WAITING);
        if (player.getIndex().equals(game.getKing())) {
            if (Boolean.TRUE.equals(messageDTO.getPlayerOptionResponse().getResponse())) {
                game.setKingOverFourPublic(true);
                RepoUtil.messageRepo.broadcastRoom(game.getRoomId(),
                        String.format(GuideMessage.REVOLUTION_KING, player.getDisplayName()));
            }
        } else {
            if (Boolean.TRUE.equals(messageDTO.getPlayerOptionResponse().getResponse())) {
                player.setHasRevolution(true);
                RepoUtil.messageRepo.broadcastRoom(game.getRoomId(),
                        String.format(GuideMessage.REVOLUTION_OTHER, player.getDisplayName()));
            }
        }
        game.updatePlayerInfo(player.getIndex(), null);
        if (game.getPlayers().stream().noneMatch(p -> p.getStatus() == PlayerStatus.PLAYING)) {
            if (game.getPlayers().stream()
                    .filter(p -> !p.getIndex().equals(game.getKing()) && !p.getIndex().equals(game.getAgent()))
                    .allMatch(p -> Boolean.TRUE.equals(p.getHasRevolution()))) {
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
