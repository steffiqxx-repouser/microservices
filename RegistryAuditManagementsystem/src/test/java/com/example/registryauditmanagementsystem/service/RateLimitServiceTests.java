package com.example.registryauditmanagementsystem.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RateLimitServiceTests {

	@Test
	void limitsEachKeyIndependently() {
		RateLimitService rateLimitService = new RateLimitService(2, 60);

		assertTrue(rateLimitService.tryAcquire("client-a").allowed());
		assertTrue(rateLimitService.tryAcquire("client-a").allowed());
		RateLimitService.Decision blockedDecision = rateLimitService.tryAcquire("client-a");
		assertFalse(blockedDecision.allowed());
		assertTrue(blockedDecision.retryAfterSeconds() > 0);

		assertTrue(rateLimitService.tryAcquire("client-b").allowed());
	}
}
