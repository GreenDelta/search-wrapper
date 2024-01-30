package com.greendelta.search.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.greendelta.search.wrapper.aggregations.SearchAggregation;
import com.greendelta.search.wrapper.score.Score;

public class SearchQuery {

	public final static int DEFAULT_PAGE_SIZE = 10;
	private final Set<SearchAggregation> aggregations;
	private final List<SearchFilter> filters = new ArrayList<>();
	private final List<MultiSearchFilter> multiFilters = new ArrayList<>();
	private final List<Score> scores = new ArrayList<>();
	private final List<LinearDecayFunction> functions = new ArrayList<>();
	private final Map<String, SearchSorting> sortBy = new HashMap<>();
	private final Set<SearchField> fields = new HashSet<>();
	private int page;
	private int pageSize;
	private boolean fullResult;

	SearchQuery(Set<SearchAggregation> aggregations) {
		this.aggregations = aggregations != null ? aggregations : new HashSet<>();
	}
	
	void addField(SearchField field) {
		this.fields.add(field);
	}

	void addFilter(String field, Set<SearchFilterValue> values, Conjunction type) {
		SearchFilter filter = null;
		for (var f : filters) {
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

	void addScore(Score score) {
		this.scores.add(score);
	}

	void addScore(LinearDecayFunction function) {
		this.functions.add(function);
	}

	public Set<SearchAggregation> getAggregations() {
		return aggregations;
	}

	public List<SearchAggregation> getAggregationsByField(String field) {
		return aggregations.stream()
				.filter(aggregation -> aggregation.field.equals(field))
				.collect(Collectors.toList());
	}

	public List<SearchFilter> getFilters() {
		return filters;
	}

	public List<MultiSearchFilter> getMultiFilters() {
		return multiFilters;
	}

	public List<Score> getScores() {
		return scores;
	}

	public List<LinearDecayFunction> getFunctions() {
		return functions;
	}

	public Map<String, SearchSorting> getSortBy() {
		return sortBy;
	}

	public Set<SearchField> getFields() {
		return fields;
	}

	public int getPage() {
		return page;
	}

	public boolean getFullResult() {
		return fullResult;
	}

	void setFullResult(boolean value) {
		this.fullResult = value;
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

}
