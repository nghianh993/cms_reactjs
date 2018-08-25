package cms.backend.controllers;

import cms.backend.configs.security.JwtAuthenticationRequest;
import cms.backend.configs.security.JwtAuthenticationResponse;
import cms.backend.configs.security.JwtTokenUtil;
import cms.backend.models.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class AuthenticationRestController {

    @Value("${webconfig.jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping(value = "${webconfig.jwt.route.authentication.path}", produces = "application/json")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        // Reload password post-security so we can generate the token

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        List<String> action = new ArrayList<>();
        String permission = "";
        for(GrantedAuthority ga : userDetails.getAuthorities()) {
            action.add(ga.getAuthority());
            permission += "," + ga.getAuthority();
        }
        final String token = jwtTokenUtil.generateToken(userDetails, permission.substring(1));
        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token, action));
    }

    @GetMapping(value = "${webconfig.jwt.route.authentication.refresh}", produces = "application/json")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        List<String> permission = new ArrayList<>();
        for(GrantedAuthority ga : user.getAuthorities()) {
            permission.add(ga.getAuthority() );
        }

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);

            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken, permission));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    private void authenticate(String username, String password) throws AuthenticationException {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
