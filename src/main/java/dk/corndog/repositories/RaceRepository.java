package dk.corndog.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dk.corndog.model.race.Race;

@RepositoryRestResource(exported=true)
public interface RaceRepository extends CrudRepository<Race, Long> {

	public List<Race> findByRaceUUIDContains(@Param("raceUUID") String raceUUID);

}
