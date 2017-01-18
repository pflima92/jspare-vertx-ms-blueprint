/**
 * Copyright 2016 Senior Sistemas.
 *
 * Software sob Medida
 *
 */
package io.github.pflima92.plyshare.common.web;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * ResponseInfo : This class is used to carry response status information from
 * service to the application presentation layer with reason codes.
 */
@Data
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResponseInfo {

	/** The reasons. */
	private List<Reason> reasons;
	
	public ResponseInfo(Reason reason){
		addReason(reason);
	}

	
	/**
	 * Adds the reason.
	 *
	 * @param reason the reason
	 */
	public void addReason(final Reason reason) {
		if (reasons == null) {
			reasons = new ArrayList<Reason>();
		}
		reasons.add(reason);
	}
}
