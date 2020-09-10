package jfang.games.baohuang.domain.entity;

import jfang.games.baohuang.common.message.PlayerInfo;
import jfang.games.baohuang.domain.card.Card;
import jfang.games.baohuang.domain.card.PlayerStatus;
import jfang.games.baohuang.domain.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jun
 * @date 2020/4/29
 */
@Getter
public class Player {

    private final Long userId;
    private final String displayName;
    private final String portrait;

    private final List<Card> cards = new ArrayList<>();
    private PlayerStatus status = PlayerStatus.INIT;
    private Integer index;

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Player(User user) {
        this.userId = user.getId();
        this.displayName = user.getUsername();
        this.portrait = user.getPortrait();
    }

    public void dealCards(List<Card> cards) {
        getCards().clear();
        getCards().addAll(cards);
    }

    public PlayerInfo toPlayerInfo(boolean includeCard) {
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setUserId(this.userId);
        playerInfo.setUsername(this.displayName);
        playerInfo.setPortrait(this.portrait);
        playerInfo.setIndex(this.index);
        playerInfo.setState(this.status.getValue());
        if (includeCard) {
            playerInfo.setCardList(cards.stream().map(Card::toCardInfo).collect(Collectors.toList()));
        }
        return playerInfo;
    }
}
