package be.phury.cauldron.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class Model {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		List<String> fields = new LinkedList<String>();
		for (Map.Entry<String, Object> e : getSignificantFields().entrySet()) {
			fields.add(e.getKey()+"="+e.getValue());
		}
		return "{"+StringUtils.join(fields, ", ")+"}";
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	protected abstract Map<String, Object> getSignificantFields();
}
