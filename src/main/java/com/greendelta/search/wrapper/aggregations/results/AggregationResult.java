package com.greendelta.search.wrapper.aggregations.results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		var s = "{name=" + name + ", ";
		s += "type=" + type + ", ";
		s += "totalCount=" + totalCount + ", ";
		s += "entries=[";
		if (entries != null) {
			var i = 0;
			for (var e : entries) {
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
		var all = new HashMap<String, AggregationResultEntry>();
		for (var entry : entries) {
			all.put(entry.key, entry);
		}
		var topLevel = new ArrayList<AggregationResultEntry>();
		for (var entry : entries) {
			var split = doSplit.apply(entry.key);
			var depth = 0;
			var parent = "";
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
		var joined = "";
		for (var index = 0; index < array.length; index++) {
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
