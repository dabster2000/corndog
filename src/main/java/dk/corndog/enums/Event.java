package dk.corndog.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Event {
	NOTHING(50.0), CRASH(0.5), ATTACK(5.0);

	private static final List<Event> VALUES = Collections
			.unmodifiableList(Arrays.asList(values()));
	private double probability;

	private Event(double probability) {
		this.probability = probability;
	}

	public double getProbability() {
		return this.probability;
	}

	private static double summWeigts() {
		double summ = 0;
		for (Event value : VALUES) {

			summ += value.getProbability();
		}
		return summ;
	}

	private static final double SIZE = summWeigts();
	private static final Random RANDOM = new Random();

	public static Event randomType() {
		double randomNum = RANDOM.nextDouble() * SIZE;
		double currentWeightSumm = 0;
		for (Event currentValue : VALUES) {
			if (randomNum > currentWeightSumm
					&& randomNum <= (currentWeightSumm + currentValue
							.getProbability())) {
				return currentValue;
			}
			currentWeightSumm += currentValue.getProbability();
		}
		return Event.NOTHING;
	}
}
