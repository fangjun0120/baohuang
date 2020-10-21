package jfang.games.baohuang.repo;

import jfang.games.baohuang.Adapter.GameConverter;
import jfang.games.baohuang.Adapter.HandConverter;
import jfang.games.baohuang.dao.GameDAO;
import jfang.games.baohuang.dao.HandDAO;
import jfang.games.baohuang.domain.entity.Game;
import jfang.games.baohuang.domain.entity.Hand;
import jfang.games.baohuang.domain.repo.GameRepo;
import jfang.games.baohuang.po.GamePO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * @author jfang
 */
@Component
public class GameRepository implements GameRepo {

    @Resource
    private GameDAO gameDAO;

    @Resource
    private HandDAO handDAO;

    @Override
    public void createGame(Game game) {
        LocalDateTime current = LocalDateTime.now();
        if (game.getId() == null) {
            game.setCreatedTime(current);
        }
        game.setUpdatedTime(current);
        GamePO gamePO = GameConverter.convertGameToPO(game);
        gamePO = gameDAO.save(gamePO);
        if (game.getId() == null) {
            game.setId(gamePO.getId());
        }
    }

    @Override
    public void saveGame(Game game) {
        GamePO gamePO = GameConverter.convertGameToPO(game);
        gameDAO.save(gamePO);
    }

    @Override
    public void saveInitHand(Game game) {
        handDAO.saveAll(game.getPlayers().stream()
                .map(p -> HandConverter.convertCardsToHand(p.getPlayerCards().getCardList(), p.getUserId(), game.getId()))
                .collect(Collectors.toList())
        );
    }

    @Override
    public void saveHand(Hand hand, Long userId, Long gameId) {
        handDAO.save(HandConverter.convertHandToPO(hand, userId, gameId));
    }
}
