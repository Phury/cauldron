package be.phury.cauldron.provider.mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import be.phury.cauldron.model.Account;
import be.phury.cauldron.model.Budget;
import be.phury.cauldron.model.Category;
import be.phury.cauldron.model.Transaction;
import be.phury.cauldron.model.TransactionType;
import be.phury.cauldron.provider.CauldronProvider;
import be.phury.cauldron.provider.mongo.dao.BudgetDao;
import be.phury.cauldron.provider.mongo.dao.ExpenseDao;
import be.phury.cauldron.provider.mongo.dao.IncomeDao;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CauldronProviderMongo implements CauldronProvider {

	private ExpenseDao expenseDao;
	private IncomeDao incomeDao;
	private BudgetDao budgetDao;
	
	@Inject
	public CauldronProviderMongo(
			ExpenseDao expenseDao, 
			IncomeDao incomeDao, 
			BudgetDao budgetDao) {
		this.expenseDao = expenseDao;
		this.incomeDao = incomeDao;
		this.budgetDao = budgetDao;
	}

	@Override
	public void addTransaction(Transaction transaction) {
		if (TransactionType.EXPENSE == transaction.getTransactionType()) {
			expenseDao.insertOne(transaction);
		} else if (TransactionType.INCOME == transaction.getTransactionType()) {
			incomeDao.insertOne(transaction);
		} else {
			throw new IllegalArgumentException("cannot store " + transaction.getTransactionType());
		}
	}

	@Override
	public List<Transaction> listAllTransactions() {
		final List<Transaction> transactions = new LinkedList<Transaction>();
		transactions.addAll(listExpenses());
		transactions.addAll(listIncomes());
		return transactions;
	}

	@Override
	public List<Transaction> listExpenses() {
		return expenseDao.listAll(Transaction.class);
	}
	
	@Override
	public List<Transaction> listIncomes() {
		return incomeDao.listAll(Transaction.class);
	}
	
	@Override
	public void addBudget(Budget budget) {
		budgetDao.insertOne(budget);
	}

	@Override
	public List<Budget> listBudgets() {
		return budgetDao.listAll(Budget.class);
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
