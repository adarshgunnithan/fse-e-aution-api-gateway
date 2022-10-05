package com.cts.eaution.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * @author aadi 
 * Auth filter
 */
public class JwtSecurityFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		final String authHeader = ((HttpServletRequest) request).getHeader("authorization");
		
		
		if ("OPTIONS".equals(((HttpServletRequest) request).getMethod())) {
			((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
			filterChain.doFilter(request, response);
		} else {
			if (authHeader == null || !authHeader.startsWith("Bearer")) {
				throw new ServletException("Missing or invalid Authorization header");
			}

			final String token = authHeader.substring(7);
			final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
			request.setAttribute("claims", claims);
			//set attribute wnt work for downsteam
			// authorization - temp setup
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
			
			@SuppressWarnings("unchecked")
			String userRole = (String) ((Map<String, Object>) claims.get("roles")).get("scopes");


			if ((path.contains("seller")) && (!userRole.equals("SELLER"))) {
				throw new ServletException("Unauthorized operation for the user");
			} else if ((path.contains("buyer")) && (!userRole.equals("BUYER"))) {
				throw new ServletException("Unauthorized operation for the user");
			} else if ((path.contains("search-services")) && (! ((userRole.equals("BUYER")) || (userRole.equals("SELLER"))))) {
					throw new ServletException("Unauthorized operation for the user");
			} else {
				filterChain.doFilter(request, response);

			}

		}
	}
}