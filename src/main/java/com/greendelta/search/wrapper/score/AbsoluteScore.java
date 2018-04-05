package com.greendelta.search.wrapper.score;

public class AbsoluteScore extends AbstractScore {

	public final String field;

	public AbsoluteScore(String field) {
		this.field = field;
	}

	public AbsoluteScore addCase(double weight, String comparator, Object value) {
		return (AbsoluteScore) addCase(field, weight, comparator, value);
	}

	public AbsoluteScore addCase(double weight, String lowerComparator, double lowerValue, String upperComparator,
			double upperValue) {
		return (AbsoluteScore) addCase(field, weight, lowerComparator, lowerValue, upperComparator, upperValue);
	}
}
