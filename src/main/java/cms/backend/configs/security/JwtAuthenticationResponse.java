package cms.backend.configs.security;

import java.io.Serializable;
import java.util.List;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;
    private final List<String> action;

    public JwtAuthenticationResponse(String token, List<String> action) {
        this.token = token;
        this.action= action;
    }

    public String getToken() {
        return this.token;
    }

    public List<String> getAction() {
        return action;
    }
}
