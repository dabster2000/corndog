package dk.corndog.model.team;

public class Bike {

	private static double[] TIREVALUES = {0.005, 0.004, 0.012};
	private static double[] AEROVALUES = {0.388, 0.445, 0.420, 0.300, 0.233, 0.200};
	
	private double weight;
	private int aero;
	private int tire;

	public Bike(double weight, int aero, int tire) {
		super();
		this.weight = weight;
		this.aero = aero;
		this.tire = tire;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getTire() {
		return tire;
	}

	public void setTire(int tire) {
		this.tire = tire;
	}

	public int getAero() {
		return aero;
	}

	public void setAero(int aero) {
		this.aero = aero;
	}
	
	public double getTireValues() {
		return TIREVALUES[tire];
	}
	
	public double getAeroValues() {
		return AEROVALUES[aero];
	}
}
