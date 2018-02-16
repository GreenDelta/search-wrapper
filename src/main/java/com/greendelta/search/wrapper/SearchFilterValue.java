package com.greendelta.search.wrapper;

import java.util.Collection;

public class SearchFilterValue {

	public final Object value;
	public final Type type;

	private SearchFilterValue(Object value, Type type) {
		this.value = value;
		this.type = type;
	}

	public static SearchFilterValue phrase(String value) {
		return new SearchFilterValue(value, Type.PHRASE);
	}

	public static SearchFilterValue phrase(Collection<String> values) {
		return new SearchFilterValue(values, Type.PHRASE);
	}

	public static SearchFilterValue wildcard(String value) {
		return new SearchFilterValue(value, Type.WILDCART);
	}

	public static SearchFilterValue from(Object from) {
		return new SearchFilterValue(new Object[] { from, null }, Type.RANGE);
	}

	public static SearchFilterValue to(Object to) {
		return new SearchFilterValue(new Object[] { null, to }, Type.RANGE);
	}

	public static SearchFilterValue range(Object from, Object to) {
		return new SearchFilterValue(new Object[] { from, to }, Type.RANGE);
	}

	public static SearchFilterValue term(Object value) {
		return new SearchFilterValue(value, Type.TERM);
	}

	@Override
	public String toString() {
		String s = "{";
		s += "type: " + type.name() + ", ";
		s += "value: " + value;
		return s + "}";
	}

	public static enum Type {

		PHRASE, WILDCART, RANGE, TERM;

	}

}
