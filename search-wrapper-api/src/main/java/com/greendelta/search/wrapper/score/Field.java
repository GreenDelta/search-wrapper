package com.greendelta.search.wrapper.score;

public class Field {
	
	public final String name;
	public final Object value;
	public final Double lowerLimit;
	public final Double upperLimit;
	
	public Field(String field, Object value) {
		this.name = field;
		this.value = value;
		this.lowerLimit = null;
		this.upperLimit = null;
	}

	public Field(String field, Double value, Double lowerLimit, Double upperLimit) {
		this.name = field;
		this.value = value;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}
	
}