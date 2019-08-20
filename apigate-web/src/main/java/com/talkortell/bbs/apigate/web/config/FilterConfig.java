package com.talkortell.bbs.apigate.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.talkortell.bbs.apigate.web.filter.PortalZuulFilter;

@Configuration
public class FilterConfig {
	@Bean
	public PortalZuulFilter portalZuulFilter() {
		return new PortalZuulFilter();
	}
}
