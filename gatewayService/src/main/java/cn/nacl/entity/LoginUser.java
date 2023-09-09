package cn.nacl.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
public class LoginUser implements UserDetails, Serializable {

    private String username;
    private String uid;
    private String password;
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public LoginUser (User user) {
        this.username = user.getUsername();
        this.uid = user.getUid().toString();
        this.password = user.getPassword();
        List<UserRole> userRoles = user.getRoles();
        for (UserRole userRole : userRoles) authorities.add(new SimpleGrantedAuthority(userRole.getRid().getRname()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getUid () {
        return uid;
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
