package jfang.games.baohuang.domain.entity;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.BaseEntity;
import jfang.games.baohuang.domain.constant.GameStageEnum;
import jfang.games.baohuang.domain.constant.GuideMessage;
import jfang.games.baohuang.domain.repo.RepoUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 一个游戏房间
 *
 * @author Jun
 * @date 2020/4/30
 */
public class Room extends BaseEntity {

    /**
     * 座位号
     */
    private final Stack<Integer> indexStack = new Stack<>();

    @Getter
    private final List<Player> playerList = new ArrayList<>();

    @Getter
    private Game game;

    public Room() {
        for (int i = 0; i < 5; i++) {
            indexStack.push(i);
        }
    }

    public Room(Long id) {
        this();
        this.id = id;
    }

    public void addPlayer(Player player) {
        if (game == null) {
            this.game = new Game(this.id, this.playerList);
            this.game.getGameStage().run(game);
        }
        if (!this.game.hasPlayer(player.getUserId())) {
            playerList.add(player);
            player.setIndex(indexStack.pop());
            RepoUtil.messageRepo.broadcastRoom(this.id, String.format(GuideMessage.JOIN_ROOM, player.getDisplayName()));
        }
        this.game.updatePlayerInfo();
    }

    public void removePlayer(Long userId) {
        Player player = this.playerList.stream()
                .filter(p -> p.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
        if (player == null) {
            return;
        }
        playerList.removeIf(p -> p.getUserId().equals(userId));
        indexStack.push(player.getIndex());
        RepoUtil.messageRepo.broadcastRoom(this.id, String.format(GuideMessage.LEFT_ROOM, player.getDisplayName()));
        if (this.playerList.size() == 0) {
            game = null;
        } else {
            this.game.updatePlayerInfo();
        }
    }

    public void resetGame() {
        this.game = new Game(this.id, this.playerList);
        this.game.getGameStage().run(game);
    }
}
