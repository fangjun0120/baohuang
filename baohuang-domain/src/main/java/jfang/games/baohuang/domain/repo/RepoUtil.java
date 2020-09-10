package jfang.games.baohuang.domain.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jfang
 */
@Component
public class RepoUtil {

    public static MessageRepo messageRepo;
    public static GameRepo gameRepo;

    @Autowired
    public void setMessageRepo(MessageRepo messageRepo) {
        RepoUtil.messageRepo = messageRepo;
    }

    @Autowired
    public void setGameRepo(GameRepo gameRepo) {
        RepoUtil.gameRepo = gameRepo;
    }
}
