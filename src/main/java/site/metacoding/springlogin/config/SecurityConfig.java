package site.metacoding.springlogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import site.metacoding.springlogin.handler.LoginSuccessHandler;

@EnableWebSecurity // 해당 파일로 시큐리티가 활성화
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 패스워드 해시 알고리즘 사용
    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    // 인증 설정하는 메서드
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        http.csrf().disable(); // 이거 설정해줘야 postman으로 테스트 가능

        http.authorizeRequests()
                .antMatchers("/s/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login-form")
                .loginProcessingUrl("/login") // login 프로세스를 탄다.
                .successHandler(new LoginSuccessHandler());
    }
}
