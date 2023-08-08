package practice.aws_practice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final SessionAuthenticationStrategy sessionStrategy;

    @Value("${url.frontend}")
    private String frontendUrl;

    public CustomAuthenticationSuccessHandler(SessionAuthenticationStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        this.sessionStrategy.onAuthentication(authentication, request, response);

        String url = makeRedirectUrl();

        System.out.println("success handler url : " + url);

        getRedirectStrategy().sendRedirect(request, response, url);
    }

    private String makeRedirectUrl() {
        return UriComponentsBuilder.fromUriString(frontendUrl)
                .build().toUriString();
    }
}
