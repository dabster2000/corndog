package dk.corndog.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dk.corndog.model.race.BikerStrategy;

@RepositoryRestResource(exported=true)
public interface BikerStrategyRepository extends CrudRepository<BikerStrategy, Long> {

}
