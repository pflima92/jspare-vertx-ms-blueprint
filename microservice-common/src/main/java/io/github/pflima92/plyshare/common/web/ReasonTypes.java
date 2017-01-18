/*
 * 
 */
package io.github.pflima92.plyshare.common.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ReasonTypes : This class provides various types of Reason that reflects the
 * result of service operation.
 */
@AllArgsConstructor
public enum ReasonTypes {

	INFO(0),
	
	WARNING(4),
	
	ERROR(8);

	@Getter
	private final int code;
}