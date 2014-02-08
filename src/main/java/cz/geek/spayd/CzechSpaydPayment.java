/*
 *  Copyright (c) 2014, martiner
 */
package cz.geek.spayd;

/**
 * Spayd payment extended for Czech national banking
 */
public class CzechSpaydPayment extends SpaydPayment {

	public CzechSpaydPayment(BankAccount bankAccount) {
		super(bankAccount);
		setCurrency("CZK");
	}

	public void setPeriod(int days) {
		putSimpleValue("X-PER", days);
	}

	public void setVs(String vs) {
		putSimpleValue("X-VS", vs);
	}

	public void setKs(String ks) {
		putSimpleValue("X-KS", ks);
	}

	public void setSs(String ss) {
		putSimpleValue("X-SS", ss);
	}

	public void setId(String id) {
		putSimpleValue("X-ID", id);
	}

	public void setUrl(String url) {
		putSimpleValue("X-URL", url);
	}

}
