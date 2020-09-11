package jfang.games.baohuang.domain.entity;

import jfang.games.baohuang.common.message.PlayerInfo;
import jfang.games.baohuang.domain.card.PlayerStatus;
import jfang.games.baohuang.domain.user.User;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jun
 * @date 2020/4/29
 */
@Getter
public class Player {

    private final Long userId;
    private final String displayName;
    private final String portrait;

    @Setter
    private PlayerCards playerCards;
    @Setter
    private PlayerStatus status = PlayerStatus.INIT;
    @Setter
    private Integer index;

    public Player(User user) {
        this.userId = user.getId();
        this.displayName = user.getUsername();
        this.portrait = user.getPortrait();
    }

    public PlayerInfo toPlayerInfo(boolean includeCard) {
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setUserId(this.userId);
        playerInfo.setUsername(this.displayName);
        playerInfo.setPortrait(this.portrait);
        playerInfo.setIndex(this.index);
        playerInfo.setState(this.status.getValue());
        if (includeCard) {
            playerInfo.setCardList(playerCards.toCardInfo());
        }
        return playerInfo;
    }
}
