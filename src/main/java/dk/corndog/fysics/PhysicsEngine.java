package dk.corndog.fysics;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dk.corndog.model.race.TrackSegment;
import dk.corndog.model.team.Bike;
import dk.corndog.model.team.Biker;

@Component
public class PhysicsEngine {

	@Value("${corndog.max_relevant_group_size}") private int MAX_RELEVANT_GROUP_SIZE;

	@Value("${corndog.physics.base_group_kph}") private int BASE_GROUP_KPH;

	@Value("${corndog.physics.level_effect_on_group_kph}") private int LEVEL_EFFECT_ON_GROUP_KPH;

	@Value("${corndog.physics.grade_effect_on_group_kph}") private double GRADE_EFFECT_ON_GROUP_KPH;

	@Value("${corndog.physics.group_power_modifier}") private double GROUP_POWER_MODIFIER;
	
	private double trans = 95 * 0.01;
	
	public double getPowerPerGrade(double grade) {
		return grade * 14.1 + 315;
	}
	
	public double getSpeedPerGradePerLevel(double grade, double level) {
		return  BASE_GROUP_KPH + (LEVEL_EFFECT_ON_GROUP_KPH * level) + (Math.random() * 5.0) - (grade * GRADE_EFFECT_ON_GROUP_KPH);
	}
	/*
	public static void main(String[] args) {
		Biker biker = new Biker("qwe", 8, 8, 8, 83, 176);
		Bike bike = new Bike(6, 4, 1);
		TrackSegment trackSegment = new TrackSegment(666.4726 - 666.1870, 86.2817, 87.2229, 0, 0, 20);
		System.out.println(trackSegment.getGrade());
		System.out.println(calcPower(biker, bike, trackSegment, 25));
		System.out.println(getSpeedPerGradePerLevel(1.35, 8.7));
	}*/
	
