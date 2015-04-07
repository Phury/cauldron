package be.phury.cauldron;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import be.phury.cauldron.model.Account;
import be.phury.cauldron.model.Budget;
import be.phury.cauldron.model.Category;
import be.phury.cauldron.model.Money;
import be.phury.cauldron.model.Recurrence;
import be.phury.cauldron.model.Transaction;
import be.phury.cauldron.provider.CauldronProvider;
import be.phury.cauldron.time.CalendarService;
import be.phury.cauldron.time.Period;

import com.google.inject.Inject;

public class CauldronServiceImpl implements CauldronService {

	private CalendarService calendarService;
	private CauldronProvider cauldronProvider;
	private CauldronFactory cauldronFactory;
	
	@Inject
	public CauldronServiceImpl(CalendarService calendarService, CauldronProvider cauldronProvider, CauldronFactory cauldronFactory) {
		setCalendarService(calendarService);
		setCauldronProvider(cauldronProvider);
		setCauldronFactory(cauldronFactory);
	}
	
	public CalendarService getCalendarService() {
		return calendarService;
	}

	public void setCalendarService(CalendarService calendarService) {
		this.calendarService = calendarService;
	}

	public CauldronProvider getCauldronProvider() {
		return cauldronProvider;
	}

	public void setCauldronProvider(CauldronProvider cauldronProvider) {
		this.cauldronProvider = cauldronProvider;
	}

	public CauldronFactory getCauldronFactory() {
		return cauldronFactory;
	}

	public void setCauldronFactory(CauldronFactory cauldronFactory) {
		this.cauldronFactory = cauldronFactory;
	}

	@Override
	public void addTransaction(Transaction transaction) {
		getCauldronProvider().addTransaction(transaction);
	}

	@Override
	public void addBudget(Budget budget) {
		getCauldronProvider().addBudget(budget);
	}
	
	public List<Transaction> getExpenses(Period p) {
		return findInPeriod(getCauldronProvider().listAllTransactions(), p);
	}
	
	public List<Transaction> getIncomes(Period p) {
		return findInPeriod(getCauldronProvider().listIncomes(), p);
	}

	public Money countTotalExpenses() {
		return Transactions.sumAll(getExpensesInCurrentPeriod());
	}

	public Money countTotalIncomes() {
		return Transactions.sumAll(getIncomesInCurrentPeriod());
	}

	@Override
	public Money countBalance() {
		return countTotalIncomes().sub(countTotalExpenses());
	}
	
	@Override
	public List<Transaction> getExpensesInCurrentPeriod() {
		return filterInCurrentPeriod(getCauldronProvider().listExpenses());
	}
	
	@Override
	public List<Transaction> getIncomesInCurrentPeriod() {
		return filterInCurrentPeriod(getCauldronProvider().listIncomes());
	}
	
	public List<Transaction> filterByCategory(List<Transaction> toFilter, List<Category> categories) {
		final List<Transaction> result = new LinkedList<Transaction>();
		for (Transaction e : toFilter) {
			if (containsAny(e.getCategories(), categories)) {
				result.add(e);
			}
		}
		return result;
	}
	
	private boolean containsAny(List<?> l1, List<?> l2) {
		return !Collections.disjoint(l1, l2);
	}
	
	public List<Transaction> filterInCurrentPeriod(final List<Transaction> toFilter) {
		return findInPeriod(toFilter, new Period(calendarService.firstOfMonth(), calendarService.today()));
	}
	
	private List<Transaction> findInPeriod(final List<Transaction> toFilter, final Period p) {
		final List<Transaction> result = new LinkedList<Transaction>();
		for (Transaction t : toFilter) {
			if (p.contains(t.getEntryDate())) {
				result.addAll(computeRecurrence(t));
			}
		}
		return result;
	}
	
	private List<Transaction> computeRecurrence(Transaction t) {
		List<Transaction> transactions = new LinkedList<Transaction>();
		
		transactions.add(t);
		if (Recurrence.biMonthly().equals(t.getRecurrence())) {
			if (getWeekOfMonth() >= 3) {
				final Transaction t2 = getCauldronFactory().copyTransaction(t);
				t2.setEntryDate(getCalendarService().today());
				transactions.add(t2);
			}
		}
		return transactions;
	}

	private int getWeekOfMonth() {
		Calendar cal = Calendar.getInstance();
	    cal.setTime(getCalendarService().today());
	    cal.setMinimalDaysInFirstWeek(1);
	    return cal.get(Calendar.WEEK_OF_MONTH);
	}
	
	@Override
	public Budget findBudgetByName(String budgetName) {
		for (Budget b : getCauldronProvider().listBudgets()) {
			if (b.getName().equals(budgetName)) {
				return b;
			}
		}
		throw new IllegalArgumentException("no budget found with name ["+budgetName+"]");
	}
	
	@Override
	public Account findAccountByName(String accountName) {
		for (Account a : getCauldronProvider().listAccounts()) {
			if (a.getName().equals(accountName)) {
				return a;
			}
		}
		throw new IllegalArgumentException("no account found with name ["+accountName+"]");
	}
	
	@Override
	public List<Category> findAllCategoriesByName(List<String> categoryNames) {
		List<Category> categories = new LinkedList<Category>();
		for (Category c : getCauldronProvider().listCategories()) {
			if (categoryNames.contains(c.getName())) {
				categories.add(c);
			}
		}
		return categories;
	}

	@Override
	public List<Category> getAllCategories() {
		final Set<Category> categories = new HashSet<Category>();
		for (Transaction t : getCauldronProvider().listAllTransactions()) {
			categories.addAll(t.getCategories());
		}
		return new ArrayList<Category>(categories);
	}
	
	@Override
	public List<Account> getAllAccounts() {
		final Set<Account> accounts = new HashSet<Account>();
		for (Transaction t : getCauldronProvider().listAllTransactions()) {
			accounts.add(t.getAccount());
		}
		return new ArrayList<Account>(accounts);
	}
	
	@Override
	public Money countBalanceForBudget(String budgetName) {
		Budget budget = findBudgetByName(budgetName);
		List<Transaction> expensesMatchingBudget = filterByCategory(getExpensesInCurrentPeriod(), budget.getCategories());
		return budget.getAmount().sub(Transactions.sumAll(expensesMatchingBudget));
	}

}
