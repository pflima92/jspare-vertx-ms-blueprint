package io.github.pflima92.plyshare.common.web;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * BusinessException.
 * 
 * <p>
 * Use this execption to handler your business errors adding {@link Reason } to be
 * manipulated.
 * </p>
 * <p>
 * This exception was made to handle business errors that might be raised at the
 * service layer, so it is interesting to use this exception as a way to raise
 * service errors.
 * </p>
 * <pre>
 * 		Future.failedFuture(new {@link BusinessException }(Reason.error("reason_code")));
 * </pre>
 */
public class BusinessException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private List<Reason> reasons;

	/**
	 * Instantiates a new business exception.
	 */
	public BusinessException() {
	}

	/**
	 * Instantiates a new business exception.
	 *
	 * @param reasons
	 *            the reasons
	 */
	public BusinessException(List<Reason> reasons) {
		this.reasons = reasons;
	}

	/**
	 * Instantiates a new business exception.
	 *
	 * @param reason
	 *            the reason
	 */
	public BusinessException(Reason reason) {
		addReason(reason);
	}

	/**
	 * Instantiates a new business exception.
	 *
	 * @param message
	 *            the message
	 */
	public BusinessException(String message) {
		super(message);
		addReason(Reason.error(getClass().getSimpleName()).detail(message));
	}

	/**
	 * Instantiates a new business exception.
	 *
	 * @param exception
	 *            the exception
	 */
	public BusinessException(Throwable exception) {
		super(exception);
	}

	/**
	 * Adds the reason.
	 *
	 * @param reason
	 *            the reason
	 * @return the business exception
	 */
	public BusinessException addReason(Reason reason) {

		if (reasons == null) {

			reasons = new ArrayList<>();
		}
		reasons.add(reason);
		return this;
	}
}