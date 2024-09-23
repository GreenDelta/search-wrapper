package com.greendelta.search.wrapper;

import java.util.HashSet;
import java.util.Set;

public class SearchFilter {

	public final String field;
	public final Set<SearchFilterValue> values;
	public final Conjunction conjunction;

	public SearchFilter(String field) {
		this(field, new HashSet<>());
	}

	public SearchFilter(String field, SearchFilterValue value) {
		this(field, toSet(value), null);
	}

	public SearchFilter(String field, Set<SearchFilterValue> values) {
		this(field, values, null);
	}

	public SearchFilter(String field, Set<SearchFilterValue> values, Conjunction conjunction) {
		this.field = field;
		this.values = values == null ? new HashSet<>() : new HashSet<>(values);
		this.conjunction = conjunction == null ? Conjunction.OR : conjunction;
	}

	@Override
	public String toString() {
		var s = "{" + field + "=";
		if (values.size() == 0)
			return s + "}";
		if (values.size() == 1)
			return s + values.iterator().next().value + "}";
		var values = this.values.toArray(new SearchFilterValue[this.values.size()]);
		for (var i = 0; i < values.length; i++) {
			s += values[i];
			if (i < values.length - 1) {
				s += " " + conjunction.name() + " ";
			}
		}
		return s + "}";
	}

	private static Set<SearchFilterValue> toSet(SearchFilterValue value) {
		var set = new HashSet<SearchFilterValue>();
		set.add(value);
		return set;
	}

}
