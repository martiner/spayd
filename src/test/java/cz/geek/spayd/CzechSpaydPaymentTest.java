package cz.geek.spayd;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CzechSpaydPaymentTest {

	@Test
	public void testPaymentStringFromAccount() {
		final CzechSpaydPayment payment = new CzechSpaydPayment(new CzechBankAccount("2000009442", "2010"));
		assertEquals("SPD*1.0*ACC:CZ6420100000002000009442*CC:CZK", payment.asString());
	}

	@Test
	public void testPaymentStringFromAccountAmountAndAlternateAccounts() throws UnsupportedEncodingException {
		final CzechSpaydPayment payment = new CzechSpaydPayment(new CzechBankAccount("2000009442", "2010"));
		payment.setAlternateAccounts(new CzechBankAccount("2000009442", "2010"), new CzechBankAccount("2000009442", "2010"));
		payment.setAmount(new BigDecimal(100.5));
		assertEquals("SPD*1.0*ACC:CZ6420100000002000009442*CC:CZK*ALT-ACC:CZ6420100000002000009442,CZ6420100000002000009442*AM:100.5",
				payment.asString());
	}

}
