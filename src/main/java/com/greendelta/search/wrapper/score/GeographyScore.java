package com.greendelta.search.wrapper.score;

/**
 * Implementing scripts are expected to define the following variables:<br>
 * <br>
 * <b>valueLat</b>: The latitude of the given point value <br>
 * <b>valueLon</b>: The longitude of the given point value <br>
 * <b>{pointField}_lat</b>: The latitude of the value of the pointField<br>
 * <b>{pointField}_lon</b>: The longitude of the value of the pointField<br>
 * <b>delta_lat</b>: The delta of valueLat to {pointField}_lat<br>
 * <b>delta_lon</b>: The delta of valueLon to {pointField}_lon<br>
 * <b>distance</b>: The distance between {pointField} and the given point value<br>
 * <br>
 * Dots in the field name (nested) are replaced by _ (e.g. loc.lat => loc_lat)
 */
public class GeographyScore extends AbstractScore {

	public final String field;
	public final Point value;

	public GeographyScore(String field, Point value) {
		this.field = field;
		this.value = value;
	}

	public static class Point {

		public final double latitude;
		public final double longitude;

		public Point(double latitude, double longitude) {
			this.latitude = latitude;
			this.longitude = longitude;
		}

	}

}
