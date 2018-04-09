package com.greendelta.search.wrapper.score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Implementing scripts are expected to define the following methods:<br>
 * <b>substring</b>: String substring(String value, int from, in to)<br>
 * <b>indexOf</b>: int indexOf(String value, String phrase)<br>
 * <b>lastIndexOf</b>: int indexOf(String value, String phrase)<br>
 * <b>abs</b>: double abs(double value)<br>
 * <b>min</b>: double min(double value1, double value2)<br>
 * <b>getDistance</b>: double getDistance(double lat1, double lon1, double lat2,
 * double lon2)
 */
public class Score {

	private List<Case> cases = new ArrayList<>();
	public final List<Field> fields;

	public Score(String field) {
		this(field, null);
	}

	public Score(String field, Object value) {
		this(new Field(field, value));
	}

	public Score(String field, Double value, Double lowerLimit, Double upperLimit) {
		this(new Field(field, value, lowerLimit, upperLimit));
	}

	public Score(Field... fields) {
		this.fields = Arrays.asList(fields);
	}

	public Score addCase(double weight, Comparator comparator, Object value) {
		return addCase(weight, "fieldValues[0]", comparator, value);
	}

	public Score addCase(double weight, String field, Comparator comparator, Object value) {
		return addCase(weight, new Condition(field, comparator, value));
	}

	public Score addCase(double weight, Comparator lowerComparator, Object lowerValue,
			Comparator upperComparator, Object upperValue) {
		return addCase(weight, "fieldValues[0]", lowerComparator, lowerValue, upperComparator, upperValue);
	}

	public Score addCase(double weight, String field, Comparator lowerComparator, Object lowerValue,
			Comparator upperComparator, Object upperValue) {
		return addCase(weight, new Condition(field, lowerComparator, lowerValue),
				new Condition(field, upperComparator, upperValue));
	}

	public Score addCase(double weight, Condition... conditions) {
		cases.add(new Case(weight, conditions));
		return this;
	}

	public Score addElse(double weight) {
		cases.add(new Case(weight));
		// else case is the last case -> make list unmodifiable
		cases = Collections.unmodifiableList(cases);
		return this;
	}

	public Case[] getCases() {
		return cases.toArray(new Case[cases.size()]);
	}

	public double getDefaultWeight() {
		if (cases.isEmpty())
			return 1;
		Case lastCase = cases.get(cases.size() - 1);
		if (lastCase.conditions.isEmpty())
			return lastCase.weight;
		return 1;
	}

}
