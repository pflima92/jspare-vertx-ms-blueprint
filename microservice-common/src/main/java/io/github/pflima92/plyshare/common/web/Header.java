/*
 *
 */
package io.github.pflima92.plyshare.common.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@AllArgsConstructor
public enum Header {

	TID("tid"), GATEWAY_ORIGIN("Gateway-Origin"), AUTHORIZATION("Authorization");

	@Getter
	private final String value;
}
