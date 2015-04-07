package be.phury.cauldron.model;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Transaction extends Model {
	
	private TransactionType transactionType;
	private Account account;
	private String label;
	private Money amount;
	private List<Category> categories;
	private Date entryDate;
	private Recurrence recurrence;
	
	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	public Recurrence getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(Recurrence recurrence) {
		this.recurrence = recurrence;
	}

	@Override
	protected Map<String, Object> getSignificantFields() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("type", getTransactionType());
		m.put("id", getId());
		m.put("account", getAccount());
		m.put("label", getLabel());
		m.put("amount", getAmount());
		m.put("categories", "["+StringUtils.join(getCategories(), ", ")+"]");
		m.put("entryDate", getEntryDate());
		m.put("recurrence", getRecurrence());
		return Collections.unmodifiableMap(m);
	}
}