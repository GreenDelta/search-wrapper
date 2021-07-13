package com.greendelta.search.wrapper.aggregations.results;

import java.util.ArrayList;
import java.util.List;

import com.greendelta.search.wrapper.SearchFilterType;

public class AggregationResultBuilder {

	private String name;
	private SearchFilterType type;
	private long totalCount;
	private List<AggregationResultEntry> entries = new ArrayList<>();
	
	public AggregationResultBuilder name(String name) {
		this.name = name;
		return this;
	}

	public AggregationResultBuilder type(SearchFilterType type) {
		this.type = type;
		return this;
	}
	
	public AggregationResultBuilder totalCount(long totalCount) {
		this.totalCount = totalCount;
		return this;
	}
	
	public AggregationResultBuilder addEntry(AggregationResultEntry entry) {
		this.entries.add(entry);
		return this;
	}
	
	public AggregationResult build() {
		return new AggregationResult(name, type, totalCount, entries);
	}

}
