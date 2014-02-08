/*
 *  Copyright (c) 2014, martiner
 */
package cz.geek.spayd;

import static org.apache.commons.lang.Validate.notEmpty;

/**
 * Bank account
 */
public class BankAccount implements SpaydValue {
	private String iban;
	private String bic;

	public BankAccount(String iban, String bic) {
		notEmpty(iban, "iban can't be empty");
		notEmpty(bic, "bic can't be empty");
		this.iban = iban;
		this.bic = bic;
	}

	public BankAccount(String iban) {
		this.iban = iban;
	}

	public String getIban() {
		return iban;
	}

	public String getBic() {
		return bic;
	}

	public String asString() {
		return bic == null ? iban : iban + "+" + bic;
	}
}
