package jfang.games.baohuang.dao;

import jfang.games.baohuang.po.HandPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jfang
 */
@Repository
public interface HandDAO extends JpaRepository<HandPO, Long> {
}
