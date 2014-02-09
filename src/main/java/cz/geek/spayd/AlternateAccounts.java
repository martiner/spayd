/*
 *  Copyright (c) 2014, martiner
 */
package cz.geek.spayd;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.Validate.noNullElements;

/**
 * Alternate {@link BankAccount}s
 */
public class AlternateAccounts implements SpaydValue {

	private List<BankAccount> accounts;

	public AlternateAccounts(List<BankAccount> accounts) {
		noNullElements(accounts);
		this.accounts = new ArrayList<BankAccount>(accounts);
	}

	public AlternateAccounts(BankAccount... accounts) {
		this(asList(accounts));
	}

	public String asString() {
		final StringBuilder s = new StringBuilder();
		int i = 0;
		for (BankAccount account: accounts) {
			s.append(account.asString());
			i++;
			if (accounts.size() != i)
				s.append(",");
		}
		return s.toString();
	}

	@Override
	public String toString() {
		return asString();
	}

}
