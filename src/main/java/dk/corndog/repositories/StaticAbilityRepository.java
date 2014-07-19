package dk.corndog.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dk.corndog.model.team.StaticAbility;

@RepositoryRestResource(exported=false)
public interface StaticAbilityRepository extends CrudRepository<StaticAbility, Long> {
}
