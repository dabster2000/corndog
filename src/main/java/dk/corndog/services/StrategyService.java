package dk.corndog.services;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dk.corndog.enums.StrategyLocation;
import dk.corndog.enums.TeamStrategyType;
import dk.corndog.model.race.BikerStrategy;
import dk.corndog.model.race.RaceDay;
import dk.corndog.model.race.TeamStrategy;
import dk.corndog.repositories.BikerStrategyRepository;
import dk.corndog.repositories.RaceDayRepository;
import dk.corndog.repositories.TeamStrategyRepository;

@Slf4j
@RestController
@RequestMapping("/raceDays/{id}")
@ExposesResourceFor(RaceDay.class)
public class StrategyService {

	@Autowired
	private RaceDayRepository raceDayRepository;
	
	@Autowired
	private TeamStrategyRepository teamStrategyRepository;
	
	@Autowired
	private BikerStrategyRepository bikerStrategyRepository;
	
	@RequestMapping(value = "teamStrategies", method = GET)
	public List<TeamStrategy> findAll(@PathVariable("id") RaceDay raceDay) {
		return raceDay.getTeamStrategies();
	}
	
	@RequestMapping(value = "teamStrategies", method = POST)
	@Transactional
	public void submitTactic(@PathVariable("id") RaceDay raceDay, @RequestBody TeamStrategy teamStrategy) {
		log.info("Received: "+raceDay);
		log.info("Received: "+teamStrategy);
		teamStrategy = teamStrategyRepository.save(teamStrategy);
		raceDay = raceDayRepository.findOne(raceDay.getId());
		raceDay.getTeamStrategies().add(teamStrategy);
	}
	
	@RequestMapping(value = "bikerStrategies", method = POST)
	@Transactional
	public void submitTactic(@PathVariable("id") RaceDay raceDay, @RequestBody BikerStrategy bikerStrategy) {
		log.info("Received: "+raceDay);
		log.info("Received: "+bikerStrategy);
		bikerStrategy = bikerStrategyRepository.save(bikerStrategy);
		raceDay = raceDayRepository.findOne(raceDay.getId());
		raceDay.getBikerStrategies().add(bikerStrategy);
	}
	
	@Data
	@EqualsAndHashCode(callSuper = true)
	static class TeamStrategyResource extends ResourceSupport {
		private final StrategyLocation strategyLocation;
		private final TeamStrategyType teamStrategyType;
	}
	
}
