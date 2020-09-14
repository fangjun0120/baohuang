package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.constant.GameStageEnum;
import jfang.games.baohuang.domain.constant.PlayerStatus;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.entity.Hand;
import jfang.games.baohuang.domain.entity.Player;
import jfang.games.baohuang.domain.entity.PlayerAction;

/**
 * @author jfang
 */
public class RunningStage implements GameStage {

    @Override
    public void run(Game game) {
        Player current = game.getPlayers().get(game.getCurrentPlayer());
        current.setStatus(PlayerStatus.PLAYING);
        game.updatePlayerInfo();
    }

    @Override
    public GameControl onPlayerMessage(Game game, MessageDTO messageDTO) {
        Long userId = messageDTO.getPlayerCallback().getUserId();
        Player player = game.getPlayerByUserId(userId);
        if (!game.getCurrentPlayer().equals(player.getIndex())) {
            throw new IllegalStateException();
        }
        if (player.getPlayerAction() == null) {
            player.setPlayerAction(new PlayerAction());
        }
        // 是否过牌
        if (Boolean.TRUE.equals(messageDTO.getPlayerCallback().getPass())) {
            player.getPlayerAction().setPass(true);
            player.setStatus(PlayerStatus.WAITING);
            int nextPlayer = game.nextPlayer();
            // TODO: 借东风的情况
            // 是否一圈没人要
            if (game.getCurrentLeader() == nextPlayer) {
                game.setCurrentPlayer(game.getCurrentLeader());
                game.setCurrentLeader(null);
            } else {
                game.getPlayers().get(nextPlayer).setStatus(PlayerStatus.PLAYING);
                game.setCurrentPlayer(nextPlayer);
            }
        } else {
            Hand hand = Hand.of(messageDTO.getPlayerCallback().getSelectedCards());
            // 不是发牌的时候，比较上一手牌
            if (game.getCurrentLeader() != null) {
                Player currentLeader = game.getPlayers().get(game.getCurrentLeader());
                Hand lastHand = currentLeader.getPlayerAction().getLastHand();
                if (!hand.isDominateThan(lastHand)) {
                    throw new IllegalArgumentException();
                } else if (currentLeader.isDead()) {
                    // 上一个玩家是否被闷了
                    currentLeader.setStatus(PlayerStatus.DEAD);
                    currentLeader.setRank(game.getReverseRankIndex());
                    game.addCompletedPlayer(currentLeader.getIndex(), true);
                }
            }
            player.getPlayerAction().setPass(false);
            player.getPlayerAction().setLastHand(hand);
            // 是否是最后一手牌
            if (player.isCompleted()) {
                player.setStatus(PlayerStatus.COMPLETED);
                game.addCompletedPlayer(player.getIndex(), false);
                if (game.isCompleted()) {
                    game.updatePlayerInfo();
                    game.setGameStage(new EndStage());
                    game.getGameStage().run(game);
                }
            } else {
                player.setStatus(PlayerStatus.WAITING);
            }
            game.setCurrentLeader(player.getIndex());
            int nextPlayer = game.nextPlayer();
            game.getPlayers().get(nextPlayer).setStatus(PlayerStatus.PLAYING);
            game.setCurrentPlayer(nextPlayer);
        }
        game.updatePlayerInfo();
        return new GameControl();
    }

    @Override
    public int getValue() {
        return GameStageEnum.RUNNING.getValue();
    }
}
