package br.com.wda.OpenBeerProject.Configuration;

import br.com.wda.OpenBeerProject.Infra.JWTUtil;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


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

    @Autowired
    PasswordEncoder passwordEncoder;

    public static final String[] PERMIT_ALL = {
        "/Carrinho/**",
        "/cerveja/**",
        "/Cliente/**",
        "/Endereco/**",
        "/Home/**",
        "/Login/**",
        "/css/**",
        "/img/**",
        "/js/**"

    };

    public static final String[] ADMIN_ONLY = {
        "/BackOffice/**"
    };

    public static PasswordEncoder plainPasswordEncoder() {
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
    public PasswordEncoder passwordEncoder() {
        return bcryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/OpenBeer/Carrinho/**",
                            "/OpenBeer/cerveja/**",
                            "/OpenBeer/Cliente/**",
                            "/OpenBeer/Endereco/**",
                            "/OpenBeer/Home/**",
                            "/OpenBeer/Login/**",
                            "*/css/**",
                            "*/img/**",
                            "*/js/**").permitAll()
                .antMatchers("/OpenBeer/BackOffice/**").hasRole("ADMIN")
                .antMatchers("/OpenBeer/Pagamento/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/OpenBeer/Login/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/OpenBeer/Login/Sucess").permitAll()
                .failureUrl("/OpenBeer/Home")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/OpenBeer/Home")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling().accessDeniedPage("/erro/403");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
