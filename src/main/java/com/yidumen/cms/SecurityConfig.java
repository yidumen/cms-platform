package com.yidumen.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author 蔡迪旻
 *         2015年12月14日
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService authenticationService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/index.html","/css/**","/script/**","/oss/**","/api/wechat/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()

                .formLogin()
                .loginPage("/index.html")
                .loginProcessingUrl("/login.do")
                .defaultSuccessUrl("/platform", true)
                .permitAll()
                .and()
                .rememberMe().rememberMeParameter("remember").userDetailsService(authenticationService).key("ydmcms").tokenValiditySeconds(2592000)
                .and()
                .logout()
                .logoutUrl("/logout.do")
                .logoutSuccessUrl("/index.html")
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService);
    }
}
