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

	public static final String BANK_ACCOUNT = "ACC";
	public static final String AMOUNT = "AM";
	public static final String CURRENCY = "CC";
	public static final String ALTERNATE_ACCOUNTS = "ALT-ACC";
	public static final String SENDER_REFERENCE = "RF";
	public static final String RECIPIENT_NAME = "RN";
	public static final String DATE = "DT";
	public static final String PAYMENT_TYPE = "PT";
	public static final String MESSAGE = "MSG";
	public static final String NOTIFY_ADDRESS = "NTA";

	protected final Map<String,SpaydValue> elements = new LinkedHashMap<String, SpaydValue>();

	public SpaydPayment(BankAccount bankAccount) {
		notNull(bankAccount);
		elements.put(BANK_ACCOUNT, bankAccount);
	}

	public String asString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("SPD*1.0");
		for (Map.Entry<String,SpaydValue> entry: elements.entrySet()) {
			builder.append("*").append(entry.getKey()).append(":").append(entry.getValue().asString());
		}
		return builder.toString();
	}

	protected void putSimpleValue(String key, Object value) {
		elements.put(key, simpleValue(value));
	}

	protected  <T> T getValue(String key, Class<T> cls) {
		final SpaydValue value = elements.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof SpaydWrappedValue) {
			return getValue(((SpaydWrappedValue) value).getValue(), cls);
		} else {
			return getValue(value, cls);
		}
	}

	private <T> T getValue(Object wrap, Class<T> cls) {
		if (cls.isInstance(wrap))
			return cls.cast(wrap);
		else
			throw new IllegalStateException("Value " + wrap + " is not instance of " + cls.getName());
	}

	public BankAccount getBankAccount() {
		return getValue(BANK_ACCOUNT, BankAccount.class);
	}

	public void setAlternateAccounts(AlternateAccounts alternateAccounts) {
		notNull(alternateAccounts);
		elements.put(ALTERNATE_ACCOUNTS, alternateAccounts);
	}

	public void setAlternateAccounts(BankAccount... bankAccount) {
		setAlternateAccounts(new AlternateAccounts(bankAccount));
	}

	public AlternateAccounts getAlternateAccounts() {
		return getValue(ALTERNATE_ACCOUNTS, AlternateAccounts.class);
	}

	public void setAmount(BigDecimal amount) {
		putSimpleValue(AMOUNT, amount);
	}

	public void setAmount(int amount) {
		setAmount(new BigDecimal(amount));
	}

	public BigDecimal getAmount() {
		return getValue(AMOUNT, BigDecimal.class);
	}

	public void setCurrency(String currency) {
		putSimpleValue(CURRENCY, currency);
	}

	public String getCurrency() {
		return getValue(CURRENCY, String.class);
	}

	public void setSendersReference(int sendersReference) {
		putSimpleValue(SENDER_REFERENCE, sendersReference);
	}

	public Integer getSenderReference() {
		return getValue(SENDER_REFERENCE, Integer.class);
	}

	public void setRecipientName(String recipientName) {
		putSimpleValue(RECIPIENT_NAME, recipientName);
	}

	public String getRecipientName() {
		return getValue(RECIPIENT_NAME, String.class);
	}

	public void setDate(Date date) {
		elements.put(DATE, new SpaydDate(date));
	}

	public Date getDate() {
		return getValue(DATE, Date.class);
	}

	public void setPaymentType(String paymentType) {
		putSimpleValue(PAYMENT_TYPE, paymentType);
	}

	public String getPaymentType() {
		return getValue(PAYMENT_TYPE, String.class);
	}

	public void setMessage(String message) {
		putSimpleValue(MESSAGE, message);
	}

	public String getMessage() {
		return getValue(MESSAGE, String.class);
	}

	public void setEmailNotify(String email) {
		putSimpleValue("NT", "E");
		putSimpleValue(NOTIFY_ADDRESS, email);
	}

	public String getEmailNotify() {
		return getValue(NOTIFY_ADDRESS, String.class);
	}

	public void setPhoneNotify(String phone) {
		putSimpleValue("NT", "P");
		putSimpleValue(NOTIFY_ADDRESS, phone);
	}

	public String getPhoneNotify() {
		return getValue(NOTIFY_ADDRESS, String.class);
	}

	@Override
	public String toString() {
		return elements.toString();
	}

}
