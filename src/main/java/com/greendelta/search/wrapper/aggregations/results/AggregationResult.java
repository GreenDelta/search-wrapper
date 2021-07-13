package com.greendelta.search.wrapper.aggregations.results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.greendelta.search.wrapper.SearchFilterType;

public class AggregationResult {

	public final String name;
	public final SearchFilterType type;
	public final long totalCount;
	public final List<AggregationResultEntry> entries;

	AggregationResult(String name, SearchFilterType type, long totalCount, List<AggregationResultEntry> entries) {
		this.name = name;
		this.type = type;
		this.totalCount = totalCount;
		this.entries = entries;
	}

	@Override
	public String toString() {
		String s = "{name=" + name + ", ";
		s += "type=" + type + ", ";
		s += "totalCount=" + totalCount + ", ";
		s += "entries=[";
		if (entries != null) {
			int i = 0;
			for (AggregationResultEntry e : entries) {
				s += e.toString();
				i++;
				if (i < entries.size()) {
					s += ", ";
				}
			}
		}
		return s + "]}";
	}

	public void group(String separator) {
		group(separator, s -> s.split(separator));
	}

	public void group(String separator, Function<String, String[]> doSplit) {
		Map<String, AggregationResultEntry> all = new HashMap<>();
		for (AggregationResultEntry entry : entries) {
			all.put(entry.key, entry);
		}
		List<AggregationResultEntry> topLevel = new ArrayList<>();
		for (AggregationResultEntry entry : entries) {
			String[] split = doSplit.apply(entry.key);
			int depth = 0;
			String parent = "";
			while (!all.containsKey(parent) && depth < split.length) {
				depth++;
				if (depth == 1 && split.length == 1)
					continue;
				parent = join(split, split.length - depth, separator);
			}
			if (!parent.isEmpty() && all.containsKey(parent)) {
				all.get(parent).subEntries.add(entry);
			} else {
				topLevel.add(entry);
			}
		}
		entries.clear();
		entries.addAll(topLevel);
	}

	private String join(String[] array, int depth, String separator) {
		String joined = "";
		for (int index = 0; index < array.length; index++) {
			if (index >= depth)
				break;
			if (!joined.isEmpty() && separator != null) {
				joined += separator;
			}
			joined += array[index];
		}
		return joined;
	}
}
