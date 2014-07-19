package dk.corndog.model.team;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import dk.corndog.enums.LeagueStatus;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@RequiredArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class League extends AbstractEntity {
	
	private @NonNull String leagueUUID;
	
	private @NonNull int rank;
	
	private @NonNull LeagueStatus status;
	
	@OneToMany
	private List<Team> teams = new ArrayList<>();
	
}
