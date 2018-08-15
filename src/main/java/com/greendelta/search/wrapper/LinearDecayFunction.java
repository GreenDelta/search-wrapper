package com.greendelta.search.wrapper;

public class LinearDecayFunction {

	public final String fieldName;
	public final Object origin;
	public final Object scale;
	public final Object offset;
	public final double decay;

	public LinearDecayFunction(String fieldName, Object origin, Object scale) {
		this(fieldName, origin, scale, null, 0.5d);
	}

	public LinearDecayFunction(String fieldName, Object origin, Object scale, Object offset, double decay) {
		this.fieldName = fieldName;
		this.origin = origin;
		this.scale = scale;
		this.offset = offset;
		this.decay = decay;
	}

}
