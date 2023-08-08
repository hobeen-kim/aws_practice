package practice.aws_practice.security;

import lombok.Getter;
import lombok.Setter;
import practice.aws_practice.Member;

@Getter @Setter
public class MemberProfile {

    private String name;
    private String email;

    public Member toMember() {
        return Member.builder()
                .username(email.split("@")[0])
                .email(email)
                .nickname(email.split("@")[0])
                .password("oauthUser")
                .build();
    }
}
