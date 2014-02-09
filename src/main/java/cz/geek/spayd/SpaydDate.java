/*
 *  Copyright (c) 2014, martiner
 */
package cz.geek.spayd;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.commons.lang.Validate.notNull;

/**
 * Formatted non-null date
 */
public class SpaydDate implements SpaydValue {

	private final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

	private Date date;

	public SpaydDate(Date date) {
		notNull(date);
		this.date = date;
	}

	public String asString() {
		return format.format(date);
	}

	@Override
	public String toString() {
		return asString();
	}

}
