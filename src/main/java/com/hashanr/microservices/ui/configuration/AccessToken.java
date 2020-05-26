package com.hashanr.microservices.ui.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;

public class AccessToken {
	
	public static String getAccessToken() {
		OAuth2AuthenticationDetails authenticationDetails=(OAuth2AuthenticationDetails) 
				SecurityContextHolder.getContext().getAuthentication().getDetails();
		return authenticationDetails.getTokenType().concat(" ").concat(authenticationDetails.getTokenValue());
	}
	
	


}
