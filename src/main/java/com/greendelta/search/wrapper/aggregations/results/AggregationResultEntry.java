package com.greendelta.search.wrapper.aggregations.results;


public class AggregationResultEntry {

	public final String key;
	public final long count;

	AggregationResultEntry(String key, long count) {
		this.key = key;
		this.count = count;
	}

	@Override
	public String toString() {
		return key + "=" + count;
	}

}
