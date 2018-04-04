package com.greendelta.search.wrapper.score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class AbstractScore implements IScore {

	List<Case> cases = new ArrayList<>();

	public Case[] getCases() {
		return cases.toArray(new Case[cases.size()]);
	}

	public IScore addElse(double weight) {
		cases.add(new Case(weight));
		// else case is the last case -> make list unmodifiable
		cases = Collections.unmodifiableList(cases);
		return this;
	}

	public IScore addCase(double weight, Condition... conditions) {
		this.cases.add(new Case(weight, conditions));
		return this;
	}
	
}
