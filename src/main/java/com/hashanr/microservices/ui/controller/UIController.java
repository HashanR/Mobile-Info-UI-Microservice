package com.hashanr.microservices.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
		.authenticated();
	}



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
