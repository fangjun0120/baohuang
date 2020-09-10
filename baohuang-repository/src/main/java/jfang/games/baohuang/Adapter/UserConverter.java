package jfang.games.baohuang.Adapter;

import jfang.games.baohuang.domain.user.User;
import jfang.games.baohuang.po.UserPO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jfang
 */
public class UserConverter {

    public static User convertUserPO(UserPO userPO) {
        User user = new User();
        user.setId(userPO.getId());
        user.setUsername(userPO.getUsername());
        user.setPassword(userPO.getPassword());
        user.setPortrait(userPO.getPortrait());
        List<String> roles = new ArrayList<>();
        if (userPO.getRoles() != null) {
            String[] tokens = userPO.getRoles().split(",");
            Collections.addAll(roles, tokens);
        }
        user.setRoles(roles);
        return user;
    }

    public static UserPO convertUser(User user) {
        UserPO userPO = new UserPO();
        userPO.setId(user.getId());
        userPO.setUsername(user.getUsername());
        userPO.setPassword(user.getPassword());
        userPO.setPortrait(user.getPortrait());
        userPO.setRoles(String.join(",", user.getRoles()));
        return userPO;
    }
}
