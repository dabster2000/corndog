package dk.corndog.utils;

import java.util.concurrent.ConcurrentLinkedQueue;

import dk.corndog.model.race.RaceDay;

public class TaskQueue {

	public ConcurrentLinkedQueue<RaceDay> raceQueue = new ConcurrentLinkedQueue<RaceDay>();
	
	private static TaskQueue taskQueue;
	
	private TaskQueue() {
		
	}
	
	public static TaskQueue getInstance() {
		if(taskQueue==null) taskQueue = new TaskQueue(); 
			return taskQueue;
	}
	
}
