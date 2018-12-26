package com.tmj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ApplicationAuthenticationSuccessHandler applicationAuthenticationSuccessHandler;
    private final ApplicationAuthenticationLogoutHandler applicationAuthenticationLogoutHandler;
    private final SessionTimeoutHandler sessionTimeoutHandler;

    @Autowired
    public SpringSecurityConfig(ApplicationAuthenticationSuccessHandler applicationAuthenticationSuccessHandler,
                                ApplicationAuthenticationLogoutHandler applicationAuthenticationLogoutHandler,
                                SessionTimeoutHandler sessionTimeoutHandler) {
        this.applicationAuthenticationSuccessHandler = applicationAuthenticationSuccessHandler;
        this.applicationAuthenticationLogoutHandler = applicationAuthenticationLogoutHandler;
        this.sessionTimeoutHandler = sessionTimeoutHandler;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/monitoring*").hasRole("config@userManagement_VIEW")
                .antMatchers("/login").permitAll()
                .antMatchers("/theme/**").permitAll()
                .antMatchers("faviconCD.ico").permitAll()
                .anyRequest().authenticated()
                .and().requestCache().requestCache(new NullRequestCache()).and()
                .formLogin().loginPage("/login")
                .usernameParameter("username").passwordParameter("password").successHandler(applicationAuthenticationSuccessHandler)
                .and().rememberMe().rememberMeParameter("remember-me").rememberMeCookieName("my-remember-me").tokenValiditySeconds(86400)
                .and()
                .logout().invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .logoutSuccessHandler(applicationAuthenticationLogoutHandler)
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied")
                .and()
                .csrf()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation().migrateSession()
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
//                .expiredUrl("/login?Session Expired")
//                .and()
                .invalidSessionUrl("/login?Invalid Session")
                .invalidSessionStrategy(sessionTimeoutHandler);

        http.headers().cacheControl().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        Context ctx = new InitialContext();
        DataSource dataSource = (DataSource) ctx.lookup("java:/TMS_DS");
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT username,password, enabled FROM tms.users WHERE upper(username)=upper(?)")
                .passwordEncoder(new ShaPasswordEncoder(256))
                .authoritiesByUsernameQuery("SELECT\n" +
                        "                        u.USERNAME AS username,\n" +
                        "                        a.AUTHORITY AS authority\n" +
                        "                    FROM\n" +
                        "                        tms.USERS u,\n" +
                        "                        tms.USER_GROUPS ug,\n" +
                        "                        tms.GROUP_AUTHORITIES ga,\n" +
                        "                        tms.AUTHORITIES a\n" +
                        "                    WHERE\n" +
                        "                        upper(u.USERNAME) = upper(?) AND\n" +
                        "                        u.USER_SEQ = ug.USER_SEQ AND\n" +
                        "                        ug.GROUP_SEQ = ga.GROUP_SEQ AND\n" +
                        "                        ga.AUTHORITY_SEQ = a.AUTHORITY_SEQ AND\n" +
                        "                        ug.STATUS=1");

    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
