package com.halo.model.enums;

/**
 * @author Deep
 * @create 2023-09-27
 */
public enum OptionType implements ValueEnum<Integer> {
	/**
	 * internal option
	 */
	INTERNAL(0),

	/**
	 * custom option
	 */
	CUSTOM(1);

	private final Integer value;

	OptionType(Integer value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return value;
	}
}
