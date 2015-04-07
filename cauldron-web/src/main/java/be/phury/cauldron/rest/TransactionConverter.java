package be.phury.cauldron.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import be.phury.cauldron.CauldronService;
import be.phury.cauldron.model.Money;
import be.phury.cauldron.model.Transaction;
import be.phury.cauldron.model.TransactionType;
import ch.lambdaj.function.convert.Converter;

@Singleton
public class TransactionConverter implements Converter<TransactionReq, Transaction> {

	private CauldronService cauldronService;
	
	@Inject
	public TransactionConverter(CauldronService cauldronService) {
		this.cauldronService = cauldronService;
	}
	
	
	@Override
	public Transaction convert(TransactionReq from) {
		Transaction t = new Transaction();
		t.setAccount(cauldronService.findAccountByName(from.getAccount()));
		t.setAmount(new Money((int)(Double.parseDouble(from.getAmount())*100), "CAD$"));
		t.setCategories(cauldronService.findAllCategoriesByName(Arrays.asList(from.getCategories().split(", "))));
		t.setEntryDate(parse(from.getEntryDate(), "dd-MM-yyyy"));
		t.setLabel(from.getLabel());
		t.setTransactionType(TransactionType.valueOf(from.getTransactionType()));
		return t;
	}

	private Date parse(String str, String format) {
		try {
			return new SimpleDateFormat(format).parse(str);
		} catch (ParseException e) {
			// TODO: log e
			return new Date();
		}
	}
}
