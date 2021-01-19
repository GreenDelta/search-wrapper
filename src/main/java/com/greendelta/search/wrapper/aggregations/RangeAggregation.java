package com.greendelta.search.wrapper.aggregations;

public class RangeAggregation extends SearchAggregation {

	public final static String TYPE = "RANGE";
	public final Double[][] ranges;	
	
	public RangeAggregation(String field, Double[][] ranges) {
		super(field, TYPE, field);
		this.ranges = ranges;
	}

	public RangeAggregation(String name, String field, Double[][] ranges) {
		super(name, TYPE, field);
		this.ranges = ranges;
	}
		
}
