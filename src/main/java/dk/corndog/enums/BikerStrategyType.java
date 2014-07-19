package dk.corndog.enums;

public enum BikerStrategyType {

	NONE(95.0), ATTACK(1.0), DEFEND(0.0), PUSH(0.0), HELP(0.0);
	
	private double probability;
	
	private BikerStrategyType(double probability) {
		this.probability = probability;
	}

	public double getProbability() {
		return this.probability;
	}
}
