/*
 *  Copyright (c) 2014, martiner
 */
package cz.geek.spayd;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static cz.geek.spayd.SimpleValue.simpleValue;
import static org.apache.commons.lang.Validate.notNull;

/**
 * Spayd payment
 */
public class SpaydPayment {

	public static final String MIME_TYPE = "application/x-shortpaymentdescriptor";

	protected final Map<String,SpaydValue> elements = new LinkedHashMap<String, SpaydValue>();

	public SpaydPayment(BankAccount bankAccount) {
		notNull(bankAccount);
		elements.put("ACC", bankAccount);
	}

	public String asString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SPD*1.0");
		for (Map.Entry<String,SpaydValue> entry: elements.entrySet()) {
			builder.append("*").append(entry.getKey()).append(":").append(entry.getValue().asString());
		}
		return builder.toString();
	}

	public void setAlternateAccounts(AlternateAccounts alternateAccounts) {
		notNull(alternateAccounts);
		elements.put("ALT-ACC", alternateAccounts);
	}

	protected void putSimpleValue(String key, Object value) {
		elements.put(key, simpleValue(value));
	}

	public void setAlternateAccounts(BankAccount... bankAccount) {
		setAlternateAccounts(new AlternateAccounts(bankAccount));
	}

	public void setAmount(BigDecimal amount) {
		putSimpleValue("AM", amount);
	}

	public void setAmount(int amount) {
		setAmount(new BigDecimal(amount));
	}

	public void setCurrency(String currency) {
		putSimpleValue("CC", currency);
	}

	public void setSendersReference(int sendersReference) {
		putSimpleValue("RF", sendersReference);
	}

	public void setRecipientName(String recipientName) {
		putSimpleValue("RN", recipientName);
	}

	public void setDate(Date date) {
		elements.put("DT", new SpaydDate(date));
	}

	public void setPaymentType(String paymentType) {
		putSimpleValue("PT", paymentType);
	}

	public void setMessage(String message) {
		putSimpleValue("MSG", message);
	}

	public void setEmailNotify(String email) {
		putSimpleValue("NT", "E");
		putSimpleValue("NTA", email);
	}

	public void setPhoneNotify(String phone) {
		putSimpleValue("NT", "P");
		putSimpleValue("NTA", phone);
	}

}
