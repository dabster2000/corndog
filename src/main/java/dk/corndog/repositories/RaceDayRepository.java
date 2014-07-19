package dk.corndog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dk.corndog.enums.RaceStatus;
import dk.corndog.model.race.RaceDay;

@RepositoryRestResource(exported=true)
public interface RaceDayRepository extends CrudRepository<RaceDay, Long> {

	public List<RaceDay> findByRaceDayUUIDContains(@Param("raceDayUUID") String raceDayUUID);
	
	@Query("select r from RaceDay r where r.raceStatus like :raceStatus")
	public List<RaceDay> findByRaceStatusContains(@Param("raceStatus") RaceStatus raceStatus);
	
}
