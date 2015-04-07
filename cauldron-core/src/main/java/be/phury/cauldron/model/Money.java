package be.phury.cauldron.model;

import java.text.DecimalFormat;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Money {

	public static final Money ZERO = new Money(0, "CAD$");
	
	private final Integer value;
	private final String currency;
	
	public Money(Integer value, String currency) {
		this.value = value;
		this.currency = currency;
	}

	public Money sum(Money money) {
		checkForSameCurrency(money);
		return new Money(this.value + money.value, this.currency);
	}

	public Money sub(Money money) {
		checkForSameCurrency(money);
		return new Money(this.value - money.value, this.currency);
	}
	
	private void checkForSameCurrency(Money other) {
		if (!this.currency.equals(other.currency)) {
			throw new IllegalArgumentException("the money do not have the same currency: this["+this.currency+"], other["+other.currency+"]");
		}
	}
	
	@Override
	public String toString() {
		return currency + new DecimalFormat("#.00").format(value/100.0);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
