/*
 *
 */
package io.github.pflima92.plyshare.gateway.common;

import org.jspare.core.annotation.Resource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Resource
@RequiredArgsConstructor
public class GatewayOptionsHolder {

	@Getter
	private final GatewayOptions options;
}