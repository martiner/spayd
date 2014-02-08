/*
 *  Copyright (c) 2012, SPAYD (www.spayd.org).
 *  Copyright (c) 2014, martiner
 */
package cz.geek.spayd;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.apache.commons.lang.Validate.notNull;

/**
 * Simple non-null value
 */
public class SimpleValue<T> implements SpaydValue {

	private T value;

	private SimpleValue(T value) {
		notNull(value);
		this.value = value;
	}

	public String asString() {
		return escapeDisallowedCharacters(value.toString().trim());
	}

	/**
	 * Escapes the payment string value in a correct way
	 * @param originalString The original non-escaped value
	 * @return A string with percent encoded characters
	 */
	private static String escapeDisallowedCharacters(String originalString) {
		try {
			final StringBuilder working = new StringBuilder();
			for (int i = 0; i < originalString.length(); i++) {
				if (originalString.charAt(i) > 127) { // escape non-ascii characters
					working.append(URLEncoder.encode("" + originalString.charAt(i), "UTF-8"));
				} else {
					if (originalString.charAt(i) == '*') { // star is a special character for the SPAYD format
						working.append("%2A");
					} else if (originalString.charAt(i) == '+') { // plus is a special character for URL encode
						working.append("%2B");
					} else if (originalString.charAt(i) == '%') { // percent is an escape character
						working.append("%25");
					} else {
						working.append(originalString.charAt(i)); // ascii characters may be used as expected
					}
				}
			}
			return working.toString();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 should ever be supported", e);
		}
	}


	public static <T> SimpleValue<T> simpleValue(T value) {
		return new SimpleValue<T>(value);
	}
}
