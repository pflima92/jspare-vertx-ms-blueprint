/*
 *
 */
package org.jspare.spareco.gateway.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Data
@EqualsAndHashCode(callSuper = false)
public class Event extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private Object payload;
}