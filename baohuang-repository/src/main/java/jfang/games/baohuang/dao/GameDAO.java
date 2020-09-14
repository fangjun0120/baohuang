package jfang.games.baohuang.dao;

import jfang.games.baohuang.po.GamePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jfang
 */
@Repository
public interface GameDAO extends JpaRepository<GamePO, Long> {
}
