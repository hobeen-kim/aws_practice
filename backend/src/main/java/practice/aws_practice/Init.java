package practice.aws_practice;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class Init {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        @Transactional
        public void init(){
            Member member = new Member();

            member.setUsername("test");
            member.setPassword(passwordEncoder.encode("1234"));
            member.setEmail("test@test.com");
            member.setNickname("testNick");

            em.persist(member);
        }

    }
}
