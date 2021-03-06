package jfang.games.baohuang.domain.entity;

import jfang.games.baohuang.common.exception.PlayerNotFoundException;
import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.common.message.PlayerOptions;
import jfang.games.baohuang.domain.BaseEntity;
import jfang.games.baohuang.domain.constant.PlayerStatus;
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

    private Long roomId;

    /**
     * 所有玩家，引用自Room.playerList
     * 顺序没有意义，位置要看Player.index
     */
    private List<Player> players = new ArrayList<>(5);

    private GameStage gameStage = new InitStage();

    /**
     * 排名序列，从0开始
     */
    private int rankIndex = 0;
    private int reverseRankIndex = 4;

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
     * 当前领先的玩家
     */
    private Integer currentLeader;

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

    /**
     * 标记结束
     */
    private boolean completed;

    public Game() {
    }

    public Game(Long roomId, List<Player> players) {
        this.roomId = roomId;
        this.players = players;
        RepoUtil.gameRepo.createGame(this);
    }

    public boolean hasPlayer(Long userId) {
        return this.players.stream().anyMatch(p -> p.getUserId().equals(userId));
    }

    public Player getPlayerByUserId(Long userId) {
        return this.players.stream()
                .filter(p -> p.getUserId().equals(userId))
                .findFirst().orElseThrow(RuntimeException::new);
    }

    public Player getPlayerByIndex(int index) {
        return this.players.stream()
                .filter(p -> p.getIndex() == index)
                .findFirst().orElseThrow(() -> new PlayerNotFoundException(index));
    }

    /**
     * @return 非主公、非保镖
     */
    public List<Player> getNormalPlayers() {
        return this.getPlayers().stream()
                .filter(p -> !p.getIndex().equals(this.king) && !p.getIndex().equals(this.agent))
                .collect(Collectors.toList());
    }

    /**
     * @return 所有还在的玩家
     */
    public List<Player> getActivePlayers() {
        return this.getPlayers().stream()
                .filter(p -> p.getStatus() != PlayerStatus.COMPLETED && p.getStatus() != PlayerStatus.DEAD)
                .collect(Collectors.toList());
    }

    public int nextPlayer() {
        int index = this.currentPlayer + 1;
        while (index != this.currentPlayer) {
            if (index > 4) {
                index = 0;
            }
            Player player = getPlayerByIndex(index);
            if (player.getStatus() == PlayerStatus.WAITING) {
                this.currentPlayer = index;
                break;
            }
            index++;
        }
        return index;
    }

    public void addCompletedPlayer(int index, boolean isDead) {
        if (isDead) {
            getPlayerByIndex(index).setRank(this.reverseRankIndex--);
        } else {
            getPlayerByIndex(index).setRank(this.rankIndex++);
        }
    }

    public void clearLastRound() {
        for (Player player: this.players) {
            if (player.getPlayerAction() != null) {
                player.getPlayerAction().setPass(null);
                player.getPlayerAction().setLastHand(null);
            }
        }
    }

    /**
     * @return 完成的人发的7,是否没人接
     */
    public boolean checkNoFollowers() {
        List<Player> activePlayers = getActivePlayers();
        return activePlayers.stream()
                .allMatch(p -> p.getPlayerAction() != null && Boolean.TRUE.equals(p.getPlayerAction().getPass()));
    }

    public boolean checkCompleted() {
        if (this.isKingOverFourPublic) {
            return getPlayerByIndex(this.king).getRank() != null || this.rankIndex > 0;
        }
        if (this.isKingOverFour) {
            return getPlayerByIndex(this.king).getRank() != null || this.rankIndex > 1;
        }
        if (getPlayerByIndex(this.king).getRank() != null && getPlayerByIndex(this.agent).getRank() != null) {
            return true;
        }
        return getNormalPlayers().stream().allMatch(p -> p.getRank() != null);
    }

    public void updatePlayerInfo() {
        for (Player player : this.players) {
            updatePlayerInfo(player.getIndex(), null);
        }
    }

    public void updatePlayerInfo(int index, PlayerOptions playerOptions) {
        Player player = getPlayerByIndex(index);
        MessageDTO messageDTO = new MessageDTO("server");
        messageDTO.setGameId(this.id);
        messageDTO.setStage(this.gameStage.getValue());
        messageDTO.setCurrentPlayer(this.currentPlayer);
        messageDTO.setIsOneOverFour(this.isKingOverFourPublic);
        messageDTO.setIsRevolution(this.hasRevolution);
        messageDTO.setPlayerInfo(players.stream()
                .map(p -> p.toPlayerInfo(p.getIndex() == index))
                .collect(Collectors.toList()));
        messageDTO.setPlayerOptions(playerOptions);
        RepoUtil.messageRepo.systemMessage(player.getDisplayName(), messageDTO);
    }

    public void buildRank() {
        Player playerKing = getPlayerByIndex(this.king);
        if (this.isKingOverFour) {
            int base = 3;
            if (this.hasRevolution) {
                base *= 2;
            }
            if (this.isKingOverFourPublic) {
                base *= 2;
                if (playerKing.getRank() == 0) {
                    setPlayerScores(-1 * base, true);
                } else {
                    setPlayerScores(base, true);
                }
            } else {
                if (playerKing.getRank() == 0) {
                    setPlayerScores(-1 * base, true);
                } else if (playerKing.getRank() == 1) {
                    setPlayerScores(0, true);
                } else {
                    setPlayerScores(base, true);
                }
            }
        } else {
            int base = playerKing.getRank() + getPlayerByIndex(this.agent).getRank() - 4;
            if (this.hasRevolution) {
                base *= 2;
            }
            setPlayerScores(base, false);
        }
    }

    private void setPlayerScores(int base, boolean oneOverFour) {
        for (Player player: this.getPlayers()) {
            if (oneOverFour) {
                if (player.getIndex().equals(this.king)) {
                    player.setScore(-4 * base);
                } else {
                    player.setScore(base);
                }
            } else {
                if (player.getIndex().equals(this.king)) {
                    player.setScore(-2 * base);
                } else if (player.getIndex().equals(this.agent)) {
                    player.setScore(-1 * base);
                } else {
                    player.setScore(base);
                }
            }
        }
    }
}
