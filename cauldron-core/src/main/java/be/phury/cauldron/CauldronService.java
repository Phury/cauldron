package be.phury.cauldron;

import java.util.List;

import be.phury.cauldron.model.Account;
import be.phury.cauldron.model.Budget;
import be.phury.cauldron.model.Category;
import be.phury.cauldron.model.Money;
import be.phury.cauldron.model.Transaction;

public interface CauldronService {

	List<Transaction> getExpensesInCurrentPeriod();

	List<Transaction> getIncomesInCurrentPeriod();

	Budget findBudgetByName(String budgetName);
	
	Account findAccountByName(String account);

	List<Category> findAllCategoriesByName(List<String> asList);

	List<Category> getAllCategories();
	
	List<Account> getAllAccounts();

	Money countBalance();
	
	Money countBalanceForBudget(String budgetName);
	
	Money countTotalExpenses();
	
	Money countTotalIncomes();
	
	void addTransaction(Transaction transaction);

	void addBudget(Budget budget);

}