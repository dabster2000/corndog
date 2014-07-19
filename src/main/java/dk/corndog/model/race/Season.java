package dk.corndog.model.race;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import dk.corndog.model.team.AbstractEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@RequiredArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Season extends AbstractEntity {
	
	private @NonNull String seasonUUID;
	
	@Embedded
	private @NonNull DateTime startDate;
	
	private @NonNull int length;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "season", referencedColumnName = "id")
	private List<Race> races = new ArrayList<>();

}
