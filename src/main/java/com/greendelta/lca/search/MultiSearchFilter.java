package com.greendelta.lca.search;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MultiSearchFilter {

	public final Set<String> fields;
	public final Set<SearchFilterValue> values;
	public final Conjunction conjunction;

	public MultiSearchFilter(Set<String> fields) {
		this(fields, null, null);
	}

	public MultiSearchFilter(Set<String> fields, SearchFilterValue value) {
		this(fields, toSet(value), null);
	}

	public MultiSearchFilter(Set<String> fields, Set<SearchFilterValue> values) {
		this(fields, values, null);

	}

	public MultiSearchFilter(Set<String> fields, Set<SearchFilterValue> values, Conjunction conjunction) {
		if (fields == null || fields.isEmpty())
			throw new IllegalArgumentException("Fields can not be empty");
		this.fields = Collections.unmodifiableSet(fields);
		this.values = values == null ? new HashSet<>() : values;
		this.conjunction = conjunction == null ? Conjunction.OR : conjunction;
	}

	@Override
	public String toString() {
		String s = "{";
		String[] fields = this.fields.toArray(new String[this.fields.size()]);
		for (int i = 0; i < fields.length; i++) {
			s += fields[i] + "=";
			for (int j = 0; j < fields.length; j++) {
				s += fields[j];
				if (j < fields.length - 1) {
					s += " AND ";
				}
			}
		}
		s += "=";
		if (values.size() == 0)
			return s + "}";
		if (values.size() == 1)
			return s + values.iterator().next().value + "}";
		String[] values = this.values.toArray(new String[this.values.size()]);
		for (int i = 0; i < values.length; i++) {
			s += values[i] + "=";
			for (int j = 0; j < values.length; j++) {
				s += values[j];
				if (j < values.length - 1) {
					s += " " + conjunction.name() + " ";
				}
			}
		}
		return s + "}";
	}

	private static <T> Set<T> toSet(T value) {
		Set<T> set = new HashSet<>();
		set.add(value);
		return set;
	}

}
