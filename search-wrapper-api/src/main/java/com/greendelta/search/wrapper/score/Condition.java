package com.greendelta.search.wrapper.score;

public class Condition {

	public final Object value1;
	public final Comparator comparator;
	public final Object value2;
	
	public Condition(Object value1, Comparator comparator, Object value2) {
		this.value1 = value1;
		this.comparator = comparator;
		this.value2 = value2;
	}

}