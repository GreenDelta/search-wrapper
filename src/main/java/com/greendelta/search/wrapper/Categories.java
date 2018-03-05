package com.greendelta.search.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greendelta.search.wrapper.aggregations.results.AggregationResult;
import com.greendelta.search.wrapper.aggregations.results.AggregationResultEntry;

public class Categories {

	public final String category;
	public final List<String> categories;
	public final List<String> categoryPaths;

	public Categories(List<String> categories) {
		this.categories = categories != null ? new ArrayList<>(categories) : null;
		if (categories == null || categories.size() == 0) {
			this.category = null;
			this.categoryPaths = null;
			return;
		}
		String category = "";
		for (String cat : categories) {
			if (!category.isEmpty()) {
				category += '/';
			}
			category += cat;
		}
		this.category = category;
		categoryPaths = new ArrayList<>();
		String current = null;
		for (String cat : categories) {
			if (current == null) {
				current = cat;
			} else {
				current += '/' + cat;
			}
			categoryPaths.add(current);
		}
	}

	public static void fillUp(Map<String, Object> map, List<String> categories) {
		Categories info = new Categories(categories);
		if (!map.containsKey("categories")) {
			map.put("categories", info.categories);
		}
		if (!map.containsKey("category")) {
			map.put("category", info.category);
		}
		if (!map.containsKey("categoryPaths")) {
			map.put("categoryPaths", info.categoryPaths);
		}
	}

	public static void groupAggregation(AggregationResult aggregation) {
		Map<String, AggregationResultEntry> all = new HashMap<>();
		for (AggregationResultEntry entry : aggregation.entries) {
			all.put(entry.key, entry);
		}
		List<AggregationResultEntry> topLevel = new ArrayList<>();
		for (AggregationResultEntry entry : aggregation.entries) {
			String[] split = entry.key.split("/");
			int depth = 0;
			String parent = "";
			while (!all.containsKey(parent) && depth < split.length) {
				depth++;
				if (depth == 1 && split.length == 1)
					continue;
				parent = join(split, split.length - depth, false);
			}
			if (!parent.isEmpty() && all.containsKey(parent)) {
				all.get(parent).subEntries.add(entry);
			} else {
				topLevel.add(entry);
			}
		}
		aggregation.entries.clear();
		aggregation.entries.addAll(topLevel);
	}

	private static String join(String[] array, int depth, boolean reverse) {
		String joined = "";
		for (int index = 0; index < array.length; index++) {
			if ((!reverse && index < depth) || (reverse && index >= array.length - depth)) {
				if (!joined.isEmpty()) {
					joined += '/';
				}
				joined += array[index];
			}
		}
		return joined;
	}
}
