package cz.geek.spayd;

import java.io.File;
import java.util.Date;

public class Payment {

	public static void main(String... args) {
		final BankAccount account = new CzechBankAccount("2000009442/2010");
		final CzechSpaydPayment payment = new CzechSpaydPayment(account);
		payment.setAmount(100);
		payment.setDate(new Date());
		payment.setVs("123");
		payment.setRecipientName("martiner");
		payment.setMessage("Thanks for Geek SPAYD!");

		final SpaydQRFactory factory = new SpaydQRFactory();
		factory.saveQRCode(payment, "png", new File("payment.png"));
	}
}
