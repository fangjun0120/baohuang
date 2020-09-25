package jfang.games.baohuang.repo;

import jfang.games.baohuang.domain.repo.UserRepo;
import jfang.games.baohuang.domain.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author jfang
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Resource
    private UserRepo userRepository;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("test5");
        user.setPassword("123456");
        user.setPortrait("/images/portrait0.jpg");
        user.setRoles(Collections.singletonList("ROLE_user"));
        userRepository.save(user);
    }
}
