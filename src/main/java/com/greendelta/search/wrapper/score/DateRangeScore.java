package com.greendelta.search.wrapper.score;

/**
 * Implementing scripts are expected to define the following variables:<br>
 * <br>
 * <b>_value_</b>: The given value of this score <br>
 * <b>{lowerField}</b>: The value of the field specified in lowerField<br>
 * <b>{upperField}</b>: The value of the field specified in upperField<br>
 * <b>distance</b>: The distance of <b>_value_</b> to the closest value (value
 * of lowerField or value of upperField)<br>
 * <br>
 * Dots in the field name (nested) are replaced by _ (e.g. loc.lat => loc_lat)
 */
public class DateRangeScore extends AbstractScore {

	public final String lowerField;
	public final String upperField;
	public final String value;

	public DateRangeScore(String lowerField, String upperField, String value) {
		this.lowerField = lowerField;
		this.upperField = upperField;
		this.value = value;
	}

	public DateRangeScore addMatch(double weight) {
		return (DateRangeScore) addCase(weight,
				new Condition("_value_", ">=", lowerField),
				new Condition("_value_", "<=", upperField));
	}

	public DateRangeScore addCase(String comparator, double limit, double weight) {
		return (DateRangeScore) addCase(weight,
				new Condition("distance", comparator, limit));
	}

	public DateRangeScore addCase(String lowerComparator, double lowerLimit, String upperComparator, double upperLimit,
			double weight) {
		return (DateRangeScore) addCase(weight,
				new Condition("distance", lowerComparator, lowerLimit),
				new Condition("distance", upperComparator, upperLimit));
	}
}
