package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.CardInfo;
import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.common.util.CheckUtil;
import jfang.games.baohuang.domain.constant.GameStageEnum;
import jfang.games.baohuang.domain.constant.GuideMessage;
import jfang.games.baohuang.domain.constant.PlayerStatus;
import jfang.games.baohuang.domain.constant.Rank;
import jfang.games.baohuang.domain.entity.Card;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.entity.Player;
import jfang.games.baohuang.domain.repo.RepoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 选主公
 *
 * @author jfang
 */
public class SelectStage implements GameStage {

    private final List<Card> donationCards = new ArrayList<>();

    @Override
    public void run(Game game) {
        Player current = game.getPlayerByIndex(game.getCurrentPlayer());
        current.setStatus(PlayerStatus.PLAYING);
        game.updatePlayerInfo();
        RepoUtil.messageRepo.broadcastRoom(game.getRoomId(),
                String.format(GuideMessage.SELECT_KING_START, current.getDisplayName()));
    }

    @Override
    public void onPlayerMessage(Game game, MessageDTO messageDTO) {
        Long userId = messageDTO.getPlayerCallback().getUserId();
        Player player = game.getPlayerByUserId(userId);
        if (player.getPlayerCards().canBeKing()) {
            CheckUtil.checkHand(messageDTO.getPlayerCallback().getSelectedCards().size() == 1, "应该选择一张牌");
            Card card = Card.of(messageDTO.getPlayerCallback().getSelectedCards().get(0));
            CheckUtil.checkHand(player.getPlayerCards().countSameCard(card) >= 3, "不能作为保镖牌");
            game.setAgentCard(card);
            game.setKing(player.getIndex());
            player.setIsKing(true);
            // find agent
            if (player.getPlayerCards().countSameCard(card) == 4) {
                game.setKingOverFour(true);
                player.getPlayerCards().getCard(card).setAgentCard(true);
            } else {
                Card sample = new Card(card.getSuit(), card.getRank());
                for (Player p: game.getPlayers()) {
                    // 不能是自己
                    if (!p.getIndex().equals(player.getIndex())) {
                        Card c = p.getPlayerCards().getCard(sample);
                        if (c != null) {
                            game.setAgent(p.getIndex());
                            c.setAgentCard(true);
                            p.getPlayerCards().sort();
                            break;
                        }
                    }
                }
            }
            player.getPlayerCards().addCards(donationCards);
            RepoUtil.messageRepo.broadcastRoom(game.getRoomId(),
                    String.format(GuideMessage.SELECT_KING_DONE, player.getDisplayName(), card.toDisplayString()));
            nextStage(game);
        } else {
            List<CardInfo> cardInfoList = messageDTO.getPlayerCallback().getSelectedCards();
            if (player.getPlayerCards().countByRank(Rank.TWO) == 0 && player.getPlayerCards().countJoker(null) == 0) {
                CheckUtil.checkHand(cardInfoList.size() == 0, "应该不用选择");
                RepoUtil.messageRepo.broadcastRoom(game.getRoomId(),
                        String.format(GuideMessage.SELECT_KING_PASS_FREE, player.getDisplayName()));
            } else {
                CheckUtil.checkHand(cardInfoList.size() == 1, "应该选择一张牌");
                Card card = Card.of(cardInfoList.get(0));
                if (donationCards.size() == 0) {
                    CheckUtil.checkHand(card.isRedJoker(), "应该是大王");
                } else if (player.getPlayerCards().countByRank(Rank.TWO) > 0) {
                    CheckUtil.checkHand(card.getRank() == Rank.TWO, "应该是2");
                } else if (player.getPlayerCards().countJoker(false) > 0) {
                    CheckUtil.checkHand(card.isBlackJoker(), "应该是小王");
                } else if (player.getPlayerCards().countJoker(true) > 0) {
                    CheckUtil.checkHand(card.isRedJoker(), "应该是大王");
                }
                donationCards.add(card);
                player.getPlayerCards().popCard(card);
                RepoUtil.messageRepo.broadcastRoom(game.getRoomId(),
                        String.format(GuideMessage.SELECT_KING_PASS, player.getDisplayName(), card.toDisplayString()));
            }
            player.setStatus(PlayerStatus.WAITING);
            int index = game.nextPlayer();
            game.setCurrentPlayer(index);
            game.getPlayerByIndex(index).setStatus(PlayerStatus.PLAYING);
            game.updatePlayerInfo();
        }
    }

    @Override
    public void nextStage(Game game) {
        game.updatePlayerInfo();
        game.setGameStage(new RevolutionStage());
        game.getGameStage().run(game);
    }

    @Override
    public int getValue() {
        return GameStageEnum.SELECT_KING.getValue();
    }
}
