package be.phury.cauldron;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import be.phury.cauldron.provider.CauldronProvider;
import be.phury.cauldron.provider.CauldronProviderMemory;
import be.phury.cauldron.time.CalendarService;

public class CauldronTest {

	@Test
	public void testCauldron() throws Exception {
		CauldronProvider provider = new CauldronProviderMemory();
		
		CalendarService calendarService = new CalendarService();
		calendarService.setDateFormat("dd-MM-yyyy");
		
		CauldronFactory factory = new CauldronFactory();
		CauldronService service = new CauldronServiceImpl(calendarService, provider, factory);
		
		service.addTransaction(factory.createExpense("CAD$10.45", Arrays.asList("restaurant", "outgoing"), "03-09-2014", "santouka", "chequing"));
		service.addTransaction(factory.createExpense("CAD$2.50", Arrays.asList("restaurant", "cofee"), "03-09-2014", "timmies", "chequing"));
		service.addTransaction(factory.createExpense("CAD$13.13", Arrays.asList("groceries"), "02-09-2014", "chinese supermarket", "chequing"));
		
		service.addTransaction(factory.createBiMonthlyTransaction(factory.createIncome("CAD$1800.00", Arrays.asList("salary"), "01-09-2014", "salary september", "chequing")));
		
		service.addBudget(factory.createBudget("CAD$250.00", Arrays.asList("restaurant", "outgoing"), "outgoing"));
		service.addBudget(factory.createBudget("CAD$250.00", Arrays.asList("groceries"), "groceries"));
		
		calendarService.setDate("14-09-2014");
		Assert.assertEquals("CAD$26.08", service.countTotalExpenses().toString());
		Assert.assertEquals("CAD$1800.00", service.countTotalIncomes().toString());
		Assert.assertEquals("CAD$1773.92", service.countBalance().toString());
		
		Assert.assertEquals("CAD$237.05", service.countBalanceForBudget("outgoing").toString());
		Assert.assertEquals("CAD$236.87", service.countBalanceForBudget("groceries").toString());
		
		calendarService.setDate("16-09-2014");
		Assert.assertEquals("CAD$3573.92", service.countBalance().toString());
	}
}
