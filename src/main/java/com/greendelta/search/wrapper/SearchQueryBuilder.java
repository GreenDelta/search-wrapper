package com.greendelta.search.wrapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.greendelta.search.wrapper.aggregations.SearchAggregation;
import com.greendelta.search.wrapper.score.Score;

public class SearchQueryBuilder {

	private int page = 0;
	private int pageSize = SearchQuery.DEFAULT_PAGE_SIZE;
	private Map<String, SearchFilter> filters = new HashMap<>();
	private Set<MultiSearchFilter> multiFilters = new HashSet<>();
	private Set<SearchAggregation> aggregations = new HashSet<>();
	private Set<LinearDecayFunction> functions = new HashSet<>();
	private Set<Score> scores = new HashSet<>();
	private Map<String, SearchSorting> sortBy = new HashMap<>();
	private boolean fullResult = true;

	public SearchQueryBuilder query(String query, String queryField) {
		return query(query, queryField, null);
	}

	public SearchQueryBuilder query(String query, String queryField, Conjunction conjunction) {
		if (query == null || query.isEmpty() || queryField == null || queryField.isEmpty())
			return this;
		filter(queryField, split(query), conjunction);
		return this;
	}

	public SearchQueryBuilder query(String query, String[] queryFields) {
		return query(query, queryFields, null);
	}

	public SearchQueryBuilder query(String query, String[] queryFields, Conjunction conjunction) {
		if (query == null || query.isEmpty() || queryFields == null || queryFields.length == 0)
			return this;
		if (queryFields.length == 1)
			return query(query, queryFields[0], conjunction);
		filter(queryFields, split(query), conjunction);
		return this;
	}

	public SearchQueryBuilder page(int page) {
		this.page = page;
		return this;
	}

	public SearchQueryBuilder pageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public SearchQueryBuilder fullResult(boolean value) {
		this.fullResult = value;
		return this;
	}

	public SearchQueryBuilder aggregation(SearchAggregation aggregation, String... values) {
		if (values == null || values.length == 0)
			return aggregation(aggregation, new HashSet<>());
		return aggregation(aggregation, SearchFilterValue.term(Arrays.asList(values)));
	}

	public SearchQueryBuilder aggregation(SearchAggregation aggregation, SearchFilterValue value) {
		return aggregation(aggregation, Collections.singleton(value));
	}

	public SearchQueryBuilder aggregation(SearchAggregation aggregation, Set<SearchFilterValue> values) {
		if (aggregation == null)
			return this;
		if (!hasAggregation(aggregation.name)) {
			this.aggregations.add(aggregation);
		}
		if (values == null || values.size() == 0)
			return this;
		filter(aggregation.field, values);
		return this;
	}

	private boolean hasAggregation(String name) {
		for (SearchAggregation aggregation : aggregations)
			if (aggregation.name.equals(name))
				return true;
		return false;
	}

	public SearchQueryBuilder filter(String field, SearchFilterValue value) {
		return filter(field, new HashSet<>(Collections.singleton(value)));
	}

	public SearchQueryBuilder filter(String field, Set<SearchFilterValue> values) {
		return filter(field, values, null);
	}

	public SearchQueryBuilder filter(String field, Set<SearchFilterValue> values, Conjunction conjunction) {
		if (field == null || values == null || values.size() == 0)
			return this;
		SearchFilter filter = this.filters.get(field);
		if (filter == null) {
			this.filters.put(field, filter = new SearchFilter(field, values, conjunction));
		} else {
			filter.values.addAll(values);
		}
		return this;
	}

	public SearchQueryBuilder filter(String[] fields, SearchFilterValue value) {
		return filter(fields, new HashSet<>(Collections.singleton(value)), null);
	}

	public SearchQueryBuilder filter(String[] fields, Set<SearchFilterValue> values, Conjunction conjunction) {
		if (fields == null || fields.length == 0 || values == null || values.size() == 0)
			return this;
		Set<String> fieldSet = new HashSet<>();
		for (String field : fields) {
			fieldSet.add(field);
		}
		multiFilters.add(new MultiSearchFilter(fieldSet, values, conjunction));
		return this;
	}

	public SearchQueryBuilder score(Score score) {
		this.scores.add(score);
		return this;
	}
	
	public SearchQueryBuilder score(LinearDecayFunction function) {
		this.functions.add(function);
		return this;
	}

	public SearchQueryBuilder sortBy(String field, SearchSorting order) {
		if (field == null || order == null)
			return this;
		this.sortBy.put(field, order);
		return this;
	}

	public SearchQuery build() {
		SearchQuery searchQuery = new SearchQuery(aggregations);
		if (page > 0) {
			searchQuery.setPage(page);
			searchQuery.setPageSize(pageSize);
		}
		for (SearchFilter filter : filters.values()) {
			searchQuery.addFilter(filter.field, filter.values, filter.conjunction);
		}
		for (MultiSearchFilter filter : multiFilters) {
			searchQuery.addFilter(filter);
		}
		for (Score score : scores) {
			searchQuery.addScore(score);
		}
		for (LinearDecayFunction function : functions) {
			searchQuery.addScore(function);
		}
		searchQuery.setFullResult(fullResult);
		searchQuery.setSortBy(sortBy);
		return searchQuery;
	}

	private Set<SearchFilterValue> split(String query) {
		Set<SearchFilterValue> splitted = new HashSet<>();
		StringTokenizer splitter = new StringTokenizer(query, "\"", true);
		boolean escaped = false;
		while (splitter.hasMoreTokens()) {
			String token = splitter.nextToken();
			if ("\"".equals(token)) {
				escaped = !escaped;
			} else if (escaped) {
				splitted.add(SearchFilterValue.phrase(token));
			} else {
				token = token.replace("@", " ");
				for (String word : token.trim().split("\\s+")) {
					splitted.add(SearchFilterValue.wildcard(word));
				}
			}
		}
		return splitted;
	}

}
