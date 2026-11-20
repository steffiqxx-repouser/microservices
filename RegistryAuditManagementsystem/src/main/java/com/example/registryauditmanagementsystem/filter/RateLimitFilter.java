package com.example.registryauditmanagementsystem.filter;

import com.example.registryauditmanagementsystem.service.RateLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

	private final RateLimitService rateLimitService;

	public RateLimitFilter(RateLimitService rateLimitService) {
		this.rateLimitService = rateLimitService;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getRequestURI().startsWith(request.getContextPath() + "/api/");
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		RateLimitService.Decision decision = rateLimitService.tryAcquire(request.getRemoteAddr());
		if (!decision.allowed()) {
			response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
			response.setHeader("Retry-After", Long.toString(decision.retryAfterSeconds()));
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("{\"error\":\"Rate limit exceeded\"}");
			return;
		}
		filterChain.doFilter(request, response);
	}
}
