package com.greendelta.search.wrapper.aggregations.results;

import java.util.ArrayList;
import java.util.List;


public class AggregationResultEntry {

	public final String key;
	public final long count;
	public final Object data;
	public final List<AggregationResultEntry> subEntries = new ArrayList<>();

	public AggregationResultEntry(String key, long count) {
		this(key, count, key);
	}

	public  AggregationResultEntry(String key, long count, Object data) {
		this.key = key;
		this.count = count;
		this.data = data;
	}

	@Override
	public String toString() {
		return key + "=" + count;
	}

}
