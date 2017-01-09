/*
 *
 */
package org.jspare.spareco.gateway.utils;

import java.time.Duration;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Stopwatch {

	public static Stopwatch create() {
		return new Stopwatch();
	}

	@Getter
	@Setter
	private LocalDateTime start;

	@Getter
	@Setter
	private LocalDateTime stop;

	public Stopwatch print() {
		if (log.isDebugEnabled()) {
			log.debug("Started at: {}", start.toString());
			log.debug("Stoped at: {}", stop.toString());
			log.debug("Time elapssed: {}ms", Duration.between(start, stop).toMillis());
		}
		return this;
	}

	public Stopwatch release() {
		start = null;
		stop = null;
		return this;
	}

	public Stopwatch start() {

		start = LocalDateTime.now();
		return this;
	}

	public Stopwatch start(String message, Object... args) {
		if (log.isDebugEnabled()) {
			log.debug(message, args);
		}
		return start();
	}

	public Stopwatch stop() {

		stop = LocalDateTime.now();
		return this;
	}

	public Stopwatch stop(String message, Object... args) {
		if (log.isDebugEnabled()) {
			log.debug(message, args);
		}
		return stop();
	}
}