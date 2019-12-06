package com.reservo.reservo.Config;
import com.reservo.reservo.Services.MongoUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableConfigurationProperties
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    @Autowired
    MongoUserDetailService userDetailsService;

    @Override
    protected void configure (HttpSecurity http) throws Exception{

        http
          /*  .requestMatchers()
                .antMatchers("/reserve/**", "/user/")
                .and()
            .authorizeRequests()
                .antMatchers("/**").hasRole("USER")
            .and()
            .cors()
            .and()
            .exceptionHandling()
            .and()
            .csrf().and()*/
          .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user/login", "/user/signup")
                .permitAll()
                .and()
          .cors().and()
          .csrf().disable()
        .authorizeRequests().anyRequest().authenticated()
        .and().httpBasic()
        .and().sessionManagement().disable();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(userDetailsService);
    }

}

