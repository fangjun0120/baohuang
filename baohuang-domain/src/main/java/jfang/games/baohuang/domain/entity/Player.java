package jfang.games.baohuang.domain.entity;

import jfang.games.baohuang.common.message.PlayerInfo;
import jfang.games.baohuang.domain.constant.PlayerStatus;
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
    private PlayerAction playerAction;
    @Setter
    private PlayerStatus status = PlayerStatus.INIT;
    @Setter
    private Integer index;
    @Setter
    private Boolean isKing;
    @Setter
    private Boolean hasRevolution;
    /**
     * 排名，从0开始
     */
    @Setter
    private Integer rank;
    @Setter
    private Integer score;

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
        playerInfo.setIsKing(this.isKing);
        playerInfo.setHasRevolution(this.hasRevolution);
        if (includeCard) {
            playerInfo.setCardList(playerCards.toCardInfo());
        }
        if (playerAction != null) {
            playerInfo.setPass(playerAction.getPass());
            if (playerAction.getLastHand() != null) {
                playerInfo.setLastHand(playerAction.getLastHand().toCardInfoList());
            }
        }
        return playerInfo;
    }

    public boolean isDead() {
        return this.getPlayerCards() != null
                && this.getPlayerCards().count() == 1
                && this.playerCards.countSeven() == 1;
    }

    public boolean isCompleted() {
        return this.getPlayerCards() != null && this.getPlayerCards().count() == 0;
    }
}
