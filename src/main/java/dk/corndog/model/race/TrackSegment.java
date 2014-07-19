package dk.corndog.model.race;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import dk.corndog.enums.DownfallType;
import dk.corndog.enums.TarmacType;
import dk.corndog.model.team.AbstractEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class TrackSegment extends AbstractEntity {

	private double distance;
	private double height;
	private double headwind;
	private double position;
	private double temperature;
	private double grade;
	private double previousHeight;
	private double totalDistance;
	private TarmacType tarmac;
	private DownfallType downfall;
	
	public TrackSegment(double distance, double totalDistance, double height, double previousHeight, double headwind, double position, double temperature) {
		super();
		this.distance = distance;
		if(this.distance <= 0.0) this.distance = 0.01;
		this.totalDistance = totalDistance;
		this.height = height;
		this.previousHeight = previousHeight;
		this.headwind = headwind;
		this.position = position;
		this.temperature = temperature;
		this.grade = (this.height - this.previousHeight) / (this.distance * 10);
		if(this.grade > 9.0) this.grade = 9.0;
		tarmac = TarmacType.ASPHALT;
		downfall = DownfallType.NOTHING;
	}

}
