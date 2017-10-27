package com.greendelta.search.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.greendelta.search.wrapper.aggregations.SearchAggregation;

public class SearchQuery {

	public final static int DEFAULT_PAGE_SIZE = 10;
	private final Set<SearchAggregation> aggregations;
	private final List<SearchFilter> filters = new ArrayList<>();
	private final List<MultiSearchFilter> multiFilters = new ArrayList<>();
	private final Map<String, SearchSorting> sortBy = new HashMap<>();
	private String query;
	private int page;
	private int pageSize;

	SearchQuery(Set<SearchAggregation> aggregations) {
		this.aggregations = aggregations != null ? aggregations : new HashSet<>();
	}

	void addFilter(String field, Set<SearchFilterValue> values, Conjunction type) {
		SearchFilter filter = null;
		for (SearchFilter f : filters) {
			if (f.field.equals(field)) {
				filter = f;
				break;
			}
		}
		if (filter == null) {
			filters.add(filter = new SearchFilter(field, values, type));
		} else {
			filter.values.addAll(values);
		}
	}
	
	void addFilter(MultiSearchFilter filter) {
		this.multiFilters.add(filter);
	}

	void setSortBy(Map<String, SearchSorting> sortBy) {
		this.sortBy.clear();
		this.sortBy.putAll(sortBy);
	}

	void setQuery(String query) {
		this.query = query;
	}

	public Set<SearchAggregation> getAggregations() {
		return aggregations;
	}

	public SearchAggregation getAggregation(String name) {
		for (SearchAggregation aggregation : aggregations)
			if (aggregation.name.equals(name))
				return aggregation;
		return null;
	}

	public boolean hasAggregation(String name) {
		return getAggregation(name) != null;
	}

	public List<SearchFilter> getFilters() {
		return filters;
	}

	public List<MultiSearchFilter> getMultiFilters() {
		return multiFilters;
	}

	public Map<String, SearchSorting> getSortBy() {
		return sortBy;
	}

	public int getPage() {
		return page;
	}

	void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isPaged() {
		return page > 0;
	}

	@Override
	public String toString() {
		String s = "{page=" + page + ", ";
		s += "pageSize=" + pageSize + ", ";
		s += "query=" + (query != null ? query : "");
		s += "aggregations=" + joinFilters(true) + ", ";
		s += "filters=" + joinFilters(false) + ", ";
		s += "multiFilters=" + joinMultiFilters() + ", ";
		return s + "sortBy=" + joinSortBy() + "}";
	}

	private String joinSortBy() {
		String s = "[";
		int i = 0;
		for (Entry<String, SearchSorting> entry : sortBy.entrySet()) {
			s += entry.getKey() + "=" + entry.getValue();
			i++;
			if (i < sortBy.size()) {
				s += ", ";
			}
		}
		return s + "]";
	}

	private String joinFilters(boolean aggregations) {
		String s = "[";
		int i = 0;
		for (SearchFilter filter : filters) {
			if (hasAggregation(filter.field) != aggregations)
				continue;
			s += filter.toString();
			i++;
			if (i < filters.size()) {
				s += ", ";
			}
		}
		return s + "]";
	}

	private String joinMultiFilters() {
		String s = "[";
		int i = 0;
		for (MultiSearchFilter filter : multiFilters) {
			s += filter.toString();
			i++;
			if (i < multiFilters.size()) {
				s += ", ";
			}
		}
		return s + "]";
	}

}
