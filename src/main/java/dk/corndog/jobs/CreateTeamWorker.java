package dk.corndog.jobs;

import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dk.corndog.enums.TeamStatus;
import dk.corndog.model.team.Biker;
import dk.corndog.model.team.Names;
import dk.corndog.model.team.Team;
import dk.corndog.repositories.BikerRepository;
import dk.corndog.repositories.TeamRepository;

@Slf4j
@Component
@EnableScheduling
public class CreateTeamWorker implements Worker {

	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	BikerRepository bikerRepository;

	@Transactional
	@Scheduled(fixedRate = 10000)
	public void work() {
		List<Team> submittedTeams = teamRepository.findByTeamStatusContains(TeamStatus.SUBMITTED);
		if(submittedTeams.size() == 0) return;
		
		log.info("Found "+submittedTeams.size()+" submitted teams");
		
		for (Team team : submittedTeams) {
			for (int i = 0; i < 10; i++) {
				Biker biker = new Biker(Names.randomFirstname()+" "+Names.randomLastname(), Math.random()*20, Math.random()*20, Math.random()*20, Math.random()*20, Math.random()*20, Math.random() * 40+60, Math.random() * 40+160, UUID.randomUUID().toString(), team.getTeamUUID());
				bikerRepository.save(biker);
				team.getBikers().add(biker);
			}
			team.setTeamStatus(TeamStatus.CREATED);
			//teamRepository.save(team);
		}
	}
}
