package jfang.games.baohuang.domain.entity;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.common.message.PlayerInfo;
import jfang.games.baohuang.domain.BaseEntity;
import jfang.games.baohuang.domain.card.Card;
import jfang.games.baohuang.domain.card.GameStageEnum;
import jfang.games.baohuang.domain.card.PlayerStatus;
import jfang.games.baohuang.domain.repo.RepoUtil;
import jfang.games.baohuang.domain.stage.GameStage;
import jfang.games.baohuang.domain.stage.InitStage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jun
 * @date 2020/4/29
 */
@Getter
@Setter
public class Game extends BaseEntity {

    private static final long serialVersionUID = 3142574777412945362L;

    /**
     * 所有玩家
     */
    private List<Player> players = new ArrayList<>(5);

    private GameStage gameStage = new InitStage();

    /**
     * 主公
     */
    private Integer king;

    /**
     * 保镖
     */
    private Integer agent;

    /**
     * 当前轮次玩家
     */
    private Integer currentPlayer;

    /**
     * 保镖牌
     */
    private Card agentCard;

    /**
     * 是否起义
     */
    private boolean hasRevolution;

    /**
     * 是否主公1打4
     */
    private boolean isKingOverFour;

    /**
     * 是否主公明1打4
     */
    private boolean isKingOverFourPublic;

    public Game() {
    }

    public Game(List<Player> players) {
        this.players = players;
    }

    public Player getPlayerByUserId(Long userId) {
        return this.players.stream()
                .filter(p -> p.getUserId().equals(userId))
                .findFirst().orElseThrow(RuntimeException::new);
    }

    public Integer nextPlayer() {
        int index = this.currentPlayer + 1;
        while (index != this.currentPlayer) {
            if (index > 4) {
                index = 0;
            }
            Player player = players.get(index);
            if (player.getStatus() == PlayerStatus.WAITING) {
                this.currentPlayer = index;
                break;
            }
            index++;
        }
        return index;
    }

    public void updatePlayerInfo() {
        for (int i = 0; i < this.players.size(); i++) {
            updatePlayerInfo(i);
        }
    }

    public void updatePlayerInfo(int index) {
        Player player = this.getPlayers().get(index);
        MessageDTO messageDTO = new MessageDTO("server");
        messageDTO.setStage(this.gameStage.getValue());
        messageDTO.setCurrentPlayer(this.currentPlayer);
        messageDTO.setPlayerInfo(players.stream()
                .map(p -> p.toPlayerInfo(p.getIndex() == index))
                .collect(Collectors.toList()));
        RepoUtil.messageRepo.systemMessage(player.getDisplayName(), messageDTO);
    }
}
