package de.diddiz.utils.speedmodifiers;

public enum SpeedModifiers implements SpeedModifier {
	LINEAR {
		@Override
		public float modify(float factor) {
			return factor;
		}
	},
	CUBIC {
		@Override
		public float modify(float factor) {
			return factor * factor;
		}
	},
	GENTLE_START_STOP {
		@Override
		public float modify(float factor) {
			if (factor <= 0.2f)
				return factor * factor * 2.5f;
			else if (factor < 0.8f)
				return (float)(0.5 + (factor - 0.5) * (0.4 / 0.3));
			else
				return 1 - (factor - 1) * (factor - 1) * 2.5f;
		}
	},
	GENTLE {
		@Override
		public float modify(float factor) {
			if (factor <= 0.5f)
				return factor * factor * 2;
			return 1 - (factor - 1) * (factor - 1) * 2;
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
	};
}
