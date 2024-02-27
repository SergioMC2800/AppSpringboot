package org.jrotero.security.filters;

import java.io.IOException;

import org.jrotero.security.jwt.JwtUtils;
import org.jrotero.service.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter{

	
	private JwtUtils jwtUtils;
	private UserDetailsServiceImpl userDetailsService;
	
	public JwtAuthorizationFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
	}



	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
									@NonNull HttpServletResponse response,
									@NonNull FilterChain filterChain) throws ServletException, IOException{
		String tokenHeader = request.getHeader("Authorization");
		
		if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
			String token = tokenHeader.substring(7);
			
			if(jwtUtils.isTokenValid(token)) {
				String username = jwtUtils.getUsernameToken(token);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}
