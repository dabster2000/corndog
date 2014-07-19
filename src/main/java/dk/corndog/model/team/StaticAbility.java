package dk.corndog.model.team;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class StaticAbility {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EMP_SEQ")
    @SequenceGenerator(name="EMP_SEQ", sequenceName="EMP_SEQ", allocationSize=100)
	public long id;
	public int level;
	public double form;
	public double mood;
	public double speed;
	public double stamina;
	public double aggressive;
	public double technique;
	public double weight;
	public double height;
	
	public StaticAbility() {
	}
	
	public StaticAbility(double form, double mood, double speed, double stamina, double aggressive,
			double technique, double weight, double height, int level) {
		super();
		this.form = form;
		this.mood = mood;
		this.speed = speed;
		this.stamina = stamina;
		this.aggressive = aggressive;
		this.technique = technique;
		this.weight = weight;
		this.height = height;
		this.level = level;
	}
	
	public double getMaxStamina() {
		return Math.log1p(stamina)*3200+500;
		//return Math.exp(((stamina / 2) + 5) * 0.9) + 100;
	}

	@Override
	public String toString() {
		return "StaticAbility [level=" + level + ", speed=" + speed
				+ ", stamina=" + stamina + ", aggressive=" + aggressive
				+ ", technique=" + technique + ", weight=" + weight
				+ ", height=" + height + "]";
	}
}
