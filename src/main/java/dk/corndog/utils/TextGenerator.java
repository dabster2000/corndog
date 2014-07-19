package dk.corndog.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.stereotype.Component;

import dk.corndog.model.race.Group;
import dk.corndog.model.race.TrackSegment;
import dk.corndog.model.team.Biker;

@Component
public class TextGenerator {

	private static Random r = new Random();
	
	private List<String> startTexts = new ArrayList<String>();
	private List<String> earlyStartTexts = new ArrayList<String>();
	private List<String> crashTexts = new ArrayList<String>();
	private List<String> groupTexts = new ArrayList<String>();
	private List<String> staminaTexts = new ArrayList<String>();
	private List<String> commentaryTexts = new ArrayList<String>();
	private List<String> attackTexts = new ArrayList<String>();
	private List<String> trackChangeTexts = new ArrayList<String>();

	public TextGenerator() {
		earlyStartTexts.add("The riders pass through the 0KM zero mark and Prudhomme throws down the flag.");
		earlyStartTexts.add("The sprinters' teams should have plenty of time to reel in any escape before getting down to the serious work of setting up their speedsters for a final fling that will reward the winner with at least one day in yellow. Mark Cavendish will be heavily tipped to take one of the two wins he needs to join Andr� Leducq at third in the all-time list with 25.");
		earlyStartTexts.add("The weather is warm (around ${temperature} degrees), sunny and there's a ${windspeed}.");
		earlyStartTexts.add("It'll be a tailwind on the way out for the riders, and then a slight headwind on the way back - but it doesn't amount to much");
		earlyStartTexts.add("We reckon the top team will tuck away's today's route in around the 25-26 minute mark");
		
		startTexts.add("Good morning, you join us from Porto-Vecchio in Corsica for stage 1 of this year's Tour de France. Unlike most years the Tour kicks off with a road stage this time around, no prologues or time trials, instead ${courseLength} km jaunt from Porto-Vecchio to Bastia.");
		
		commentaryTexts.add("Things have since settled down, with a break established full of riders of no threat to the GC. Full list of names will follow shortly.");
		commentaryTexts.add("${avgSpeed}kmh average for first hour.");
		commentaryTexts.add("Couple of Euskaltels having a go. Short-lived effort.");
		commentaryTexts.add("Peloton bunching up - gap up to 6'17");
		commentaryTexts.add("Looks like ${weather} on the alpe from that pic on twitter");
		commentaryTexts.add("No rain at the moment, but rain is forecasted. Regardless of the conditions though, the race organisers say the route will not be altered.");
		commentaryTexts.add("The ${teamName} pair remain in complete no mans land. Saxo's tactics not working at the moment");
		commentaryTexts.add("Now ${teamName} amass at the front. Expect to see these two teams at the front a lot over the next three weeks");
		commentaryTexts.add("${kmLeft}km to go and ${bikerName} had a pop, but he's quickly caught by a few riders");
		
		crashTexts.add("A huge huge crash and ${bikerName} is there.");
		crashTexts.add("Oh, goodness, more drama - a big crash! ${bikerName} and ${bikerName} involved, the bunch in pieces, Lotto at the front, but no sign of Griepel"); 
		
		groupTexts.add("The ${groupName} group has split to pieces.");
		
		attackTexts.add("${bikerName} ATTACKS!");
		
		staminaTexts.add("${bikerName} falls back");
		
		trackChangeTexts.add("A little war cry from one of the ${teamName} riders then, waving his hands in the air as he approached the climb. Gap down to less than six minutes though");
		trackChangeTexts.add("That pace should blow the bunch to pieces when they hit the ${grade} ramp at the first corner");
	}
	
	public String getStartText(String length, String time) {
		Map<String, String> valuesMap = new HashMap<String, String>();
		valuesMap.put("courseLength", length);
		valuesMap.put("courseStartTime", time);
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		return sub.replace(startTexts.get(r.nextInt(startTexts.size())));
	}
	
	public String getEarlyStartText() {
		if(earlyStartTexts.size()==0) return "";
		return earlyStartTexts.remove(r.nextInt(earlyStartTexts.size()));
	}
	
	public String getCrashText(Group crashedGroup) {
		Map<String, String> valuesMap = new HashMap<String, String>();
		valuesMap.put("bikerName", crashedGroup.bikers.get(r.nextInt(crashedGroup.bikers.size())).name);
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		return sub.replace(crashTexts.get(r.nextInt(crashTexts.size())));
	}
	
	public String getStaminaText(Biker biker) {
		Map<String, String> valuesMap = new HashMap<String, String>();
		valuesMap.put("bikerName", biker.name);
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		return sub.replace(staminaTexts.get(r.nextInt(staminaTexts.size())));
	}
	
	public String getAttackText(Biker biker) {
		Map<String, String> valuesMap = new HashMap<String, String>();
		valuesMap.put("bikerName", biker.name);
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		return sub.replace(attackTexts.get(r.nextInt(attackTexts.size())));
	}
	
	public String getGroupText(String groupName) {
		Map<String, String> valuesMap = new HashMap<String, String>();
		valuesMap.put("groupName", groupName);
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		return sub.replace(groupTexts.get(r.nextInt(groupTexts.size())));
	}
	
	public String getCommentaryText() {
		Map<String, String> valuesMap = new HashMap<String, String>();
		//valuesMap.put("groupName", groupName);
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		return sub.replace(commentaryTexts.get(r.nextInt(commentaryTexts.size())));
	}
	
	public String getTrackChangeText(TrackSegment ts) {
		if(trackChangeTexts.size()==0) return "";
		Map<String, String> valuesMap = new HashMap<String, String>();
		valuesMap.put("grade", Math.round(ts.getGrade())+"%");
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		return sub.replace(trackChangeTexts.remove(r.nextInt(trackChangeTexts.size())));
	}
}
