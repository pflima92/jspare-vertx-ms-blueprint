/*
 *
 */
package io.github.pflima92.plyshare.common.circuitbreaker;

import org.jspare.core.annotation.Resource;

import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Resource
@RequiredArgsConstructor
public class CircuitBreakerHolder {

	public static CircuitBreakerHolder create(CircuitBreaker circuitBreaker) {

		return new CircuitBreakerHolder(circuitBreaker);
	}

	@Getter
	private final CircuitBreaker circuitBreaker;

	public <T> Future<T> execute(Handler<Future<T>> handler) {

		return circuitBreaker.execute(handler);
	}
}