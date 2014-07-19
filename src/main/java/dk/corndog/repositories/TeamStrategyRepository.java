package dk.corndog.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dk.corndog.model.race.TeamStrategy;

@RepositoryRestResource(exported=true)
public interface TeamStrategyRepository extends CrudRepository<TeamStrategy, Long> {
}
