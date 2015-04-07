package be.phury.cauldron.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import be.phury.cauldron.model.Account;
import be.phury.cauldron.model.Budget;
import be.phury.cauldron.model.Category;
import be.phury.cauldron.model.Transaction;
import be.phury.cauldron.model.TransactionType;

import com.google.inject.Singleton;

@Singleton
public class CauldronProviderMemory implements CauldronProvider {

	private Map<String, Transaction> transactions = new HashMap<String, Transaction>();
	private Map<String, Budget> budgets = new HashMap<String, Budget>();
	
	@Override
	public void addTransaction(Transaction transaction) {
		transactions.put(transaction.getId(), transaction);
	}

	@Override
	public void addBudget(Budget budget) {
		budgets.put(budget.getId(), budget);
	}

	@Override
	public List<Transaction> listAllTransactions() {
		return Collections.unmodifiableList(new ArrayList<Transaction>(transactions.values()));
	}
	
	@Override
	public List<Transaction> listExpenses() {
		return filterTransactionsByType(TransactionType.EXPENSE);
	}
	
	@Override
	public List<Transaction> listIncomes() {
		return filterTransactionsByType(TransactionType.INCOME);
	}
	
	private List<Transaction> filterTransactionsByType(final TransactionType type) {
		List<Transaction> filteredTransactions = new ArrayList<Transaction>(listAllTransactions());
		CollectionUtils.filter(filteredTransactions, new Predicate() {
			@Override public boolean evaluate(Object o) { return ((Transaction)o).getTransactionType() == type; }
		});
		return filteredTransactions;
	}

	@Override
	public List<Budget> listBudgets() {
		return Collections.unmodifiableList(new ArrayList<Budget>(budgets.values()));
	}

	@Override
	public List<Account> listAccounts() {
		Map<String, Account> accounts = new HashMap<String, Account>();
		for (Transaction t : listAllTransactions()) {
			accounts.put(t.getAccount().getName(), t.getAccount());
		}
		return new ArrayList<Account>(accounts.values());
	}

	@Override
	public List<Category> listCategories() {
		Map<String, Category> categories = new HashMap<String, Category>();
		for (Transaction t : listAllTransactions()) {
			for (Category c : t.getCategories()) {
				categories.put(c.getName(), c);
			}
		}
		return new ArrayList<Category>(categories.values());
	}
}
