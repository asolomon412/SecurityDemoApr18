package com.securityex.SecurityDemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.securityex.SecurityDemo.dao.UsersRepository;
import com.securityex.SecurityDemo.service.CustomUserDetailsService;

// add another annotation for the roles 
@EnableGlobalMethodSecurity(prePostEnabled = true) // this is added for the @PreAuthorize annotatin in the
													// HomeController
@EnableWebSecurity // enables Spring Security for us
@EnableJpaRepositories(basePackageClasses = UsersRepository.class) // will inject the classes for jpa
@Configuration // lets spring know that this is a configuration file
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService usersDS;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usersDS).passwordEncoder(getPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// disable cross-site request forgery
		http.csrf().disable();

		// authorize the secure users
		http.authorizeRequests().antMatchers("/**").authenticated().anyRequest().permitAll().and().formLogin() // .loginPage("/loginpage")
																												// --
																												// this
																												// is
																												// for a
																												// custom
																												// login
																												// page
				.permitAll();

	}

	// Should use an encryption library like BCrypt
	private PasswordEncoder getPasswordEncoder() {
		return new PasswordEncoder() {

			@Override
			public String encode(CharSequence rawPassword) {
				// just returning the value of the string 
				return rawPassword.toString();
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				
				return true;
			}

		};
	}

}
