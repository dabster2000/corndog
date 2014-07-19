package dk.corndog.jobs;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dk.corndog.enums.RaceStatus;
import dk.corndog.model.race.RaceDay;
import dk.corndog.model.race.TrackSegment;
import dk.corndog.repositories.RaceDayRepository;
import dk.corndog.repositories.TeamRepository;
import dk.corndog.repositories.TrackSegmentRepository;
import dk.corndog.utils.TaskQueue;
import dk.corndog.utils.Terrain;

@Slf4j
@Component
@EnableScheduling
public class CreateRaceWorker implements Worker {

	@Autowired
	RaceDayRepository raceRepository;
	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	TrackSegmentRepository trackSegmentRepository;

	@Transactional
	@Scheduled(fixedRate = 30000)
	public void work() {
		List<RaceDay> races = raceRepository.findByRaceStatusContains(RaceStatus.SUBMITTED);
		if(races.size() == 0) return;
		
		log.info("Found "+races.size()+" submitted races");
		
		for (RaceDay race : races) {
			race.setRaceStatus(RaceStatus.CLOSED);
			Terrain terrain = new Terrain();
			
			for (TrackSegment trackSegment : terrain.getTerrain(race.getRaceDayUUID())) {
				race.getTrackSegments().add(trackSegmentRepository.save(trackSegment));
				race.setDistance(race.getDistance() + trackSegment.getDistance());
			}
			
			/** Previous autogenerator track
			terrain.generateTerrain();
			
			for (int n = 0; n < terrain.parts + 1; n++) {
				TrackSegment trackSegment = new TrackSegment(1, terrain.tops[n], ((n>0)?terrain.tops[n-1]:terrain.tops[0]), 0, 0, 20.0);
				race.trackSegments.add(trackSegmentRepository.save(trackSegment));
			}
			*/
			
			//race.teams.addAll(teamRepository.findByStatusContains(TeamStatus.CREATED));
			
			
			
			TaskQueue.getInstance().raceQueue.add(race);
		}
	}

}
