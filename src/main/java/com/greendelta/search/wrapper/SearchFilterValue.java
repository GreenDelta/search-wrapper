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

	public static SearchFilterValue from(Number value) {
		return new SearchFilterValue(value, Type.FROM);
	}

	public static SearchFilterValue to(Number value) {
		return new SearchFilterValue(value, Type.TO);
	}

	public static SearchFilterValue is(Number value) {
		return new SearchFilterValue(value, Type.PHRASE);
	}

	public static enum Type {

		PHRASE, WILDCART, TO, FROM;

	}

}
