package com.greendelta.lca.search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.greendelta.lca.search.SearchFilterValue.Type;
import com.greendelta.lca.search.aggregations.SearchAggregation;

public class SearchQueryBuilder {

	private String query;
	private int page = 0;
	private int pageSize = SearchQuery.DEFAULT_PAGE_SIZE;
	private Map<String, SearchFilter> filters = new HashMap<>();
	private Set<MultiSearchFilter> multiFilters = new HashSet<>();
	private Set<SearchAggregation> aggregations = new HashSet<>();
	private Map<String, SearchSorting> sortBy = new HashMap<>();

	public SearchQueryBuilder query(String query, String queryField) {
		if (query == null || query.isEmpty() || queryField == null || queryField.isEmpty())
			return this;
		this.query = query;
		filter(queryField, split(query));
		return this;
	}

	public SearchQueryBuilder query(String query, String[] queryFields) {
		if (query == null || query.isEmpty() || queryFields == null || queryFields.length == 0)
			return this;
		if (queryFields.length == 1)
			return query(query, queryFields[0]);
		this.query = query;
		filter(queryFields, split(query));
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

	public SearchQueryBuilder aggregation(SearchAggregation aggregation, String... values) {
		if (aggregation == null)
			return this;
		if (!hasAggregation(aggregation.name)) {
			this.aggregations.add(aggregation);
		}
		if (values == null)
			return this;
		filter(aggregation.name, values, Type.PHRASE);
		return this;
	}

	private boolean hasAggregation(String name) {
		for (SearchAggregation aggregation : aggregations)
			if (aggregation.name.equals(name))
				return true;
		return false;
	}

	public SearchQueryBuilder filter(String field, String value, Type type) {
		return filter(field, new String[] {value}, type);
	}

	public SearchQueryBuilder filter(String field, String[] values, Type type) {
		if (field == null || values == null || values.length == 0)
			return this;
		Set<SearchFilterValue> filterValues = new HashSet<>();
		for (String value : values) {
			filterValues.add(new SearchFilterValue(value, type));
		}
		filter(field, filterValues);
		return this;
	}

	private SearchQueryBuilder filter(String field, Set<SearchFilterValue> values) {
		if (field == null || values == null || values.size() == 0)
			return this;
		SearchFilter filter = this.filters.get(field);
		if (filter == null) {
			this.filters.put(field, filter = new SearchFilter(field, values));
		} else {
			filter.values.addAll(values);
		}
		return this;
	}

	public SearchQueryBuilder filter(String[] fields, String value, Type type) {
		return filter(fields, new String[] {value}, type);
	}

	public SearchQueryBuilder filter(String[] fields, String[] values, Type type) {
		if (fields == null || fields.length == 0 || values == null || values.length == 0)
			return this;
		Set<SearchFilterValue> filterValues = new HashSet<>();
		for (String value : values) {
			filterValues.add(new SearchFilterValue(value, type));
		}
		filter(fields, filterValues);
		return this;
	}

	private SearchQueryBuilder filter(String[] fields, Set<SearchFilterValue> values) {
		if (fields == null || fields.length == 0 || values == null || values.size() == 0)
			return this;
		Set<String> fieldSet = new HashSet<>();
		for (String field : fields) {
			fieldSet.add(field);
		}
		multiFilters.add(new MultiSearchFilter(fieldSet, values));
		return this;
	}

	public SearchQueryBuilder sortBy(String field, SearchSorting order) {
		if (field == null || order == null)
			return null;
		this.sortBy.put(field, order);
		return this;
	}

	public SearchQuery build() {
		SearchQuery searchQuery = new SearchQuery(aggregations);
		if (page > 0) {
			searchQuery.setPage(page);
			searchQuery.setPageSize(pageSize);
		}
		if (query != null) {
			searchQuery.setQuery(query);
		}
		for (SearchFilter filter : filters.values()) {
			searchQuery.addFilter(filter.field, filter.values, filter.conjunction);
		}
		for (MultiSearchFilter filter : multiFilters) {
			searchQuery.addFilter(filter);
		}
		searchQuery.setSortBy(sortBy);
		return searchQuery;
	}

	private static Set<SearchFilterValue> split(String query) {
		Set<SearchFilterValue> splitted = new HashSet<>();
		StringTokenizer splitter = new StringTokenizer(query, "\"", true);
		boolean escaped = false;
		while (splitter.hasMoreTokens()) {
			String token = splitter.nextToken();
			if ("\"".equals(token)) {
				escaped = !escaped;
			} else if (escaped) {
				splitted.add(new SearchFilterValue(token, Type.PHRASE));
			} else {
				token = token.replace("@", " ");
				for (String word : token.trim().split("\\s+")) {
					splitted.add(new SearchFilterValue(word, Type.PHRASE));
				}
			}
		}
		return splitted;
	}

}
