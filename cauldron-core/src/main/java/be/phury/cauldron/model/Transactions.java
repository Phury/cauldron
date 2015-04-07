//package be.phury.cauldron.model;
//
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//
//public class Transactions {
//	
//	public static Transaction createTransaction(Transaction t) {
//		return create(new Transaction(), t.getLabel(), t.getMoney(), t.getCategories(), t.getEntryDate());
//	}
//
//	public static Transaction createTransaction(String label, Money money, List<Category> categories, Date today) {
//		return create(new Transaction(), label, money, categories, today);
//	}
//	
//	public static Expense createExpense(String label, Money money, List<Category> categories, Date entryDate) {
//		return create(new Expense(), label, money, categories, entryDate);
//	}
//	
//	public static Income createIncome(String label, Money money, List<Category> categories, Date entryDate) {
//		return create(new Income(), label, money, categories, entryDate);
//	}
//	
//	public static BiMonthlyTransaction createBiMonthlyTransaction(Transaction t) {
//		return new BiMonthlyTransaction(t);
//	}
//	
//	private static <T extends Transaction> T create(T t, String label, Money money, List<Category> categories, Date entryDate) {
//		t.setId(UUID.randomUUID().toString());
//		t.setLabel(label);
//		t.setMoney(money);
//		t.setCategories(categories);
//		t.setEntryDate(entryDate);
//		return t;
//	}
//	
//	/*
//	public Transaction(Transaction t) {
//		this(t.id, t.getLabel(), t.getMoney(), t.getCategories(), t.getEntryDate());
//	}
//	
//	private Transaction(String id, String label, Money money, List<Category> categories, Date entryDate) {
//		this.id = id;
//		this.label = label;
//		this.money = money;
//		this.categories = categories;
//		this.entryDate = entryDate;
//	}
//	
//	public Transaction(String label, Money money, List<Category> categories, Date entryDate) {
//		this(UUID.randomUUID().toString(), label, money, categories, entryDate);
//	}
//	*/
//}
