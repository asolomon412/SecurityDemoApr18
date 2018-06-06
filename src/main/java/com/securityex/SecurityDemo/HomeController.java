package com.securityex.SecurityDemo;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.securityex.SecurityDemo.dao.UsersRepository;
import com.securityex.SecurityDemo.entity.Users;

@Controller
public class HomeController {
	
	@Autowired
	UsersRepository uP;

	@RequestMapping("/")
	public ModelAndView index() {
		return new ModelAndView("index");
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/secured")
	public ModelAndView secured() {
		return new ModelAndView("welcome");
	}
	
	@RequestMapping("/nonadmin")
	public ModelAndView nonAdmin() {
		return new ModelAndView("welcome");
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request,response, auth);
		}
		
		return "redirect:/login?logout";
	}
	
	// for Jill - based off of our troubleshooting after class
	@RequestMapping("/testing")
	public ModelAndView test() {
		// this will show the memory locations because no overriden toString() exists in the Users class
		List<Users> u1 = uP.findAll();
		
		// this is demonstrating how to use the findByName() to check for the correct username (name) in the table
		Optional<Users> u2 = uP.findByName("test");
		// showing how I created a new method to get the lastname based off of the new method added to the UsersRepository
		// and named based off of my variable name 
		Optional<Users> u3 = uP.findByLastName("Solomon");
		// doing a test print for the email to be returned from the getEmail() based off of the query above for u3
		System.out.println(u3.get().getEmail());
		// checking that the value is not null from the method call above
		// then checking that the password matches based off the credentials in the DB
		if (u2.isPresent() && u2.get().getPassword().equals("feathers")) {
			
			return new ModelAndView("testing", "testing",u2.get().getPassword());
			
		}
		
		
		return new ModelAndView("testing","testing", u1);
	}

}
