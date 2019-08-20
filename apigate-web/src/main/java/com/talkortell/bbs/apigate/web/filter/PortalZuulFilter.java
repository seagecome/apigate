package com.talkortell.bbs.apigate.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PortalZuulFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Object run() throws ZuulException {
		log.info("===PortalZuulFilter===start===");
		return null;
	}
}