	public double getActualPowerUsed(Biker biker, Bike bike, TrackSegment ts, double speed, int groupSize) {
		TrackSegment tsAdjust;
		try {
			if(groupSize > MAX_RELEVANT_GROUP_SIZE) groupSize = MAX_RELEVANT_GROUP_SIZE;
			
			tsAdjust = (TrackSegment) BeanUtils.cloneBean(ts);
			if(groupSize > MAX_RELEVANT_GROUP_SIZE) groupSize = MAX_RELEVANT_GROUP_SIZE;
			groupSize -= (ts.getGrade()>0.0)?ts.getGrade() / 2:0; // Withdraw the grade if its uphill.
			if(groupSize < 0) groupSize = 0;
			tsAdjust.setHeadwind(ts.getHeadwind() * (1.0 - ( (1.0 / MAX_RELEVANT_GROUP_SIZE) * groupSize) ) );
			
			double power = calcPower(biker, bike, tsAdjust, speed);
			return power * (1 - (GROUP_POWER_MODIFIER * groupSize));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return 0.0;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return 0.0;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return 0.0;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	
	public double calcVelocity(Biker biker, Bike bike, TrackSegment trackSegment, Double power) {
		double density = (1.293 - 0.00426 * trackSegment.getTemperature()) * Math.exp(-trackSegment.getHeight() / 7000.0);
		double airResistenceParameter = 0.5 * bike.getAeroValues() * density; // full air resistance parameter
		double weightInNewtons = 9.8 * (biker.sa.weight + bike.getWeight()); // total weight in newtons
		double gravityAndRollingResistance = weightInNewtons * ((trackSegment.getGrade() * 0.001) + bike.getTireValues()); // gravity and rolling resistance
		double velocity = newton(airResistenceParameter, trackSegment.getHeadwind() / 3.6, gravityAndRollingResistance, trans, power) * 3.6;
		return velocity;
	}
	
	public double calcTime(Biker biker, Bike bike, TrackSegment trackSegment, double power) {
		double velocity = calcVelocity(biker, bike, trackSegment, power);
		if (velocity > 0.0) return 60.0 * trackSegment.getDistance() / velocity;
		else return 0.0; // don't want any div by zero errors
	}
	
	public double calcCalories(double minutes, double power) {
		return (minutes / 60.0) * (power * 4.0) / 1.163;
	}
	
	public double calcPower(Biker biker, Bike bike, TrackSegment trackSegment, double speed) {
		double rweightv = biker.sa.weight; 
		double bweightv = bike.getWeight(); 
		double rollingRes = bike.getTireValues();  
		double frontalArea = bike.getAeroValues(); 
		double gradev = 0.01 * trackSegment.getGrade(); 
		double headwindv = trackSegment.getHeadwind() / 3.6; 
		double distancev = trackSegment.getDistance(); 
		double temperaturev = trackSegment.getTemperature(); 
		double elevationv = trackSegment.getHeight(); 
		double transv = 0.95; 
		double density = (1.293 - 0.00426 * temperaturev) * Math.exp(-elevationv / 7E3); 
		double twt = 9.8 * (rweightv + bweightv); 
		double A2 = 0.5 * frontalArea * density; 
		double tres = twt * (gradev + rollingRes); 
		//double calcMode ? ( 
		double v = speed / 3.6; 
		double tv = v + headwindv;
		double powerv = (v * tres + v * tv * tv * A2) / transv; 
		//t = 0 < v ? 16.6667 * distancev / v : 0, 
		//power.value = makeDecimal0(powerv), 
		//dragSlider.setValue(powerv / 500)) 
		/*
		: 
		( powerv = eval(power.value), 
		v = 3.6 * newton(A2, headwindv, tres, transv, powerv), 
		t = 0 < v ? 60 * distancev / v : 0, 
		velocity.value = makeDecimal2(v * ( units ? 0.6214 : 1))
		), 
		c = 0.24 * t * powerv, 
		wl = c / 32318, 
		time.value = makeDecimal2(t), 
		calories.value = makeDecimal0(c * ( units ? 0.2388 : 1)), 
		weightloss.value = makeDecimal2(wl * ( units ? 2.205 : 1))
		*/
		return powerv;
	}
	
	private double newton(double aero, double hw, double tr, double tran,
			double p) { /* Newton's method */
		/*System.out.println(aero);
		System.out.println(hw);
		System.out.println(tr);
		System.out.println(tran);
		System.out.println(p);*/

		double velocity = 20; // Initial guess
		double maxIterations = 10; // maximum iterations
		double tolerance = 0.05; // tolerance

		for (int i = 1; i < maxIterations; i++) {
			double tv = velocity + hw;
			double f = velocity * (aero * tv * tv + tr) - tran * p; // the function
			double fp = aero * (3.0 * velocity + hw) * tv + tr; // the derivative
			double newVelocity = velocity - f / fp;
			if (Math.abs(newVelocity - velocity) < tolerance)
				return newVelocity; // success
			velocity = newVelocity;
		}
		return 0.0; // failed to converge
	}
}

/*
calcMode = units = 0;
tireValues = [0.005, 0.004, 0.012];
aeroValues = [0.388, 0.445, 0.42, 0.3, 0.233, 0.2];

function makeDecimal2(a) {
	if (null != a && 0 != a)
		var b = Math.round(100 * parseFloat(a)), a = 100 > b ? 0 : parseInt(b / 100), b = b % 100, a = "" + a + "." + (10 <= b ? "" : "0") + b;
	return a
}

function makeDecimal0(a) {
	null != a && 0 != a && ( a = "" + Math.round(parseFloat(a)));
	return a
}

function valDecNumber(a) {
	if (0 == a.length)
		return !1;
	var b = !0, d = !0;
	for ( i = 0; i < a.length; i++) {
		var f = a.substring(i, i + 1);
		if (d && "." == f)
			d = !1;
		else if (b && "-" == f)
			b = !1;
		else if ("0" > f || "9" < f)
			return alert("I don't think you want to do that, Dave."), !1
	}
	return !0
}

function empty(a) {
	return "" == a || null == a ? !0 : !1
}

function newton(a, b, d, f, h) {
	var g = 20;
	for ( i = 1; 10 > i; i++) {
		var e = g + b, e = g - (g * (a * e * e + d) - f * h) / (a * (3 * g + b) * e + d);
		if (0.05 > Math.abs(e - g))
			return e;
		g = e
	}
	return 0
}

function setMode(a) {
	calcMode = a
}

function changeUnits(a) {
	with (a)
	units = unitsMenu.selectedIndex, 0 == units ? (rweight.value = makeDecimal0(rweightv), bweight.value = makeDecimal0(bweightv), headwind.value = makeDecimal0(3.6 * headwindv), distance.value = makeDecimal2(distancev), temperature.value = makeDecimal0(temperaturev), elevation.value = makeDecimal0(elevationv)) : (rweight.value = makeDecimal0(2.205 * rweightv), bweight.value = makeDecimal0(2.205 * bweightv), headwind.value = makeDecimal0(2.2374 * headwindv), distance.value = makeDecimal2(0.6215 * distancev), temperature.value = makeDecimal0(1.8 * temperaturev + 32), elevation.value = makeDecimal0(3.281 * elevationv)), calcMode = 0, update(a)
}

function updateDistance(a, b) {
	with (a)
	1 == b ? ( newTime = eval(time.value), distancev *= newTime / t) : 2 == b ? ( newCal = eval(calories.value) * ( units ? 4.188 : 1), distancev *= newCal / c) : ( newWeightloss = eval(weightloss.value) * ( units ? 0.4536 : 1), distancev *= newWeightloss / wl), distance.value = makeDecimal2(distancev * ( units ? 0.6214 : 1)), update(a)
}

function update(a) {
	with (a)
	rweightv = eval(rweight.value) * ( units ? 0.4536 : 1), bweightv = eval(bweight.value) * ( units ? 0.4536 : 1), theTire = tire.selectedIndex, rollingRes = tireValues[theTire], theAero = aero.selectedIndex, frontalArea = aeroValues[theAero], gradev = 0.01 * eval(grade.value), headwindv = eval(headwind.value) * ( units ? 1.609 : 1) / 3.6, distancev = eval(distance.value) * ( units ? 1.609 : 1), temperaturev = (eval(temperature.value) - ( units ? 32 : 0)) * ( units ? 0.5555 : 1), elevationv = eval(elevation.value) * ( units ? 0.3048 : 1), transv = 0.95, density = (1.293 - 0.00426 * temperaturev) * Math.exp(-elevationv / 7E3), twt = 9.8 * (rweightv + bweightv), A2 = 0.5 * frontalArea * density, tres = twt * (gradev + rollingRes), calcMode ? ( v = eval(velocity.value) / 3.6 * ( units ? 1.609 : 1), tv = v + headwindv, powerv = (v * tres + v * tv * tv * A2) / transv, t = 0 < v ? 16.6667 * distancev / v : 0, power.value = makeDecimal0(powerv), dragSlider.setValue(powerv / 500)) : ( powerv = eval(power.value), v = 3.6 * newton(A2, headwindv, tres, transv, powerv), t = 0 < v ? 60 * distancev / v : 0, velocity.value = makeDecimal2(v * ( units ? 0.6214 : 1))), c = 0.24 * t * powerv, wl = c / 32318, time.value = makeDecimal2(t), calories.value = makeDecimal0(c * ( units ? 0.2388 : 1)), weightloss.value = makeDecimal2(wl * ( units ? 2.205 : 1))
}

function startDragSlider() {
	dragSlider = new Dragdealer("powerCtrl", {
		x : 0.3,
		animationCallback : function(a) {
			var b = document.getElementById("powerID"), d = document.getElementById("calcID");
			b.value = makeDecimal0(500 * a);
			calcMode = 0;
			update(d)
		}
	})
};
*/