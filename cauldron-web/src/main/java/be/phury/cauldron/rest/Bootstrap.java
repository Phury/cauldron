package be.phury.cauldron.rest;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.phury.cauldron.CauldronFactory;
import be.phury.cauldron.CauldronService;
import be.phury.cauldron.time.CalendarService;

import com.google.inject.Inject;

public class Bootstrap {
	
	private Logger logger = LoggerFactory.getLogger(Bootstrap.class);
	
	private CauldronService cauldronService;
	private CalendarService calendarService;
	private CauldronFactory cauldronFactory;
	
	@Inject
	public Bootstrap(CauldronService cauldronService, CalendarService calendarService, CauldronFactory cauldronFactory) {
		this.cauldronService = cauldronService;
		this.calendarService = calendarService;
		this.cauldronFactory = cauldronFactory;
	}

	@Inject
	public void postConstruct() {
		calendarService.setDate("10-11-2014");
		if (cauldronService.getAllCategories().isEmpty()) {
			logger.debug("bootstraping expenses");
			addExpense("CAD$10.45", Arrays.asList("restaurant", "outgoing"), "03-11-2014", "santouka", "chequing");
			addExpense("CAD$2.50", Arrays.asList("restaurant", "cofee"), "03-11-2014", "timmies", "chequing");
			addExpense("CAD$13.13", Arrays.asList("groceries"), "02-11-2014", "chinese supermarket", "chequing");
			addExpense("CAD$50.59", Arrays.asList("groceries"), "03-11-2014", "no frills", "chequing");
			addExpense("CAD$7.50", Arrays.asList("groceries"), "03-11-2014", "chinese supermarket", "chequing");
			addExpense("CAD$24.99", Arrays.asList("gift"), "02-11-2014", "turtles trucker cap", "visa");
			addExpense("CAD$289.99", Arrays.asList("restaurant", "outgoing"), "01-11-2014", "360 restaurant", "visa");
			addIncome("CAD$150.00", Arrays.asList("sell"), "01-11-2014", "Sell longboard", "cash");
			addBiMonthlyIncome("CAD$1800.00", Arrays.asList("salary"), "01-11-2014", "salary october", "chequing");
			addBudget("CAD$250.00", Arrays.asList("restaurant", "outgoing"), "outgoing");
			addBudget("CAD$250.00", Arrays.asList("groceries"), "groceries");
		} else {
			logger.debug("skipping bootstrap");
		}
	}
	
	private void addIncome(String money, List<String> categories, String date, String detail, String account) {
		cauldronService.addTransaction(cauldronFactory.createIncome(money, categories, date, detail, account));
	}
	
	private void addBiMonthlyIncome(String money, List<String> categories, String date, String detail, String account) {
		cauldronService.addTransaction(cauldronFactory.createBiMonthlyTransaction(cauldronFactory.createIncome(money, categories, date, detail, account)));
	}
	
	private void addExpense(String money, List<String> categories, String date, String detail, String account) {
		cauldronService.addTransaction(cauldronFactory.createExpense(money, categories, date, detail, account));
	}
	
	private void addBudget(String money, List<String> categories, String name) {
		cauldronService.addBudget(cauldronFactory.createBudget(money, categories, name));
	}
}
