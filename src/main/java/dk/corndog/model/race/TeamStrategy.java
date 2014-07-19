package dk.corndog.model.race;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import dk.corndog.enums.StrategyLocation;
import dk.corndog.enums.TeamStrategyType;
import dk.corndog.model.team.AbstractEntity;
import dk.corndog.model.team.Team;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@RequiredArgsConstructor
public class TeamStrategy extends AbstractEntity {

	private @NonNull StrategyLocation strategyLocation;

	@ManyToOne
	private @NonNull Team team;
	
	@Enumerated(EnumType.STRING) 
	private @NonNull TeamStrategyType teamStrategyType;
	
}
