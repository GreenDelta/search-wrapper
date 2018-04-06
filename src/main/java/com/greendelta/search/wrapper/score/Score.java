package com.greendelta.search.wrapper.score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Implementing scripts are expected to define the following variables:<br>
 * <br>
 * <b>fieldClass</b>: The calculated class value for the field value<br>
 * <b>valueClass</b>: The calculated class value for the score value<br>
 * <b>classDifference</b>: The difference between the class value of the field
 * value and score value<br>
 * <br>
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
	public final List<List<Condition>> classes = new ArrayList<>();

	public Score(String field, Object value) {
		this.fields = Collections.singletonList(new Field(field, value));
	}

	public Score(Field... fields) {
		this.fields = Arrays.asList(fields);
	}

	public Score addClass(Comparator comparator, Object value) {
		return addClass("fieldValues[0]", comparator, value);
	}

	public Score addClass(String field, Comparator comparator, Object value) {
		return addClass(new Condition(field, comparator, value));
	}

	public Score addClass(Comparator lowerComparator, Object lowerValue,
			Comparator upperComparator, Object upperValue) {
		return addClass("fieldValues[0]", lowerComparator, lowerValue, upperComparator, upperValue);
	}

	public Score addClass(String field, Comparator lowerComparator, Object lowerValue,
			Comparator upperComparator, Object upperValue) {
		return addClass(new Condition(field, lowerComparator, lowerValue),
				new Condition(field, upperComparator, upperValue));
	}

	public Score addClass(Condition... conditions) {
		classes.add(Arrays.asList(conditions));
		return this;
	}

	public Score addElse(double weight) {
		cases.add(new Case(weight));
		// else case is the last case -> make list unmodifiable
		cases = Collections.unmodifiableList(cases);
		return this;
	}

	public Score addCase(double weight, String field, Comparator comparator, Object value) {
		return addCase(weight, new Condition(field, comparator, value));
	}

	public Score addCase(double weight, Condition... conditions) {
		this.cases.add(new Case(weight, conditions));
		return this;
	}

	public Case[] getCases() {
		return cases.toArray(new Case[cases.size()]);
	}

}
