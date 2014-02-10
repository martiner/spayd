package cz.geek.spayd;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class SpaydPaymentTest {
	@Test
	public void testPayment() throws Exception {
		final SpaydPayment payment = new SpaydPayment(new BankAccount("1234"));
		assertThat(payment.getBankAccount().getIban(), is("1234"));

		assertThat(payment.getAmount(), is(nullValue()));
		payment.setAmount(100);
		assertThat(payment.getAmount(), is(new BigDecimal(100)));

		assertThat(payment.getCurrency(), is(nullValue()));
		payment.setCurrency("EUR");
		assertThat(payment.getCurrency(), is("EUR"));

		assertThat(payment.getAlternateAccounts(), is(nullValue()));
		payment.setAlternateAccounts(new BankAccount("4321"));
		assertThat(payment.getAlternateAccounts().getAccounts().get(0).getIban(), is("4321"));

		assertThat(payment.getSenderReference(), is(nullValue()));
		payment.setSendersReference(123);
		assertThat(payment.getSenderReference(), is(123));

		assertThat(payment.getRecipientName(), is(nullValue()));
		payment.setRecipientName("foo");
		assertThat(payment.getRecipientName(), is("foo"));

		assertThat(payment.getDate(), is(nullValue()));
		final Date date = new Date();
		payment.setDate(date);
		assertThat(payment.getDate(), is(date));

		assertThat(payment.getPaymentType(), is(nullValue()));
		payment.setPaymentType("bar");
		assertThat(payment.getPaymentType(), is("bar"));

		assertThat(payment.getMessage(), is(nullValue()));
		payment.setMessage("baz");
		assertThat(payment.getMessage(), is("baz"));

		assertThat(payment.getEmailNotify(), is(nullValue()));
		assertThat(payment.getPhoneNotify(), is(nullValue()));
		payment.setEmailNotify("email");
		assertThat(payment.getEmailNotify(), is("email"));
		payment.setPhoneNotify("phone");
		assertThat(payment.getEmailNotify(), is("phone"));

	}
}
