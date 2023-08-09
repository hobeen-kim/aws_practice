package practice.aws_practice.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<SameSiteFilter> loggingFilter() {
        FilterRegistrationBean<SameSiteFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(sameSiteFilter());
        registrationBean.addUrlPatterns("/*"); // 필터를 적용할 경로 설정. 모든 경로에 적용하려면 "/*" 사용

        return registrationBean;
    }

    @Bean
    public SameSiteFilter sameSiteFilter() {
        return new SameSiteFilter();
    }
}
