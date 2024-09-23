package com.greendelta.search.wrapper.aggregations;

import com.greendelta.search.wrapper.SearchFilterType;

public abstract class SearchAggregation {
	
	public final String name;
	public final SearchFilterType type;
	public final String field;

	protected SearchAggregation(String name, SearchFilterType type, String field) {
		this.name = name;
		this.type = type;
		this.field = field;
	}
	
}
