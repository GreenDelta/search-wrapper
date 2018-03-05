package com.greendelta.search.wrapper.aggregations.results;

import java.util.ArrayList;
import java.util.List;


public class AggregationResultEntry {

	public final String key;
	public final long count;
	public final List<AggregationResultEntry> subEntries = new ArrayList<>();

	AggregationResultEntry(String key, long count) {
		this.key = key;
		this.count = count;
	}

	@Override
	public String toString() {
		return key + "=" + count;
	}

}
