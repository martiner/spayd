/*
 *  Copyright (c) 2014, martiner
 */
package cz.geek.spayd;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Formatted non-null date
 */
public class SpaydDate extends SpaydWrappedValue<Date> {

	private final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

	public SpaydDate(Date date) {
		super(date);
	}

	public String asString() {
		return format.format(value);
	}

	@Override
	public String toString() {
		return asString();
	}

}
