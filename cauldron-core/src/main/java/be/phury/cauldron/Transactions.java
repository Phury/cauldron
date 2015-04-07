package be.phury.cauldron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.builder.CompareToBuilder;

import be.phury.cauldron.model.Money;
import be.phury.cauldron.model.Transaction;

public class Transactions {

	public static Money sumAll(List<Transaction> entries) {
		Money total = Money.ZERO;
		for (Transaction e : entries) {
			total = total.sum(e.getAmount());
		}
		return total;
	}

	public static List<Transaction> sortByDate(final List<Transaction> transactions) {
		final List<Transaction> sorted = new ArrayList<Transaction>(transactions);
		Collections.sort(sorted, Collections.reverseOrder(new Comparator<Transaction>() {
			@Override public int compare(Transaction o1, Transaction o2) {
				return new CompareToBuilder().append(o1.getEntryDate(), o2.getEntryDate()).toComparison();
			}
		}));
		return sorted;
	}

}
