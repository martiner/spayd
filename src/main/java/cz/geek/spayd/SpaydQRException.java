/*
 *  Copyright (c) 2014, martiner
 */
package cz.geek.spayd;

public class SpaydQRException extends RuntimeException {
	public SpaydQRException(String message) {
		super(message);
	}

	public SpaydQRException(String message, Throwable cause) {
		super(message, cause);
	}
}
