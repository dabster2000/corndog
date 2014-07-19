package dk.corndog.model.team;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import org.springframework.hateoas.Identifiable;

@MappedSuperclass
@Getter
@ToString
@EqualsAndHashCode
public class AbstractEntity implements Identifiable<Long> {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EMP_SEQ")
    @SequenceGenerator(name="EMP_SEQ", sequenceName="EMP_SEQ", allocationSize=100)
	private final Long id;

	protected AbstractEntity() {
		this.id = null;
	}
}
