/*
 *
 */
package io.github.pflima92.plyshare.common.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Parameter {

	DELIVERY("alias");

	@Getter
	private final String value;
}
