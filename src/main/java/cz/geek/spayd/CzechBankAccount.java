/*
 *  Copyright (c) 2012, SPAYD (www.spayd.org).
 *  Copyright (c) 2014, martiner
 */
package cz.geek.spayd;

import static org.apache.commons.lang.Validate.isTrue;
import static org.apache.commons.lang.Validate.notEmpty;

/**
 * Czech bank account number
 */
public class CzechBankAccount extends BankAccount {

	public CzechBankAccount(String accountPrefix, String accountNumber, String bankCode) {
		super(createIban(accountPrefix, accountNumber, bankCode));
	}

	public CzechBankAccount(String number, String bankCode) {
		this(null, number, bankCode);
	}

	public CzechBankAccount(String account) {
		super(parseIban(account));
	}

	private static String parseIban(String account) {
		notEmpty(account);
		final String[] split = account.split("/");
		if (split.length != 2)
			throw new IllegalArgumentException("Expecting string in pattern 'number/bank': " + account);
		final String bankCode = split[1].trim();

		final String[] number = split[0].split("-");
		final String accountPrefix;
		final String accountNumber;
		if (number.length > 2)
			throw new IllegalArgumentException("Only one '-' allowed in bank number: " + account);
		else if (number.length == 2) {
			accountPrefix = number[0].trim();
			accountNumber = number[1].trim();
		} else {
			accountPrefix = null;
			accountNumber = number[0].trim();
		}
		return createIban(accountPrefix, accountNumber, bankCode);
	}

	private static String createIban(String accountPrefix, String accountNumber, String bankCode) {
		final String prefix = String.format("%06d", longValue11("accountPrefix", accountPrefix != null ? accountPrefix : "000000"));
		final String number = String.format("%010d", longValue11("accountNumber", accountNumber));
		final String bank = String.format("%04d", longValue("bankCode", bankCode));

		// calculate the check sum
		final  String buf = bank + prefix + number + "123500";
		int index = 0;
		String dividend;
		int pz = -1;
		while (index <= buf.length()) {
			if (pz < 0) {
				dividend = buf.substring(index, Math.min(index + 9, buf.length()));
				index += 9;
			} else if (pz >= 0 && pz <= 9) {
				dividend = pz + buf.substring(index, Math.min(index + 8, buf.length()));
				index += 8;
			} else {
				dividend = pz + buf.substring(index, Math.min(index + 7, buf.length()));
				index += 7;
			}
			pz = Integer.valueOf(dividend) % 97;
		}
		pz = 98 - pz;

		return "CZ" + String.format("%02d", pz) + bank + prefix + number;
	}

	private static long longValue11(String name, String number) {
		final long value = longValue(name, number);

		int weight = 1;
		int sum = 0;
		for (int k = number.length() - 1; k >= 0; k--) {
			sum += (number.charAt(k) - '0') * weight;
			weight *= 2;
		}

		isTrue(sum % 11 == 0, name + " must pass bank mod 11 test");
		return value;
	}

	private static long longValue(String name, String value) {
		notEmpty(value, name + " can't be empty");
		return Long.valueOf(value);
	}
}
