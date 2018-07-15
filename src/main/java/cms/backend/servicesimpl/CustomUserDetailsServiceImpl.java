package cms.backend.servicesimpl;

import cms.backend.entities.Permission;
import cms.backend.entities.Roles;
import cms.backend.entities.Users;
import cms.backend.models.JwtUser;
import cms.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Users user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("Không đúng tên đăng nhập hoặc mật khẩu!");
            }
            return new JwtUser(user.getId(), username, user.getPassword(), user.getEmail(), user.getImages(), !user.isIslock(), user.getLastpasswordresetdate(), getAuthorities(user.getRoles()));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final Collection<? extends GrantedAuthority> getAuthorities(final Collection<Roles> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private final List<String> getPrivileges(final Collection<Roles> roles) {
        final List<String> permission = new ArrayList<String>();
        final List<Permission> collection = new ArrayList<Permission>();
        for (final Roles role : roles) {
            collection.addAll(role.getPermissions());
        }
        for (final Permission item : collection) {
            permission.add(item.getCode());
        }
        return permission;
    }

    private final List<GrantedAuthority> getGrantedAuthorities(final List<String> permission) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        List<String> lstpermission = new ArrayList<>();
        for (final String privilege : permission) {
            lstpermission.add(privilege);
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
