package jfang.games.baohuang.domain.user;

import jfang.games.baohuang.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Jun
 * @date 2020/4/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseEntity {

    private String username;
    private String password;
    private String portrait;
    private List<String> roles;
}
