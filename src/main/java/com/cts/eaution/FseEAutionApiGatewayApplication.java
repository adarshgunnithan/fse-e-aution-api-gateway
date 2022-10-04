package com.cts.eaution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.cts.eaution.filter.JwtSecurityFilter;

/**
 * @author aadi
 *JWT token filter
 */
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class FseEAutionApiGatewayApplication {
	
	public static final String SELLER_URL_PATTERN = "/e-auction/api/v1/seller/*";
	
	public static final String BUYER_URL_PATTERN = "/e-auction/api/v1/buyer/*";
	
	public static final String COMMON_SEARCH_URL_PATTERN = "/e-auction/api/v1/search-services/*";

	public static void main(String[] args) {
		SpringApplication.run(FseEAutionApiGatewayApplication.class, args);
	}

	
	/**
	 * Adding url patterns to security
	 * @return registrationBean
	 */
	@Bean
	public FilterRegistrationBean<JwtSecurityFilter> jwtFilter() {
		final FilterRegistrationBean<JwtSecurityFilter> registrationBean = new FilterRegistrationBean<JwtSecurityFilter>();
		registrationBean.setFilter(new JwtSecurityFilter());
		registrationBean.addUrlPatterns(SELLER_URL_PATTERN,BUYER_URL_PATTERN,COMMON_SEARCH_URL_PATTERN);
		return registrationBean;
	}
}
