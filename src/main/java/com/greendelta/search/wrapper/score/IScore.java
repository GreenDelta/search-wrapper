package com.greendelta.search.wrapper.score;


public interface IScore {

	public Case[] getCases();

	public IScore addElse(double weight);

	public IScore addCase(double weight, Condition... conditions);

}
