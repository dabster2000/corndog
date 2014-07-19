package dk.corndog.model.team;


public class DynamicAbility {

	private StaticAbility sa;
	public double stamina;
	public double speed;
	public double frustration;
	public boolean fatigued;
	public double powerLevel; // How much power is being used
	public double recoveredStamina;
	public double aggressive;

	public DynamicAbility(StaticAbility sa) {
		this.sa = sa;
		this.powerLevel = 0.0;
		this.recoveredStamina = 0.0;
		this.aggressive = sa.aggressive;
		stamina = sa.getMaxStamina();
		speed = 0.0;
		frustration = sa.aggressive * 0.01;
		fatigued = false;
	}
	
	public void recover(double amount) {
		if((stamina+=amount) > sa.getMaxStamina()) stamina = sa.getMaxStamina();
	}

	@Override
	public String toString() {
		return "DynamicAbility [sa=" + sa + ", stamina=" + stamina + ", speed="
				+ speed + ", frustration=" + frustration + ", fatigued="
				+ fatigued + "]";
	}

}
