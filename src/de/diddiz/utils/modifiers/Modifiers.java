package de.diddiz.utils.modifiers;

public enum Modifiers implements Modifier {
	LINEAR {
		@Override
		public float modify(float factor) {
			return factor;
		}
	},
	QUADRATIC {
		@Override
		public float modify(float factor) {
			return factor * factor;
		}
	},
	CUBIC {
		@Override
		public float modify(float factor) {
			return factor * factor * factor;
		}
	},
	GENTLE_START_STOP {
		@Override
		public float modify(float factor) {
			if (factor <= 0.2f)
				return factor * factor * 2.5f;
			else if (factor < 0.8f)
				return 0.5f + (factor - 0.5f) * 1.3333333f;
			else
				return 1 - (factor - 1) * (factor - 1) * 2.5f;
		}
	},
	/**
	 * <pre>
	 * x²(3 - 2x) = 3x² - 2x³
	 * </pre>
	 */
	GENTLE {
		@Override
		public float modify(float factor) {
			return factor * factor * (3f - 2f * factor);
		}
	},
	START_GENTLE {
		@Override
		public float modify(float factor) {
			if (factor <= 0.5f)
				return factor;
			return 1 - (factor - 1) * (factor - 1) * 2;
		}
	},
	STOP_GENTLE {
		@Override
		public float modify(float factor) {
			if (factor >= 0.5f)
				return factor;
			return factor * factor * 2;
		}
	},
	SIN {
		@Override
		public float modify(float factor) {
			return (float)(factor + Math.sin(Math.toRadians(factor / Math.PI * 10000)) / 10);
		}
	},
	/**
	 * All slopes are linear.
	 * 
	 * <pre>
	 * <table>
	 * <th><tr><td>Input</td><td>Returns</td></tr></th>
	 * <tr><td>0.0f -> 0.25f</td><td>0.0f -> 1.0f</td></tr>
	 * <tr><td>0.25f-0.75f</td><td>1.0f</td></tr>
	 * <tr><td>0.75f -> 1.0f</td><td>1.0f -> 0.0f</td></tr>
	 * </table>
	 * </pre>
	 */
	FADE_IN_FADE_OUT {
		@Override
		public float modify(float factor) {
			if (factor < 0.25f)
				return factor * 4f;
			else if (factor > 0.75f)
				return (1f - factor) * 4f;
			else
				return 1f;
		}
	},
	FLICKER_IN {
		private static final float SCALE_FACTOR = 4.8104773f;

		@Override
		public float modify(float factor) {
			return (float)(Math.sin(Math.log(factor * factor * factor * factor * factor * SCALE_FACTOR)) + 1f) / 2f;
		}
	};

	/**
	 * Creates a modifier wraps two other Modifiers.
	 * 
	 * For {@code factors} lesser or equal to {@code stopA}, {@code modifierA} will be used, {@code modifierB} for all others.
	 */
	public static Modifier compositeModifier(final Modifier modifierA, final float stopA, final Modifier modifierB) {
		return new Modifier() {
			@Override
			public float modify(float factor) {
				if (factor <= stopA)
					return modifierA.modify(factor / stopA);
				return modifierB.modify((factor - stopA) / (1f - stopA));
			}
		};
	}

	public static Modifier flickerIn(final float start, final float stop) {
		return new Modifier() {
			@Override
			public float modify(float factor) {
				if (factor <= start)
					return 0f;
				if (factor >= stop)
					return 1f;
				return FLICKER_IN.modify((factor - start) / (stop - start));
			}
		};
	}

	/**
	 * Creates a modifier that returns {@code 0f} while {@code factor} is smaller than {@code on} and {@code 1f} afterwards.
	 */
	public static Modifier offOn(final float on) {
		return new Modifier() {
			@Override
			public float modify(float factor) {
				if (factor < on)
					return 0f;
				return 1f;
			}
		};
	}

	/**
	 * Creates a modifier that returns {@code 1f} while {@code factor} is smaller than {@code off} and {@code 0f} afterwards.
	 */
	public static Modifier onOff(final float off) {
		return new Modifier() {
			@Override
			public float modify(float factor) {
				if (factor < off)
					return 1f;
				return 0f;
			}
		};
	}

	/**
	 * Creates a modifier that simply returns a static value.
	 */
	public static Modifier staticModifier(final float value) {
		return new Modifier() {
			@Override
			public float modify(float factor) {
				return value;
			}
		};
	}
}
