package be.phury.cauldron.model;

import java.util.LinkedList;
import java.util.List;

public class TransactionCollection {

	private List<Transaction> money = new LinkedList<Transaction>();
	
	public TransactionCollection() {
	}
	
	public TransactionCollection(List<Transaction> money) {
		this.money.addAll(money);
	}
}
