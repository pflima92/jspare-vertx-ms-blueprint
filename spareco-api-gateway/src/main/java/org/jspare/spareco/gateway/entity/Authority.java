/*
 *
 */
package org.jspare.spareco.gateway.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
public class Authority extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
}