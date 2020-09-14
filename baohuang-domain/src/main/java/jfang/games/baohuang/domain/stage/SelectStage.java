package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.constant.GameStageEnum;
import jfang.games.baohuang.domain.constant.PlayerStatus;
import jfang.games.baohuang.domain.entity.Card;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.entity.Player;
import org.springframework.util.CollectionUtils;

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
        Player current = game.getPlayers().get(game.getCurrentPlayer());
        current.setStatus(PlayerStatus.PLAYING);
        game.updatePlayerInfo(game.getCurrentPlayer(), null);
    }

    @Override
    public void onPlayerMessage(Game game, MessageDTO messageDTO) {
        Long userId = messageDTO.getPlayerCallback().getUserId();
        Player player = game.getPlayerByUserId(userId);
        if (player.getPlayerCards().canBeKing()) {
            Card card = Card.of(messageDTO.getPlayerCallback().getSelectedCards().get(0));
            game.setAgentCard(card);
            game.setKing(player.getIndex());
            player.setIsKing(true);
            // find agent
            if (player.getPlayerCards().countSameCard(card) == 4) {
                game.setKingOverFour(true);
                player.getPlayerCards().getCard(card).setAgentCard(true);
            } else {
                for (Player p: game.getPlayers()) {
                    Card c = p.getPlayerCards().getCard(card);
                    if (c != null) {
                        game.setAgent(player.getIndex());
                        c.setAgentCard(true);
                        break;
                    }
                }
            }
            player.getPlayerCards().addCards(donationCards);
            nextStage(game);
        } else {
            if (!CollectionUtils.isEmpty(messageDTO.getPlayerCallback().getSelectedCards())) {
                Card card = Card.of(messageDTO.getPlayerCallback().getSelectedCards().get(0));
                donationCards.add(card);
                player.getPlayerCards().popCard(card);
            }
            player.setStatus(PlayerStatus.WAITING);
            int index = game.nextPlayer();
            game.setCurrentPlayer(index);
            game.getPlayers().get(index).setStatus(PlayerStatus.PLAYING);
            game.updatePlayerInfo();
        }
    }

    private void nextStage(Game game) {
        game.updatePlayerInfo();
        game.setGameStage(new RevolutionStage());
        game.getGameStage().run(game);
    }

    @Override
    public int getValue() {
        return GameStageEnum.SELECT_KING.getValue();
    }
}
