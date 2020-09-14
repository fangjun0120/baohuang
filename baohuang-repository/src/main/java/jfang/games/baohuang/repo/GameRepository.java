package jfang.games.baohuang.repo;

import jfang.games.baohuang.Adapter.GameConverter;
import jfang.games.baohuang.dao.GameDAO;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.repo.GameRepo;
import jfang.games.baohuang.po.GamePO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jfang
 */
@Component
public class GameRepository implements GameRepo {

    @Resource
    private GameDAO gameDAO;

    @Override
    public void createGame(Game game) {
        GamePO gamePO = GameConverter.convertGameToPO(game);
        gamePO = gameDAO.save(gamePO);
        if (game.getId() == null) {
            game.setId(gamePO.getId());
        }
    }

    @Override
    public void saveGame(Game game) {

    }
}
