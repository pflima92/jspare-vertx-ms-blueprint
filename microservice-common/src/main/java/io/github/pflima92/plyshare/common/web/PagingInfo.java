package io.github.pflima92.plyshare.common.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * PagingInfo - This class is used to hold pagination information.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PagingInfo extends SimpleResponse {

	/** The total number of available items. */
	private int total;

	/**
	 * The index (zero-based) of the starting item in a list (or response
	 * message).
	 */
	private int startItem;

	/** The number of items in a list (or response message). */
	private int numberOfRecords;
}