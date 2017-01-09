/*
 *
 */
package org.jspare.spareco.gateway.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Cipher {

	@SneakyThrows(NoSuchAlgorithmException.class)
	public String encrypt(String text) {
		MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
		byte[] result = mDigest.digest(text.getBytes());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
}