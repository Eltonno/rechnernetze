package kalender;

public enum WiederholungType {
	TAEGLICH("täglich") {
		@Override
		public int inTagen() {
			return 1;
		}
	},
	WOECHENTLICH("wöchentlich") {
		@Override
		public int inTagen() {
			return 7;
		}
	};

	private String name;

	private WiederholungType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public abstract int inTagen();
};
