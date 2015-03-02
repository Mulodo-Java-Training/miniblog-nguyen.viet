package miniblog.util;

import java.util.ArrayList;
import java.util.List;

import miniblog.service.interfaces.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Component;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider{
 
    // Create User Service
    @Autowired
    @Qualifier("UserServiceImpl")
    private IUserService userService;
    
    
    @Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        if (name != null)
        {
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new GrantedAuthorityImpl("ROLE_USER"));
            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
            return auth;
        } else {
            return null;
        }
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
