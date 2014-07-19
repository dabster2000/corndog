package dk.corndog.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dk.corndog.model.race.Season;

@RepositoryRestResource(exported=true)
public interface SeasonRepository extends CrudRepository<Season, Long> {

	public List<Season> findBySeasonUUIDContains(@Param("seasonUUID") String seasonUUID);
	
}
