package jfang.games.baohuang.domain.repo;

import jfang.games.baohuang.domain.user.User;

/**
 * @author jfang
 */
public interface UserRepo {

    User findUserById(Long id);

    User findUserByUsername(String name);

    void save(User user);
}
