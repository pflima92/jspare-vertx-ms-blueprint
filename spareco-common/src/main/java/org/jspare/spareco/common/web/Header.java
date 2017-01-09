/*
 *
 */
package org.jspare.spareco.common.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@AllArgsConstructor
public enum Header {

	TID("tid"), GATEWAY_ORIGIN("Gateway-Origin"), AUTHORIZATION("Authorization"), INTERPROCESS_API_TOKEN("api-token");

	@Getter
	private final String value;
}
