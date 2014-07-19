package dk.corndog.fysics;

public class SpeedProfile {
	public static double getPowerPerGrade(double grade) {
		return grade * 14.1 + 315;
	}
}

// 45 km/h at 0 grade, alone, no wind = 

// 66 kg at 6.4w/kg = 422,4 watt on Alpe d'Huez