package jfang.games.baohuang.domain.entity;

import jfang.games.baohuang.common.message.MessageDTO;
import jfang.games.baohuang.domain.BaseEntity;
import jfang.games.baohuang.domain.event.PlayerJoinEvent;
import jfang.games.baohuang.domain.event.PlayerLeftEvent;
import jfang.games.baohuang.domain.repo.RepoUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个游戏房间
 *
 * @author Jun
 * @date 2020/4/30
 */
public class Room extends BaseEntity {

    @Getter
    private final List<Player> playerList = new ArrayList<>();

    @Getter
    private Game game;

    public Room() {
    }

    public Room(Long id) {
        this.id = id;
    }

    public void addPlayer(Player player) {
        playerList.add(player);
        player.setIndex(playerList.size()-1);
        onPlayerAdded(player);
    }

    public void removePlayer(Player player) {
        playerList.removeIf(p -> p.getUserId().equals(player.getUserId()));
        onPlayerRemoved(player);
    }

    /**
     * 广播给其他人
     */
    public void onPlayerAdded(Player player) {
        PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(this.id, player);
        String message = playerJoinEvent.toChatMessage();
        MessageDTO systemMessage = playerJoinEvent.toJoinMessage();
        playerList.forEach(p -> {
            // 聊天消息
            RepoUtil.messageRepo.broadcastRoom(this.id, message);
            // 用户信息
            RepoUtil.messageRepo.systemMessage(p.getDisplayName(), systemMessage);
        });
        if (playerList.size() == 5) {
            onGameStarted();
        }
    }

    /**
     * 广播给其他人
     */
    public void onPlayerRemoved(Player player) {
        PlayerLeftEvent playerLeftEvent = new PlayerLeftEvent(this.id, player);
        String message = playerLeftEvent.toMessage();
        playerList.forEach(p -> {
            RepoUtil.messageRepo.broadcastRoom(this.id, message);
        });
    }

    /**
     * 广播给所有人
     */
    public void onGameStarted() {
        this.game = new Game(this.playerList);
        RepoUtil.gameRepo.createGame(game);
        this.game.getGameStage().run(game);
    }

    public void resetGame() {
        this.game = null;
    }
}
