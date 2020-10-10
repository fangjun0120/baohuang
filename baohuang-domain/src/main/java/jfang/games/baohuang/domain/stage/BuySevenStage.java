package jfang.games.baohuang.domain.stage;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.common.util.CheckUtil;
import jfang.games.baohuang.domain.constant.GameStageEnum;
import jfang.games.baohuang.domain.constant.GuideMessage;
import jfang.games.baohuang.domain.constant.PlayerStatus;
import jfang.games.baohuang.domain.constant.Rank;
import jfang.games.baohuang.domain.entity.Card;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.entity.Hand;
import jfang.games.baohuang.domain.entity.Player;
import jfang.games.baohuang.domain.repo.RepoUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 买 7
 *
 * @author jfang
 */
@Slf4j
public class BuySevenStage implements GameStage {

    private int remainCount = 0;

    private final Stack<Card> sevenStack = new Stack<>();
    private final Stack<Card> otherStack = new Stack<>();
    private final List<Integer> sevenBuyers = new ArrayList<>();
    private final Map<Integer, Integer> sevenSellers = new HashMap<>();

    @Override
    public void run(Game game) {
        RepoUtil.messageRepo.broadcastRoom(game.getRoomId(), GuideMessage.BUY_SEVEN_START);
        for (Player player: game.getPlayers()) {
            int count = player.getPlayerCards().countSeven();
            if (count == 1) {
                player.setStatus(PlayerStatus.WAITING);
                RepoUtil.messageRepo.sendMessage(player.getDisplayName(), GuideMessage.BUY_SEVEN_WAIT);
            } else if (count > 1) {
                remainCount ++;
                player.setStatus(PlayerStatus.PLAYING);
                RepoUtil.messageRepo.sendMessage(player.getDisplayName(), GuideMessage.BUY_SEVEN_SELL);
            } else {
                remainCount ++;
                player.setStatus(PlayerStatus.PLAYING);
                RepoUtil.messageRepo.sendMessage(player.getDisplayName(), GuideMessage.BUY_SEVEN_BUY);
            }
        }
        game.updatePlayerInfo();
        if (remainCount == 0) {
            nextStage(game);
        }
    }

    @Override
    public void onPlayerMessage(Game game, MessageDTO messageDTO) {
        Long userId = messageDTO.getPlayerCallback().getUserId();
        Player player = game.getPlayerByUserId(userId);
        Hand selected = Hand.of(messageDTO.getPlayerCallback().getSelectedCards());

        // 校验
        int count = player.getPlayerCards().countSeven();
        if (count > 1) {
            CheckUtil.checkHand(selected.getCards().stream().allMatch(c -> c.getRank() == Rank.SEVEN), "存在不是7的牌");
            CheckUtil.checkHand(selected.getCards().size() + 1 == count, "7张数错误");
        } else if (count == 0) {
            if (player.getPlayerCards().countByRank(Rank.TWO) > 0) {
                CheckUtil.checkHand(selected.getCards().size() == 1, "应该选择一张牌");
                CheckUtil.checkHand(selected.getCards().get(0).getRank() == Rank.TWO, "买7应该用2");
            } else if (player.getPlayerCards().countJoker(false) > 0) {
                CheckUtil.checkHand(selected.getCards().size() == 1, "应该选择一张牌");
                CheckUtil.checkHand(selected.getCards().get(0).isBlackJoker(), "买7应该用小王");
            } else if (player.getPlayerCards().countJoker(true) > 0) {
                CheckUtil.checkHand(selected.getCards().size() == 1, "应该选择一张牌");
                CheckUtil.checkHand(selected.getCards().get(0).isRedJoker(), "买7应该用大王");
            } else {
                CheckUtil.checkHand(selected.getCards().size() == 0, "买7应该买不起");
            }
        } else {
            log.warn("no need to buy/sell 7");
            return;
        }

        player.getPlayerCards().popCards(selected.getCards());
        if (selected.getCards().size() > 0 &&
                selected.getCards().get(0).getRank() == Rank.SEVEN) {
            selected.getCards().forEach(sevenStack::push);
            sevenSellers.put(player.getIndex(), selected.getCards().size());
        } else {
            if (selected.getCards().size() > 0) {
                Card card = selected.getCards().get(0);
                otherStack.push(card);
                RepoUtil.messageRepo.broadcastRoom(game.getRoomId(),
                        String.format(GuideMessage.BUY_SEVEN_CONSUME, player.getDisplayName(), card.toDisplayString()));
            } else {
                RepoUtil.messageRepo.broadcastRoom(game.getRoomId(),
                        String.format(GuideMessage.BUY_SEVEN_FREE, player.getDisplayName()));
            }
            sevenBuyers.add(player.getIndex());
        }
        remainCount--;
        player.setStatus(PlayerStatus.WAITING);
        game.updatePlayerInfo(player.getIndex(), null);

        // 补齐
        if (remainCount == 0) {
            sevenSellers.keySet().forEach(i -> {
                Player p = game.getPlayerByIndex(i);
                // 有人买不起的时候就拿不到牌
                for (int j = 0; j < sevenSellers.get(i); j++) {
                    if (!otherStack.empty()) {
                        Card card = otherStack.pop();
                        p.getPlayerCards().addCards(Collections.singletonList(card));
                        RepoUtil.messageRepo.broadcastRoom(game.getRoomId(),
                                String.format(GuideMessage.BUY_SEVEN_OFFER, p.getDisplayName(), card.toDisplayString()));
                    }
                }
            });
            sevenBuyers.forEach(i -> {
                Player p = game.getPlayerByIndex(i);
                p.getPlayerCards().addCards(Collections.singletonList(sevenStack.pop()));
            });
            nextStage(game);
        }
    }

    private void nextStage(Game game) {
        game.setGameStage(new SelectStage());
        game.getGameStage().run(game);
    }

    @Override
    public int getValue() {
        return GameStageEnum.BUY_SEVEN.getValue();
    }
}
