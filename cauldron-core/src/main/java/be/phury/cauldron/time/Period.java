package be.phury.cauldron.time;

import java.util.Date;

public class Period {

	private final Date start;
	private final Date end;
	
	public Period(Date start, Date end) {
		this.start = start;
		this.end = end;
	}
	public Date getStart() {
		return start;
	}
	public Date getEnd() {
		return end;
	}
	public boolean contains(Date date) {
		return (start.before(date) || start.equals(date)) && (end.after(date) || end.equals(date));
	}
	public int getNumberOfWeeks() {
		long diff = getEnd().getTime() - getStart().getTime();
        float dayCount = (float) diff / (24 * 60 * 60 * 1000);
        return (int) (dayCount / 7) ;
	}
}
