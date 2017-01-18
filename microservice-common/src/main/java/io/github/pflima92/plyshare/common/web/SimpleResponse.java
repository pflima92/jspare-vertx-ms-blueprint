package io.github.pflima92.plyshare.common.web;

public class SimpleResponse {

	/** The self. */
	private String self;

	/** The response info. */
	private ResponseInfo responseInfo;

	/** The data. */
	private Object value;

	/**
	 * Reason.
	 *
	 * @param reason
	 *            the reason
	 * @return the simple response
	 */
	public SimpleResponse addReason(Reason reason) {

		if (responseInfo == null) {

			responseInfo = new ResponseInfo();
		}
		responseInfo.addReason(reason);
		return this;
	}

	public String getSelf() {
		return self;
	}

	public SimpleResponse setSelf(String self) {
		this.self = self;
		return this;
	}

	public ResponseInfo getResponseInfo() {
		return responseInfo;
	}

	public SimpleResponse setResponseInfo(ResponseInfo responseInfo) {
		this.responseInfo = responseInfo;
		return this;
	}

	public Object getValue() {
		return value;
	}

	public SimpleResponse setValue(Object value) {
		this.value = value;
		return this;
	}
}
