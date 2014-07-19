package dk.corndog.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dk.corndog.model.race.TrackSegment;

@RepositoryRestResource(exported=true)
public interface TrackSegmentRepository extends CrudRepository<TrackSegment, Long> {

	
}
