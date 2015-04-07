package be.phury.cauldron.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import be.phury.cauldron.CauldronService;
import be.phury.cauldron.Transactions;
import be.phury.cauldron.model.Account;
import be.phury.cauldron.model.Category;
import be.phury.cauldron.model.Transaction;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Path("/")
@Singleton
public class CauldronResource {

	private CauldronService cauldronService;
	private TransactionConverter transactionConverter;
	
	@Inject
	public CauldronResource(
			Bootstrap bootstrap, 
			CauldronService cauldronService, 
			TransactionConverter transactionConverter) {
		bootstrap.postConstruct();
		this.cauldronService = cauldronService;
		this.transactionConverter = transactionConverter;
	}

	@GET
	@Path("/expenses")
	@Produces("application/json")
	public List<Transaction> getExpensesInCurrentPeriod() {
		return Transactions.sortByDate(cauldronService.getExpensesInCurrentPeriod());
	}
	
	@GET
	@Path("/incomes")
	@Produces("application/json")
	public List<Transaction> getIncomesInCurrentPeriod() {
		return Transactions.sortByDate(cauldronService.getIncomesInCurrentPeriod());
	}
	
	@GET
	@Path("/categories")
	@Produces("application/json")
	public List<Category> getAllCategories() {
		return cauldronService.getAllCategories();
	}
	
	@GET
	@Path("/accounts")
	@Produces("application/json")
	public List<Account> getAllAccounts() {
		return cauldronService.getAllAccounts();
	}
	
	@POST
	@Path("/transaction")
	@Produces("application/json")
	public void addTransaction(TransactionReq t) {
		cauldronService.addTransaction(transactionConverter.convert(t));
	}
}
