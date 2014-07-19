package dk.corndog.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dk.corndog.model.team.League;

@RepositoryRestResource(exported=true)
public interface LeagueRepository extends CrudRepository<League, Long> {

	public List<League> findByLeagueUUIDContains(@Param("leagueUUID") String leagueUUID);
	
}
