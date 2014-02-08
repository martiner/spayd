package cz.geek.spayd;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CzechBankAccountTest {

	@Test
	public void testComputeIBANFromBankAccount() {
		CzechBankAccount account = new CzechBankAccount("19", "2000145399", "0800");
		assertEquals("CZ6508000000192000145399", account.getIban());
	}

	@Test
	public void testComputeIBANFromBankAccount2() {
		CzechBankAccount account = new CzechBankAccount("178124", "4159", "0710");
		assertEquals("CZ6907101781240000004159", account.getIban());
	}

	@Test
	public void shouldParseAccountFromString() {
		final CzechBankAccount account = new CzechBankAccount("2000009442 / 2010");
		assertEquals("CZ6420100000002000009442", account.getIban());
	}
}
