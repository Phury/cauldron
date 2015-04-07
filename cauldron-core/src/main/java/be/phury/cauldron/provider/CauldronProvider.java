package be.phury.cauldron.provider;

import java.util.List;

import be.phury.cauldron.model.Account;
import be.phury.cauldron.model.Budget;
import be.phury.cauldron.model.Category;
import be.phury.cauldron.model.Transaction;

public interface CauldronProvider {

	void addTransaction(Transaction transaction);

	void addBudget(Budget budget);

	List<Transaction> listAllTransactions();
	
	List<Transaction> listExpenses();
	
	List<Transaction> listIncomes();

	List<Budget> listBudgets();
	
	List<Account> listAccounts();
	
	List<Category> listCategories();

}
