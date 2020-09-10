package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.CardInfo;
import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.card.Card;
import jfang.games.baohuang.domain.card.GameStageEnum;
import jfang.games.baohuang.domain.card.PlayerStatus;
import jfang.games.baohuang.domain.entity.Game;

/**
 * 选主公
 *
 * @author jfang
 */
public class SelectStage implements GameStage {

    @Override
    public void run(Game game) {
        // wait for player action
    }

    @Override
    public void onPlayerMessage(Game game, MessageDTO messageDTO) {
        if (messageDTO.getPlayerAction().getPass()) {
            game.setPlayerStatus(game.getCurrentPlayer(), PlayerStatus.WAITING);
            game.nextPlayer();
            game.setPlayerStatus(game.getCurrentPlayer(), PlayerStatus.PLAYING);
            game.updatePlayerInfo(game.getCurrentPlayer());
        } else {
            // TODO: 校验
            CardInfo cardInfo = messageDTO.getPlayerAction().getAgentCard();
            game.setAgentCard(Card.of(cardInfo));
            game.setKing(game.getCurrentPlayer());
            // TODO: look for agent

        }
    }

    @Override
    public int getValue() {
        return GameStageEnum.SELECT.getValue();
    }
}
