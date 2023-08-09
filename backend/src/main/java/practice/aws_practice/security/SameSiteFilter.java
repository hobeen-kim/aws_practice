package practice.aws_practice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class SameSiteFilter implements Filter {
//    @Value("${server.servlet.session.cookie.sameSite}")
    private String sameSitePolicy = "None";
    @Value("${cookie.domain}")
    private String domain;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("JSESSIONID")){

                ((HttpServletResponse) response).addHeader(HttpHeaders.SET_COOKIE, addSameSiteCookieAttribute(cookie).toString());
            }
        }

    }

    private ResponseCookie addSameSiteCookieAttribute(Cookie cookie) {

        return ResponseCookie.from(cookie.getName(), cookie.getValue())
                .domain(domain)
                .sameSite(sameSitePolicy)
                .path(cookie.getPath())
                .maxAge(60 * 60 * 24 * 14)
                .build();


    }
}
