package dk.corndog.model.race;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import dk.corndog.enums.RaceStatus;
import dk.corndog.model.team.AbstractEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@RequiredArgsConstructor
public class RaceDay extends AbstractEntity {

	private @NonNull String raceDayUUID;
	
	private @NonNull String raceDayName;
	
	private @NonNull int startTime;

	private double distance;
	
	@Enumerated(EnumType.STRING) 
	private RaceStatus raceStatus;

	@OneToMany(fetch = FetchType.LAZY)
	private List<TrackSegment> trackSegments;

	@OneToMany(fetch = FetchType.LAZY)
	private List<TeamStrategy> teamStrategies = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY)
	private List<BikerStrategy> bikerStrategies = new ArrayList<>();

	@PrePersist
	void preInsert() {
		raceStatus = RaceStatus.SUBMITTED;
	}
	
	public TrackSegment getTrackSegmentBasedOnDistance(double distance) {
		double trackSegmentLength = 0;
		TrackSegment relevantTrackSegment = trackSegments.get(0);
		for (TrackSegment trackSegment : trackSegments) {
			trackSegmentLength += trackSegment.getDistance();
			if(distance < trackSegmentLength) {
				return trackSegment;
			}
			relevantTrackSegment = trackSegment;
		}
		return relevantTrackSegment;
	}
	
	public double getDistanceOnCurrentTrackSegment(double distance) {
		double totalDistance = 0.0;
		double result = distance;
		for (TrackSegment trackSegment : trackSegments) {
			totalDistance += trackSegment.getDistance();
			if(distance < totalDistance) return result;
			else result -= trackSegment.getDistance();
		}
		return result;
	}
}
