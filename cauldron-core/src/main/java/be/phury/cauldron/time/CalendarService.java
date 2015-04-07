package be.phury.cauldron.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.google.inject.Singleton;

@Singleton
public class CalendarService {

	private static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy";
	private Date date;
	private String dateFormat;
	
	public CalendarService() {
		this(null);
	}

	public CalendarService(Date date) {
		setDate(date);
		setDateFormat(DEFAULT_DATE_FORMAT);
	}
	
	public Date today() {
		return date == null ? new Date() : date;
	}
	
	public Date firstOfMonth() {
		Calendar c = new GregorianCalendar();
		c.setTime(today());
	    c.set(Calendar.DAY_OF_MONTH, 1);
	    return c.getTime();
	}
	
	private void setDate(Date date) {
		this.date = date;
	}
	
	private void setDate(String format, String date) {
		try {
			setDate(new SimpleDateFormat(format).parse(date));
		} catch (ParseException e) {
			throw new IllegalArgumentException("the date ["+date+"] could not be parsed");
		}
	}
	
	public void setDate(String date) {
		setDate(getDateFormat(), date);
	}
	
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	public String getDateFormat() {
		return dateFormat;
	}
}
