package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.constant.GameStageEnum;
import jfang.games.baohuang.domain.constant.GuideMessage;
import jfang.games.baohuang.domain.constant.PlayerStatus;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.entity.Player;
import jfang.games.baohuang.domain.repo.RepoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

/**
 * 准备过程
 *
 * @author jfang
 */
@Slf4j
public class InitStage implements GameStage {

    @Override
    public void run(Game game) {
        log.info("game created {}", game.getId());
    }

    @Override
    public void onPlayerMessage(Game game, MessageDTO messageDTO) {
        Long userId = messageDTO.getPlayerCallback().getUserId();
        Player player = game.getPlayerByUserId(userId);
        if (Boolean.TRUE.equals(messageDTO.getPlayerCallback().getReady())) {
            player.setStatus(PlayerStatus.READY);
            if (game.getPlayers().stream().noneMatch(p -> p.getStatus() != PlayerStatus.READY)) {
                game.setGameStage(new ShuffleStage());
                game.getGameStage().run(game);
            }
        } else {
            player.setStatus(PlayerStatus.INIT);
        }
        String names = game.getPlayers().stream()
                .filter(p -> p.getStatus() == PlayerStatus.READY)
                .map(Player::getDisplayName)
                .collect(Collectors.joining(","));
        if (!StringUtils.isEmpty(names)) {
            RepoUtil.messageRepo.broadcastRoom(game.getRoomId(), String.format(GuideMessage.PLAYER_READY, names));
        }
        game.updatePlayerInfo();
    }

    @Override
    public int getValue() {
        return GameStageEnum.INIT.getValue();
    }
}
