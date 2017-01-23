/*
 *
 */
package io.github.pflima92.plyshare.common.web.handlers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang.StringUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TidGenerator {

	/** The Constant TIMESTAMP_FORMAT. */
	private final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";

	/** The Constant MILLI_OF_SECOND. */
	private final int MILLI_OF_SECOND = 3;

	/** The Constant MIN. */
	private final long MIN = 100000l;

	/** The Constant MAX. */
	private final long MAX = 989898l;

	/** The dtf. */
	private DateTimeFormatter dtf = new DateTimeFormatterBuilder().appendPattern(TIMESTAMP_FORMAT)
			.appendValue(ChronoField.MILLI_OF_SECOND, MILLI_OF_SECOND).toFormatter();

	public String generate() {

		StringBuilder tid = new StringBuilder();
		tid.append(LocalDateTime.now().format(dtf));
		tid.append(StringUtils.leftPad(String.valueOf(ThreadLocalRandom.current().nextLong(MIN, MAX)), 6, "0"));
		tid.append(calculateVerifyDigit(tid.toString()));
		return tid.toString();
	}

	public boolean validate(final String tid) {

		try {
			if (StringUtils.isEmpty(tid)) {
				return false;
			}

			String prefix = tid.substring(0, TIMESTAMP_FORMAT.length() + MILLI_OF_SECOND);
			if (!LocalDateTime.parse(prefix, dtf).isBefore(LocalDateTime.now())) {
				return false;
			}

			long sufix = Long.parseLong(tid.substring(TIMESTAMP_FORMAT.length() + MILLI_OF_SECOND, tid.length() - 1));
			if (sufix <= MIN || sufix >= MAX) {
				return false;
			}
			int verifyDigit = Integer.parseInt(String.valueOf(tid.charAt(tid.length() - 1)));

			return validateVerifyDigit(tid, verifyDigit);

		} catch (Exception e) {

			return false;
		}
	}

	/**
	 * Calculate verify digit.
	 *
	 * @param preTid
	 *            the pre tid
	 * @return the int
	 */
	private int calculateVerifyDigit(final String preTid) {

		int calc = 0;
		for (int i = 0, n = preTid.length(); i < n; i++) {
			if (i % 2 != 0) {
				calc += preTid.charAt(i);
			}
		}
		calc *= 3;
		for (int p = 0, n = preTid.length(); p < n; p++) {
			if (p % 2 == 0) {
				calc += preTid.charAt(p);
			}
		}

		int d = 0;
		while ((calc + d) % 10 != 0) {
			d++;
		}
		return d;
	}

	/**
	 * Validate verify digit.
	 *
	 * @param tid
	 *            the tid
	 * @param verifyDigit
	 *            the verify digit
	 * @return true, if successful
	 */
	private boolean validateVerifyDigit(final String tid, final int verifyDigit) {

		String preTid = tid.substring(0, tid.length() - 1);
		return calculateVerifyDigit(preTid) == verifyDigit;
	}
}