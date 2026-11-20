package com.example.registryauditmanagementsystem.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RateLimitService {

	private final int maxRequests;
	private final long windowNanos;
	private final ConcurrentHashMap<String, Window> windows = new ConcurrentHashMap<>();
	private final AtomicLong requestsSinceCleanup = new AtomicLong();

	public RateLimitService(
			@Value("${app.rate-limit.max-requests:60}") int maxRequests,
			@Value("${app.rate-limit.window-seconds:60}") long windowSeconds) {
		if (maxRequests < 1 || windowSeconds < 1) {
			throw new IllegalArgumentException("Rate limit and window duration must be positive");
		}
		this.maxRequests = maxRequests;
		this.windowNanos = windowSeconds * 1_000_000_000L;
	}

	public Decision tryAcquire(String key) {
		long now = System.nanoTime();
		AtomicReference<Decision> decision = new AtomicReference<>();

		windows.compute(key, (ignored, current) -> {
			if (current == null || now - current.startedAtNanos >= windowNanos) {
				current = new Window(now);
			}

			if (current.requestCount < maxRequests) {
				current.requestCount++;
				decision.set(new Decision(true, 0));
			}
			else {
				long remainingNanos = windowNanos - (now - current.startedAtNanos);
				long retryAfterSeconds = Math.max(1,
						(remainingNanos + 999_999_999L) / 1_000_000_000L);
				decision.set(new Decision(false, retryAfterSeconds));
			}
			return current;
		});

		if (requestsSinceCleanup.incrementAndGet() % 256 == 0) {
			windows.entrySet().removeIf(entry -> now - entry.getValue().startedAtNanos >= windowNanos);
		}
		return decision.get();
	}

	public record Decision(boolean allowed, long retryAfterSeconds) {
	}

	private static final class Window {

		private final long startedAtNanos;
		private int requestCount;

		private Window(long startedAtNanos) {
			this.startedAtNanos = startedAtNanos;
		}
	}
}
