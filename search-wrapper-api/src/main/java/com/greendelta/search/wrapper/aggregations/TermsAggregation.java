package com.greendelta.search.wrapper.aggregations;

import com.greendelta.search.wrapper.SearchFilterType;

public class TermsAggregation extends SearchAggregation {

	public final static SearchFilterType TYPE = SearchFilterType.TERM;

	public TermsAggregation(String field) {
		super(field, TYPE, field);
	}

	public TermsAggregation(String name, String field) {
		super(name, TYPE, field);
	}

}
