package dk.corndog.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dk.corndog.enums.TeamStatus;
import dk.corndog.model.race.Race;
import dk.corndog.model.race.RaceDay;
import dk.corndog.model.race.Season;
import dk.corndog.model.team.Team;
import dk.corndog.repositories.RaceDayRepository;
import dk.corndog.repositories.RaceRepository;
import dk.corndog.repositories.SeasonRepository;
import dk.corndog.repositories.TeamRepository;

@Slf4j
@Component
@EnableScheduling
public class BootstrapWorker implements Worker {
	
	@Value("${corndog.season_length}") private int SEASON_LENGTH;

	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	SeasonRepository seasonRepository;
	
	@Autowired
	RaceRepository raceRepository;
	
	@Autowired
	RaceDayRepository raceDayRepository;
	
	private String[] teamNames = {"Rynkeby", "Post Danmark", "Easy On", "Bjarke Riis", "Tanderup Hjul", "Ned ad Bakke"};
	
	private static boolean runOnce = false;
	
	@Transactional
	@Scheduled(fixedRate = 10000, initialDelay = 10000)
	public void work() {
		if(!runOnce) {
			log.info("Bootstrap running...");
			
			for (String teamName : teamNames) {
				Team team = new Team(UUID.randomUUID().toString(), "", "", teamName);
				team.setTeamStatus(TeamStatus.SUBMITTED);
				team.setTeamUUID(UUID.randomUUID().toString());
				if(teamRepository.findByTeamnameContains(teamName).size() == 0 ) teamRepository.save(team);
			}
			Team team2 = new Team(UUID.randomUUID().toString(), "hans", "1234", "Epoke");
			team2.setTeamStatus(TeamStatus.SUBMITTED);
			team2.setTeamUUID(UUID.randomUUID().toString());
			teamRepository.save(team2);
			Team team3 = new Team(UUID.randomUUID().toString(), "jesper", "1234", "Dropings");
			team3.setTeamStatus(TeamStatus.SUBMITTED);
			team3.setTeamUUID(UUID.randomUUID().toString());
			teamRepository.save(team3);
			
			Season season = new Season(UUID.randomUUID().toString(), new DateTime(new Date()), SEASON_LENGTH);
			seasonRepository.save(season);

			Calendar date = Calendar.getInstance();
			date.add(Calendar.DAY_OF_YEAR, 1);
			Race race = new Race(UUID.randomUUID().toString(), "Tour de France", date.getTime());
			raceRepository.save(race);
			
			RaceDay raceDay1 = new RaceDay(UUID.randomUUID().toString(), "Stage 1", 12);
			raceDayRepository.save(raceDay1);
			
			RaceDay raceDay2 = new RaceDay(UUID.randomUUID().toString(), "Stage 2", 12);
			raceDayRepository.save(raceDay2);
			
			season.getRaces().add(race);
			race.getRaceDays().add(raceDay1);
			race.getRaceDays().add(raceDay2);
			
			runOnce = true;
		}
	}
}
