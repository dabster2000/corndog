package dk.corndog.model.race;

import java.util.ArrayList;

import dk.corndog.model.team.Biker;


public class Group {

	public ArrayList<Biker> bikers = new ArrayList<Biker>();
	
	public String name = "Peleton";
	public double distance = 0;
	public double speed = 0;
	public boolean fatigued = false;
	public double speedKmPerHour;
	public boolean finished = false;

}
