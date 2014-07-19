package dk.corndog.utils;

import java.util.Collection;
import java.util.Random;

public class WeightedRandomGenerator<T> {
	
	private final static Random RANDOM = new Random();
	
	private Collection<Weight<T>> weights;
	private double size;
	
	public static class Weight<T> {
		public double weight;
		public T value;
		
		public Weight(double weight, T value) {
			this.weight = weight;
			this.value = value;
		}
	}
	
	public WeightedRandomGenerator(Collection<Weight<T>> weights) {
		this.weights = weights;
		for (Weight<T> weight : weights) {
			size += weight.weight;
		}
	}
	
	public T getRandomValue() {
		double randomNum = RANDOM.nextDouble()*size;
		double currentWeightSumm = 0;
			for (Weight<T> currentValue : weights) {
			if (randomNum > currentWeightSumm
					&& randomNum <= (currentWeightSumm + currentValue.weight)) {
				return currentValue.value;
			}
			currentWeightSumm += currentValue.weight;
		}
		return null;
	}
	
}
