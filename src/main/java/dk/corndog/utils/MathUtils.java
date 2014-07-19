package dk.corndog.utils;

public class MathUtils {

	private MathUtils() {
	}
	
	public static boolean isBetween(double test, double low, double high) {
		return (test > low && test < high);
	}
	
	public static double chancePerKm(double chance, double distance) {
		return chance / (distance / 100.0);
	}
}
