package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.constant.GameStageEnum;
import jfang.games.baohuang.domain.constant.GuideMessage;
import jfang.games.baohuang.domain.constant.PlayerStatus;
import jfang.games.baohuang.domain.constant.Rank;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.entity.Hand;
import jfang.games.baohuang.domain.entity.Player;
import jfang.games.baohuang.domain.entity.PlayerAction;
import jfang.games.baohuang.domain.repo.RepoUtil;

/**
 * @author jfang
 */
public class RunningStage implements GameStage {

    @Override
    public void run(Game game) {
        String info = game.isHasRevolution() ? GuideMessage.RUNNING_REVOLUTION : GuideMessage.RUNNING_NO_REVOLUTION;
        if (game.isKingOverFourPublic()) {
            info += " " + GuideMessage.RUNNING_ONE_OVER_FOUR;
        }
        RepoUtil.messageRepo.broadcastRoom(game.getRoomId(), String.format(GuideMessage.RUNNING, info));
        Player current = game.getPlayerByIndex(game.getCurrentPlayer());
        current.setStatus(PlayerStatus.PLAYING);
        game.updatePlayerInfo();
        RepoUtil.gameRepo.saveInitHand(game);
    }

    @Override
    public void onPlayerMessage(Game game, MessageDTO messageDTO) {
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
            if (game.getCurrentLeader() == null) {
                throw new IllegalArgumentException("你先出，请出牌");
            }
            player.getPlayerAction().setPass(true);
            player.setStatus(PlayerStatus.WAITING);
            int nextPlayer = game.nextPlayer();
            // 是否一圈没人要，或者没人要已经走了的人的7
            if (game.getCurrentLeader() == nextPlayer || game.checkNoFollowers()) {
                game.setCurrentLeader(null);
                game.clearLastRound();
            }
            game.setCurrentPlayer(nextPlayer);
            game.getPlayerByIndex(nextPlayer).setStatus(PlayerStatus.PLAYING);
        } else {
            Hand hand = Hand.of(messageDTO.getPlayerCallback().getSelectedCards());
            player.getPlayerCards().popCards(hand.getCards());
            // 不是发牌的时候，比较上一手牌
            if (game.getCurrentLeader() != null) {
                Player currentLeader = game.getPlayerByIndex(game.getCurrentLeader());
                Hand lastHand = currentLeader.getPlayerAction().getLastHand();
                if (!hand.isDominateThan(lastHand)) {
                    throw new IllegalArgumentException("牌型不够大");
                } else if (currentLeader.isDead()) {
                    // 上一个玩家是否被闷了
                    currentLeader.setStatus(PlayerStatus.DEAD);
                    currentLeader.setRank(game.getReverseRankIndex());
                    game.addCompletedPlayer(currentLeader.getIndex(), true);
                    RepoUtil.messageRepo.broadcastRoom(game.getRoomId(),
                            String.format(GuideMessage.RUNNING_DEAD, currentLeader.getDisplayName()));
                }
            } else {
                if (hand.getCards().size() == 1 && hand.getRank() == Rank.SEVEN.getValue()) {
                    if (player.getPlayerCards().count() > 1) {
                        throw new IllegalArgumentException("7必须最后出");
                    }
                }
            }
            player.getPlayerAction().setPass(false);
            player.getPlayerAction().setLastHand(hand);
            // 是否是最后一手牌
            if (player.isCompleted()) {
                player.setStatus(PlayerStatus.COMPLETED);
                game.addCompletedPlayer(player.getIndex(), false);
                RepoUtil.messageRepo.broadcastRoom(game.getRoomId(),
                        String.format(GuideMessage.RUNNING_OVER, player.getDisplayName(), player.getRank() + 1));
                if (game.checkCompleted()) {
                    game.updatePlayerInfo();
                    nextStage(game);
                }
            } else {
                player.setStatus(PlayerStatus.WAITING);
            }
            game.setCurrentLeader(player.getIndex());
            int nextPlayer = game.nextPlayer();
            game.getPlayerByIndex(nextPlayer).setStatus(PlayerStatus.PLAYING);
            game.setCurrentPlayer(nextPlayer);
            RepoUtil.gameRepo.saveHand(hand, player.getUserId(), game.getId());
        }
        game.updatePlayerInfo();
    }

    @Override
    public void nextStage(Game game) {
        game.setGameStage(new EndStage());
        game.getGameStage().run(game);
    }

    @Override
    public int getValue() {
        return GameStageEnum.RUNNING.getValue();
    }
}
