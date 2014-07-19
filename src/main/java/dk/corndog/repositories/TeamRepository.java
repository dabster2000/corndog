package dk.corndog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import dk.corndog.enums.TeamStatus;
import dk.corndog.model.team.Team;

@RepositoryRestResource(exported=true)
public interface TeamRepository extends CrudRepository<Team, Long> {

	public List<Team> findByUsernameContains(@Param("username") String username);
	
	public List<Team> findByTeamnameContains(@Param("teamname") String teamname);
	
	public List<Team> findByTeamUUIDContains(@Param("teamUUID") String teamUUID);
	
	@Query("select t from Team t where t.teamStatus like :teamStatus")
	@Transactional(readOnly=true)
	public List<Team> findByTeamStatusContains(@Param("teamStatus") TeamStatus teamStatus);
}
