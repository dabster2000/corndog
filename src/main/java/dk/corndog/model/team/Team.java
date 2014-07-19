package dk.corndog.model.team;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import dk.corndog.enums.TeamStatus;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@RequiredArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Team extends AbstractEntity {
		
	private @NonNull String teamUUID;
	
	private @NonNull String username;
	
	@JsonIgnore
	private @NonNull String password;
	
	private @NonNull String teamname;
	
	@Enumerated(EnumType.STRING) 
	private TeamStatus teamStatus;
	
	@ManyToOne
	private League league;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "team", referencedColumnName = "id")
	private List<Biker> bikers = new ArrayList<Biker>();

}
