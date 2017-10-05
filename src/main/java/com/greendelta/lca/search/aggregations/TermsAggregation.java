package com.greendelta.lca.search.aggregations;

public class TermsAggregation extends SearchAggregation {

	public final static String TYPE = "TERM";

	public TermsAggregation(String field) {
		super(field, TYPE, field);
	}

	public TermsAggregation(String name, String field) {
		super(name, TYPE, field);
	}

}
