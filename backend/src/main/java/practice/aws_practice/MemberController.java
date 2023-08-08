package practice.aws_practice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/userInfo")
    public String oauthLoginInfo(Authentication authentication){

        if(authentication.getPrincipal() instanceof OAuth2User){
            //oAuth2User.toString() 예시 : Name: [2346930276], Granted Authorities: [[USER]], User Attributes: [{id=2346930276, provider=kakao, name=김준우, email=bababoll@naver.com}]
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            //attributes.toString() 예시 : {id=2346930276, provider=kakao, name=김준우, email=bababoll@naver.com}
            Map<String, Object> attributes = oAuth2User.getAttributes();
            return attributes.toString();
        }

        return "Session login";
    }

    @GetMapping("/auth")
    public String index(){
        return "로그인된 사용자만 접근 가능한 페이지";
    }


    @GetMapping("/csrf")
    public ResponseEntity<String> getOrCreateCsrfToken(HttpSession session, HttpServletRequest request) {
        final DefaultCsrfToken csrfToken = (DefaultCsrfToken) session.getAttribute(
                "CSRF_TOKEN");

        assert(csrfToken == request.getAttribute(csrfToken.getParameterName()));

        return ResponseEntity.ok().header(csrfToken.getHeaderName(), csrfToken.getToken()).build();
    }

}
