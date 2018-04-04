package com.greendelta.search.wrapper.score;

public class Condition {

	public final String field;
	public final String comparator;
	public final Object limit;
	public final String otherField;

	public Condition(String field, String comparator, String otherField) {
		this.field = field;
		this.comparator = comparator;
		this.otherField = otherField;
		this.limit = null;
	}

	public Condition(String field, String comparator, Object limit) {
		this.field = field;
		this.comparator = comparator;
		this.limit = limit;
		this.otherField = null;
	}

}