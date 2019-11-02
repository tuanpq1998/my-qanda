/**
 * @author admin
 * @date 26-10-2019
 */

package com.tuanpq.myaskfm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.tuanpq.myaskfm.service.UserService;
import com.tuanpq.myaskfm.utility.Constant;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomLoginSuccessHandler customLoginSuccessHandler; 
	
	@Autowired
	private UserService userService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		provider.setPasswordEncoder(passwordEncoder());
		auth.authenticationProvider(provider);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/index").permitAll()
		.antMatchers("/wall**").permitAll()
		.antMatchers("/user/questions/{questionId}/**")
			.access("@guard.checkUserAndQuestion(authentication, #questionId)")
		.antMatchers("/user/**").authenticated()
		.and().formLogin().loginPage("/"+Constant.LOGIN_URI)
		.loginProcessingUrl("/"+Constant.LOGIN_URI)
		.successHandler(customLoginSuccessHandler).permitAll()
		.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/"+Constant.LOGIN_URI+"?logout").permitAll()
		.and().exceptionHandling().accessDeniedPage("/error");
	}

}
