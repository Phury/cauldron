package be.phury.cauldron;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.phury.cauldron.model.Account;
import be.phury.cauldron.model.Budget;
import be.phury.cauldron.model.Category;
import be.phury.cauldron.model.Money;
import be.phury.cauldron.model.Recurrence;
import be.phury.cauldron.model.Transaction;
import be.phury.cauldron.model.TransactionType;

import com.google.inject.Singleton;

@Singleton
public class CauldronFactory {
	
	private Logger logger = LoggerFactory.getLogger(CauldronFactory.class);
	
	public Transaction createBiMonthlyTransaction(Transaction transaction) {
		Transaction biMonthlyTransaction = copyTransaction(transaction);
		biMonthlyTransaction.setTransactionType(getTypeForRecurrence(transaction.getTransactionType()));
		biMonthlyTransaction.setRecurrence(Recurrence.biMonthly());
		return log(biMonthlyTransaction);
	}
	
	private TransactionType getTypeForRecurrence(TransactionType type) {
//		if (type == TransactionType.EXPENSE || type == TransactionType.RECURRENT_EXPENSE) return TransactionType.RECURRENT_EXPENSE;
//		else if (type == TransactionType.INCOME || type == TransactionType.RECURRENT_INCOME) return TransactionType.RECURRENT_INCOME;
//		else if (type == TransactionType.TRANSFER || type == TransactionType.RECURRENT_TRANSFER) return TransactionType.RECURRENT_TRANSFER;
//		else throw new IllegalArgumentException("recurrence cannot be computed for type ["+type+"]");
		return type;
	}

	public Budget createBudget(String money, List<String> categories, String name) {
		Budget b = new Budget();
		b.setAmount(parseMoney(money));
		b.setCategories(parseCategories(categories));
		b.setId(id());
		b.setName(name);
		return log(b);
	}

	public Transaction createIncome(String money, List<String> categories, String date, String detail, String account) {
		Transaction income = createTransaction(money, categories, date, detail, account, TransactionType.INCOME);
		return log(income);
	}

	public Transaction createExpense(String money, List<String> categories, String date, String detail, String account) {
		Transaction expense = createTransaction(money, categories, date, detail, account, TransactionType.EXPENSE);
		return log(expense);
	}
	
	private List<Category> parseCategories(List<String> categoryNames) {
		final List<Category> categories = new LinkedList<Category>();
		for (String s : categoryNames) {
			categories.add(createCategory(s));
		}
		return categories;
	}
	
	public Category createCategory(String name) {
		Category c = new Category();
//		c.setId(id());
		c.setName(name);
		return log(c);
	}
	
	public Account createAccount(String name) {
		Account a = new Account();
//		a.setId(id());
		a.setName(name);
		return log(a);
	}
	
	private Transaction createTransaction(String money, List<String> categories, String date, String detail, String account, TransactionType type) {
		Transaction t = new Transaction();
		t.setAccount(createAccount(account));
		t.setAmount(parseMoney(money));
		t.setCategories(parseCategories(categories));
		t.setEntryDate(parseDate(date));
		t.setId(id());
		t.setLabel(detail);
		t.setRecurrence(Recurrence.none());
		t.setTransactionType(type);
		return t;
	}
	
	public Transaction copyTransaction(Transaction toCopy) {
		Transaction t = new Transaction();
		t.setAccount(toCopy.getAccount());
		t.setAmount(toCopy.getAmount());
		t.setCategories(toCopy.getCategories());
		t.setEntryDate(toCopy.getEntryDate());
		t.setId(toCopy.getId());
		t.setLabel(toCopy.getLabel());
		t.setRecurrence(toCopy.getRecurrence());
		t.setTransactionType(toCopy.getTransactionType());
		return t;
	}

	private String id() {
		return UUID.randomUUID().toString();
	}
	
	private Money parseMoney(String money) {
//		final String amount = StringUtils.replace(money.substring(0, money.length()-3), ".", "");
//		final String currency = money.substring(money.length()-3, money.length());
		final String currency = money.substring(0, "CAD$".length());
		final String amount = StringUtils.replace(money.substring("CAD$".length(), money.length()), ".", "");
		return new Money(Integer.parseInt(amount), currency);
	}
	
	private Date parseDate(String date) {
		try {
			return new SimpleDateFormat("dd-MM-yyyy").parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException("invalid date ["+date+"]", e);
		}
	}
	
	private <T> T log(T t) {
		logger.debug("created: " + t.toString());
		return t;
	}
}
