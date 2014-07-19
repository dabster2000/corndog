package dk.corndog.model.team;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Biker {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EMP_SEQ")
    @SequenceGenerator(name="EMP_SEQ", sequenceName="EMP_SEQ", allocationSize=100)
	public long id;

	public String bikerUUID;
	
	public String teamUUID;
	
	@OneToOne(cascade=CascadeType.ALL)
	public StaticAbility sa;
	
	@Transient
	@JsonIgnore
	public DynamicAbility da;
	
	public String name;
	
	public Biker() {
	}
	
	public Biker(String name, double form, double mood, double speed, double aggressive, double technique, double weight, double height, String uuid, String teamUUID) {
		int level = (int)Math.round((speed + aggressive + technique) / 3);
		sa = new StaticAbility(form, mood, speed, level, aggressive, technique, weight, height, level);
		da = new DynamicAbility(sa);
		this.name = name;
		this.teamUUID = teamUUID;
		reset();
	}
	
	public void reset() {
		da = new DynamicAbility(sa);
		da.fatigued = false;
		da.stamina = sa.getMaxStamina();
	}

	@Override
	public String toString() {
		return "Biker [sa=" + sa + ", da=" + da + ", name=" + name + "]";
	}
	
	public void roundDown() {
		sa.aggressive = Math.round(sa.aggressive);
		sa.height = Math.round(sa.height);
		sa.speed = Math.round(sa.speed);
		sa.stamina = Math.round(sa.stamina);
		sa.technique = Math.round(sa.technique);
		sa.weight = Math.round(sa.weight);
	}

	@PrePersist
	void preInsert() {
		bikerUUID = UUID.randomUUID().toString();
	}

	
	public String getBikerUUID() {
		return bikerUUID;
	}

	public void setBikerUUID(String bikerUUID) {
		this.bikerUUID = bikerUUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
