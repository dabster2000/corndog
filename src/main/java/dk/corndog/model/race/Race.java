package dk.corndog.model.race;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import dk.corndog.model.team.AbstractEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@RequiredArgsConstructor
public class Race extends AbstractEntity {

	private @NonNull String raceUUID;
	
	private @NonNull String name;
	
	private @NonNull Date startDate;

	@OneToMany(fetch = FetchType.LAZY)
	private List<RaceDay> raceDays = new ArrayList<>();
	
}
