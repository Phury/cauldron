package be.phury.cauldron.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Budget extends Model {

	private Money amount;
	private List<Category> categories;
	private String name;
	
	public Money getAmount() {
		return amount;
	}
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected Map<String, Object> getSignificantFields() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("id", getId());
		m.put("name", getName());
		m.put("categories", "["+StringUtils.join(getCategories(), ", ")+"]");
		return Collections.unmodifiableMap(m);
	}
}
