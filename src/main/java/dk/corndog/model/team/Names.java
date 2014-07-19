package dk.corndog.model.team;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Names {
	
	private static ArrayList<Name> firstnames = new ArrayList<Name>();
	private static ArrayList<Name> lastnames = new ArrayList<Name>();
	
	private static String firstnamesFile = "player_firstnames.csv";
	private static String lastnamesFile = "player_lastnames.csv";

	public static void loadNames(String file, ArrayList<Name>names) {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
	 
		try {
			br = new BufferedReader(new InputStreamReader(Names.class.getResourceAsStream(file)));
			while ((line = br.readLine()) != null) {
				String[] country = line.split(cvsSplitBy);
				Name name = new Name(country[1].replace("\"", ""), Math.round(Float.parseFloat(country[2].replace("\"", ""))*10000));
				names.add(name);
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static int summWeigts(String filename, ArrayList<Name> names) {
		loadNames(filename, names);
		int summ = 0;
		for (Name name: firstnames) {
			summ += name.priority;
		}
		return summ;
	}

	private static final int FIRSTNAMES_SIZE = summWeigts(firstnamesFile, firstnames);
	private static final int LASTNAMES_SIZE = summWeigts(lastnamesFile, lastnames);
	private static final Random RANDOM = new Random();

	public static String randomFirstname() {
		int randomNum = RANDOM.nextInt(FIRSTNAMES_SIZE);
		int currentWeightSumm = 0;
		for (Name currentValue : firstnames) {
			if (randomNum > currentWeightSumm
					&& randomNum <= (currentWeightSumm + currentValue.priority)) {
				return currentValue.name;
			}
			currentWeightSumm += currentValue.priority;
		}
		return "";
	}
	
	public static String randomLastname() {
		int randomNum = RANDOM.nextInt(LASTNAMES_SIZE);
		int currentWeightSumm = 0;
		for (Name currentValue : lastnames) {
			if (randomNum > currentWeightSumm
					&& randomNum <= (currentWeightSumm + currentValue.priority)) {
				return currentValue.name;
			}
			currentWeightSumm += currentValue.priority;
		}
		return "";
	}
}
