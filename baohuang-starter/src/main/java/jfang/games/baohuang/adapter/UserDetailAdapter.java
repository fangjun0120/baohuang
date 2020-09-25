package jfang.games.baohuang.adapter;

import jfang.games.baohuang.domain.user.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author jfang
 */
@Getter
@ToString
public class UserDetailAdapter implements UserDetails {

    private User user;

    public UserDetailAdapter() {
    }

    public UserDetailAdapter(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] array = new String[user.getRoles().size()];
        for (int i = 0; i < user.getRoles().size(); i++) {
            array[i] = user.getRoles().get(i);
        }
        return AuthorityUtils.createAuthorityList(array);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
