package cz.geek.spayd;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CzechSpaydPaymentTest {
	@Test
	public void testPayment() throws Exception {
		final CzechSpaydPayment payment = new CzechSpaydPayment(new CzechBankAccount("2000009442", "2010"));
		assertThat(payment.getBankAccount().getIban(), is("CZ6420100000002000009442"));
		assertThat(payment.getCurrency(), is("CZK"));

		assertThat(payment.getPeriod(), is(nullValue()));
		payment.setPeriod(5);
		assertThat(payment.getPeriod(), is(5));

		assertThat(payment.getVs(), is(nullValue()));
		payment.setVs("vs");
		assertThat(payment.getVs(), is("vs"));

		assertThat(payment.getKs(), is(nullValue()));
		payment.setKs("ks");
		assertThat(payment.getKs(), is("ks"));

		assertThat(payment.getSs(), is(nullValue()));
		payment.setSs("ss");
		assertThat(payment.getSs(), is("ss"));

		assertThat(payment.getId(), is(nullValue()));
		payment.setId("id");
		assertThat(payment.getId(), is("id"));

		assertThat(payment.getUrl(), is(nullValue()));
		payment.setUrl("url");
		assertThat(payment.getUrl(), is("url"));


	}

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
