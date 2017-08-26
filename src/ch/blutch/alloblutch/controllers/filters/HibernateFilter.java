package ch.blutch.alloblutch.controllers.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import ch.blutch.alloblutch.utils.HibernateUtils;

public class HibernateFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HibernateUtils.openSession();
		chain.doFilter(request, response);
		HibernateUtils.closeSession();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
