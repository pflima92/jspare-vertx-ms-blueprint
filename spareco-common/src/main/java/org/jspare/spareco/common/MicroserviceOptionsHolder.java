/*
 *
 */
package org.jspare.spareco.common;

import org.jspare.core.annotation.Resource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Resource
@RequiredArgsConstructor
public class MicroserviceOptionsHolder {

	@Getter
	private final MicroserviceOptions options;
}
