package jfang.games.baohuang.repo;

import jfang.games.baohuang.Adapter.UserConverter;
import jfang.games.baohuang.dao.UserDAO;
import jfang.games.baohuang.domain.repo.UserRepo;
import jfang.games.baohuang.domain.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jfang
 */
@Component
public class UserRepository implements UserRepo {

    @Resource
    private UserDAO userDAO;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public User findUserById(Long id) {
        return userDAO.findById(id).map(UserConverter::convertUserPO).orElse(null);
    }

    @Override
    public User findUserByUsername(String name) {
        return userDAO.findByUsername(name).map(UserConverter::convertUserPO).orElse(null);
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.save(UserConverter.convertUser(user));
    }
}
