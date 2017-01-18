package io.github.pflima92.plyshare.common.web;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Reason : This class is used to hold response reason information in service
 * layer to presentation layer. This class is used by ResponseInfo.
 */
@Accessors(fluent = true)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Reason implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Reason error(String code) {

		return error(code, StringUtils.EMPTY);
	}
	
	public static Reason error(String code, String detail) {

		return new Reason(code, ReasonTypes.ERROR).detail(detail);
	}

	public static Reason info(String code) {

		return info(code, StringUtils.EMPTY);
	}
	
	public static Reason info(String code, String detail) {

		return new Reason(code, ReasonTypes.INFO).detail(detail);
	}

	public static Reason warning(String code) {

		return warning(code, StringUtils.EMPTY);
	}
	
	public static Reason warning(String code, String detail) {

		return new Reason(code, ReasonTypes.WARNING).detail(detail);
	}

	/** The reason code. */
	private final String code;

	/** The type of reason (i.e. Error, Warning, Success). */
	private final int type;
	/**
	 * The detail information. This is additional information supplied with
	 * reason code to be used in client code for creating user friendly message.
	 * change to String for Entitlement Reasons
	 */
	private String detail;

	/**
	 * Instantiates a new reason.
	 *
	 * @param code the code
	 * @param type the type
	 */
	public Reason(String code, ReasonTypes type) {
		this.code = code;
		this.type = type.getCode();
	}
}