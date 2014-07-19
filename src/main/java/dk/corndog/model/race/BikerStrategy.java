package dk.corndog.model.race;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import dk.corndog.enums.BikerStrategyType;
import dk.corndog.enums.StrategyLocation;
import dk.corndog.model.team.AbstractEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@RequiredArgsConstructor
public class BikerStrategy extends AbstractEntity {

	private @NonNull StrategyLocation strategyLocation;
	
	private @NonNull String bikerUUID;
	
	@Enumerated(EnumType.STRING) 
	private @NonNull BikerStrategyType bikerStrategyType;
	
	private String targetBikerUUID;
	
}
