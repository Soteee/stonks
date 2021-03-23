package es.ucm.fdi.stonks;
   
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").permitAll() // "/**" = todo
                .anyRequest().authenticated().and().formLogin().permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // allows access to h2 console iff running under debug mode
        web.ignoring().antMatchers("/h2/**");
    }

}
