package dk.corndog.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import dk.corndog.model.team.Team;

@Component
public class CoreTeamRessourceProcessor implements ResourceProcessor<Resource<Team>> {

	@Autowired
	private EntityLinks entityLinks;
	
	@Override
	public Resource<Team> process(Resource<Team> resource) {
		Team team = resource.getContent();
		
		resource.add(entityLinks.linkForSingleResource(team).withRel("leave"));
		
		return resource;
	}

}
