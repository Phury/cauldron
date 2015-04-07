package be.phury.cauldron.model;


public class Recurrence {

	private static final Recurrence NONE = new Recurrence();
	private static final Recurrence BI_MONTHLY = new Recurrence();
	
	public static Recurrence none() { return NONE; }
	public static Recurrence biMonthly() { return BI_MONTHLY; }

}
