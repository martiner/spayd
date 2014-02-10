/*
 *  Copyright (c) 2014, martiner
 */
package cz.geek.spayd;

/**
 * Spayd payment extended for Czech national banking
 */
public class CzechSpaydPayment extends SpaydPayment {

	public static final String PERIOD = "X-PER";
	public static final String VS = "X-VS";
	public static final String KS = "X-KS";
	public static final String SS = "X-SS";
	public static final String ID = "X-ID";
	public static final String URL = "X-URL";

	public CzechSpaydPayment(BankAccount bankAccount) {
		super(bankAccount);
		setCurrency("CZK");
	}

	public void setPeriod(int days) {
		putSimpleValue(PERIOD, days);
	}

	public Integer getPeriod() {
		return getValue(PERIOD, Integer.class);
	}

	public void setVs(String vs) {
		putSimpleValue(VS, vs);
	}

	public String getVs() {
		return getValue(VS, String.class);
	}

	public void setKs(String ks) {
		putSimpleValue(KS, ks);
	}

	public String getKs() {
		return getValue(KS, String.class);
	}

	public void setSs(String ss) {
		putSimpleValue(SS, ss);
	}

	public String getSs() {
		return getValue(SS, String.class);
	}

	public void setId(String id) {
		putSimpleValue(ID, id);
	}

	public String getId() {
		return getValue(ID, String.class);
	}

	public void setUrl(String url) {
		putSimpleValue(URL, url);
	}

	public String getUrl() {
		return getValue(URL, String.class);
	}

}
