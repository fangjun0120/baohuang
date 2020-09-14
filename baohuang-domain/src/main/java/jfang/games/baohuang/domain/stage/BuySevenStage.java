package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.entity.Card;
import jfang.games.baohuang.domain.constant.GameStageEnum;
import jfang.games.baohuang.domain.constant.PlayerStatus;
import jfang.games.baohuang.domain.constant.Rank;
import jfang.games.baohuang.domain.entity.Hand;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * 买 7
 *
 * @author jfang
 */
public class BuySevenStage implements GameStage {

    private int remainCount = 0;

    private final Stack<Card> sevenStack = new Stack<>();
    private final Stack<Card> otherStack = new Stack<>();
    private final List<Integer> sevenConsumer = new ArrayList<>();
    private final List<Integer> sevenProducer = new ArrayList<>();

    @Override
    public void run(Game game) {
        for (Player player: game.getPlayers()) {
            if (player.getPlayerCards().countSeven() == 1) {
                player.setStatus(PlayerStatus.WAITING);
            } else {
                remainCount ++;
                player.setStatus(PlayerStatus.PLAYING);
            }
        }
        if (remainCount == 0) {
            nextStage(game);
        }
    }

    @Override
    public GameControl onPlayerMessage(Game game, MessageDTO messageDTO) {
        Long userId = messageDTO.getPlayerCallback().getUserId();
        Player player = game.getPlayerByUserId(userId);
        Hand selected = Hand.of(messageDTO.getPlayerCallback().getSelectedCards());
        player.getPlayerCards().popCards(selected.getCards());
        // 买不起
        if (selected.getCards().size() > 0 &&
                selected.getCards().get(0).getRank() == Rank.SEVEN) {
            selected.getCards().forEach(sevenStack::push);
            sevenProducer.add(player.getIndex());
        } else {
            selected.getCards().forEach(otherStack::push);
            sevenConsumer.add(player.getIndex());
        }
        remainCount--;

        // 补齐
        if (remainCount == 0) {
            sevenProducer.forEach(i -> {
                Player p = game.getPlayers().get(i);
                if (!otherStack.empty()) {
                    p.getPlayerCards().addCards(Collections.singletonList(otherStack.pop()));
                }
                p.setStatus(PlayerStatus.WAITING);
            });
            sevenConsumer.forEach(i -> {
                Player p = game.getPlayers().get(i);
                p.getPlayerCards().addCards(Collections.singletonList(sevenStack.pop()));
                p.setStatus(PlayerStatus.WAITING);
            });
            nextStage(game);
        }
        return new GameControl();
    }

    private void nextStage(Game game) {
        game.updatePlayerInfo();
        game.setGameStage(new SelectStage());
        game.getGameStage().run(game);
    }

    @Override
    public int getValue() {
        return GameStageEnum.BUY_SEVEN.getValue();
    }
}
