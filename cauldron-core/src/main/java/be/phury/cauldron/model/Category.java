package be.phury.cauldron.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Category extends Model {

	private String name;

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
		return Collections.unmodifiableMap(m);
	}
}
