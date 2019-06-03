package br.com.wda.OpenBeerProject.Configuration;

import br.com.wda.OpenBeerProject.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Darlan Silva
 * @author Wesley Moura
 * @author Alison Souza
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private LoginService loginService;
    
    public static PasswordEncoder plainPasswordEncoder(){
        return new PasswordEncoder() {
            
            @Override
            public String encode(CharSequence cs) {
                return cs.toString();
            }

            @Override
            public boolean matches(CharSequence cs, String hashSenha) {
                return hashSenha != null && hashSenha.equals(cs.toString());
            }
        };
    }
    
    public static PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return bcryptPasswordEncoder();
    }
    
    @Override
    protected void configure (HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/css/**", "/img/**", "/js/**").permitAll()
                .antMatchers("/OpenBeer/endereco").authenticated()
            .and()
                .formLogin().permitAll()
                    .loginPage("/OpenBeer/login/HomeLogin")
                    .usernameParameter("email-login")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/OpenBeer/Home").permitAll();
//            .and()
//                .logout()
//                    .logoutUrl("/logout")
//                    .logoutSuccessUrl("/login?logout")
//                    .invalidateHttpSession(true).deleteCookies("JSESSIONID")
//            .and()
//                .exceptionHandling().accessDeniedPage("/erro/403");
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(loginService)
        .passwordEncoder(new BCryptPasswordEncoder());
    }
}
