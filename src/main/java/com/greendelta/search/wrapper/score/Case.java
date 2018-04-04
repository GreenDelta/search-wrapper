package com.greendelta.search.wrapper.score;

import java.util.Arrays;
import java.util.List;

public class Case {

	public final List<Condition> conditions;
	public final double weight;

	Case(double weight, Condition... conditions) {
		if (conditions == null) {
			conditions = new Condition[0];
		}
		this.weight = weight;
		this.conditions = Arrays.asList(conditions);
	}

}