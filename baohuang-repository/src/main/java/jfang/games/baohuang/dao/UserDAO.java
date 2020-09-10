package jfang.games.baohuang.dao;

import jfang.games.baohuang.po.UserPO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author jfang
 */
@Repository
public interface UserDAO extends PagingAndSortingRepository<UserPO, Long> {

    Optional<UserPO> findByUsername(String username);
}
