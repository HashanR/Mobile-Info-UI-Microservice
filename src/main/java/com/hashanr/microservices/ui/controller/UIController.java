package com.hashanr.microservices.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import com.hashanr.microservice.commons.model.SubscriberData;
import com.hashanr.microservices.ui.configuration.AccessToken;


@Controller
@EnableOAuth2Sso
public class UIController extends WebSecurityConfigurerAdapter {


	
	
	@Autowired
	RestTemplate restTemplate;
	
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.logout()
		.logoutSuccessUrl("/")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	
	/*
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      if (auth != null){    
	         new SecurityContextLogoutHandler().logout(request, response, auth);
	      }
	    SecurityContextHolder.getContext().setAuthentication(null);
	    
	    return "home";
	    
	    
	}
	
	
*/


	@RequestMapping(value="/")
	public String loadUI()
	{
		return "home";
	}
	
	
	
	@RequestMapping(value="/secure")
	public String loadSecuredUI()
	{
		return "secure";
	}
	
	
	
	
	
	
	@RequestMapping(value = "/api")
	public String loadInfo(Model model, @RequestParam int msisdn) {
		HttpHeaders httpHeaders= new HttpHeaders();
		httpHeaders.add("Authorization", AccessToken.getAccessToken());
		
		
		String url = "http://localhost:8080/api/v1/subscriber/"+msisdn;
		try {
		HttpEntity<SubscriberData> subscriberHttpEntity= new HttpEntity<>(httpHeaders);
		
		ResponseEntity<SubscriberData> responseEntity=restTemplate.exchange(url,
				HttpMethod.GET,subscriberHttpEntity,SubscriberData.class);
		
		model.addAttribute("subscribers", responseEntity.getBody());
		}
	    catch (HttpStatusCodeException e) {
        ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
        model.addAttribute("error", responseEntity);
        }
		
		return "secure";
	}

}
