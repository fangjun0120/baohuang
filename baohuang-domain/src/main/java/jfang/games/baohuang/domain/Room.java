package jfang.games.baohuang.domain;

import jfang.games.baohuang.domain.card.Game;
import jfang.games.baohuang.domain.user.User;

import java.util.List;
import java.util.Set;

/**
 * 一个游戏房间
 *
 * @author Jun
 * @date 2020/4/30
 */
public class Room {

    private List<User> userList;
    private Set<Long> readyUserSet;

    private Game game;


    public void addUser(User user) {

    }

    public void removeUser(User user) {

    }


    public void onUserMessageReceived() {

    }

    /**
     * 广播给其他人
     */
    public void onUserAdded() {

    }

    /**
     * 广播给其他人
     */
    public void onUserRemoved() {

    }

    /**
     * 广播给所有人
     */
    public void onGameStarted() {

    }


}
