package com.greendelta.search.wrapper;

import java.util.Collection;

public class SearchFilterValue {

	public final Object value;
	public final SearchFilterType type;
	public final Float boost;

	private SearchFilterValue(Object value, SearchFilterType type, Float boost) {
		this.value = value;
		this.type = type;
		this.boost = boost;
	}

	public static SearchFilterValue phrase(String value) {
		return phrase(value, null);
	}

	public static SearchFilterValue phrase(String value, Float boost) {
		return new SearchFilterValue(value, SearchFilterType.PHRASE, boost);
	}

	public static SearchFilterValue phrase(Collection<String> values) {
		return phrase(values, null);
	}

	public static SearchFilterValue phrase(Collection<String> values, Float boost) {
		return new SearchFilterValue(values, SearchFilterType.PHRASE, boost);
	}

	public static SearchFilterValue wildcard(String value) {
		return wildcard(value, null);
	}

	public static SearchFilterValue wildcard(String value, Float boost) {
		return new SearchFilterValue(value, SearchFilterType.WILDCART, boost);
	}

	public static SearchFilterValue from(Object from) {
		return from(from, null);
	}

	public static SearchFilterValue from(Object from, Float boost) {
		return new SearchFilterValue(new Object[] { from, null }, SearchFilterType.RANGE, boost);
	}

	public static SearchFilterValue to(Object to) {
		return to(to, null);
	}

	public static SearchFilterValue to(Object to, Float boost) {
		return new SearchFilterValue(new Object[] { null, to }, SearchFilterType.RANGE, boost);
	}

	public static SearchFilterValue range(Object from, Object to) {
		return range(from, to, null);
	}

	public static SearchFilterValue range(Object from, Object to, Float boost) {
		return new SearchFilterValue(new Object[] { from, to }, SearchFilterType.RANGE, boost);
	}

	public static SearchFilterValue term(Object value) {
		return term(value, null);
	}

	public static SearchFilterValue term(Object value, Float boost) {
		return new SearchFilterValue(value, SearchFilterType.TERM, boost);
	}

	@Override
	public String toString() {
		String s = "{";
		s += "type: " + type.name() + ", ";
		s += "value: " + value;
		return s + "}";
	}

}
