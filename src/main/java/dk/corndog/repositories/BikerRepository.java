package dk.corndog.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dk.corndog.model.team.Biker;

@RepositoryRestResource(exported=true)
public interface BikerRepository extends CrudRepository<Biker, Long> {
	public List<Biker> findByTeamUUIDContains(@Param("teamUUID") String teamUUID);
}
