package dk.corndog.jobs;

import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dk.corndog.enums.LeagueStatus;
import dk.corndog.enums.TeamStatus;
import dk.corndog.model.team.League;
import dk.corndog.model.team.Team;
import dk.corndog.repositories.LeagueRepository;
import dk.corndog.repositories.TeamRepository;

@Slf4j
@Component
@EnableScheduling
public class CreateLeagueWorker implements Worker {

	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	LeagueRepository leagueRepository;
	
	@Transactional
	@Scheduled(fixedRate = 30000)
	public void work() {
		List<Team> createdTeams = teamRepository.findByTeamStatusContains(TeamStatus.CREATED);
		if(createdTeams.size() == 0) return;
		log.info("Found "+createdTeams.size()+" created teams");
		while (createdTeams.size() >= 8) {
			League league = new League(UUID.randomUUID().toString(), 1, LeagueStatus.CREATED);
			leagueRepository.save(league);
			for (int i = 0; i < 8; i++) {
				Team team = createdTeams.remove(0);
				team.setLeague(league);
				team.setTeamStatus(TeamStatus.ACTIVE);
				teamRepository.save(team);
				league.getTeams().add(team);
			}
		}
	}
}

