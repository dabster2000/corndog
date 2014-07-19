package dk.corndog.model.race;

import javax.xml.bind.annotation.XmlRootElement;

import dk.corndog.model.team.Biker;


@XmlRootElement
public class StaticBiker {

	public String bikerId;

	public double stamina;
	
	public double recoveredStamina;
	
	public double powerLevel;

	public boolean fatigued;
	

	public StaticBiker() {
	}
	
	public StaticBiker(Biker biker) {
		this.bikerId = biker.bikerUUID;
		this.stamina = biker.da.stamina;
		this.fatigued = biker.da.fatigued;
		this.recoveredStamina = biker.da.recoveredStamina;
		this.powerLevel = biker.da.powerLevel;
	}
}
