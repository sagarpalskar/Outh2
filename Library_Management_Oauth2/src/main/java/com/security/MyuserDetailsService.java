package com.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dao.UserRepository;
import com.model.User;
@Service
@ComponentScan(basePackages = {"com.dao.UserRepository"})
public class MyuserDetailsService implements UserDetailsService 
{
	static Logger log = LogManager.getLogger( MyuserDetailsService.class.getName());
	
	@Autowired(required=true)
	private UserRepository repo;
	@Override
	public UserDetails loadUserByUsername(String uname) throws UsernameNotFoundException 
	{
		User user = repo.findByuname(uname);
		if (user==null)
		{
			log.error("username not fond");
			throw new UsernameNotFoundException("404");
		}
		return new UserPrincipal(user);
	}
	

}
