package jfang.games.baohuang.service;

import jfang.games.baohuang.adapter.UserDetailAdapter;
import jfang.games.baohuang.domain.repo.UserRepo;
import jfang.games.baohuang.domain.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jfang
 */
@Component
public class UserLoginService implements UserDetailsService {

    @Resource
    private UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return new UserDetailAdapter(user);
    }
}
