package com.greendelta.lca.search.aggregations.results;



public class TermEntryBuilder {

	private String key;
	private long count;
	
	public TermEntryBuilder key(String key) {
		this.key = key;
		return this;
	}

	public TermEntryBuilder count(long count) {
		this.count = count;
		return this;
	}
	
	public AggregationResultEntry build() {
		return new AggregationResultEntry(key, count);
	}
	
}
