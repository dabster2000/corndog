package dk.corndog.utils;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import dk.corndog.model.race.TrackSegment;

public class ReadXMLFile {

	private static ArrayList<TrackSegment> trackSegments = new ArrayList<TrackSegment>();
	
	//private static XYSeries series = new XYSeries("AlpeDHuez");
	
	public static void main(String[] args) {
		ArrayList<TrackSegment> track = getTrack("");
		
		System.out.println("track.size(): " + track.size());
		double totalDistance = 0.0;
		for (TrackSegment trackSegment : track) {
			System.out.println(trackSegment);
			totalDistance += trackSegment.getDistance();
		}
		System.out.println("totalDistance: " +totalDistance);
		System.out.println(new ReadXMLFile().getTrackSegmentBasedOnDistance(track, 3.0));
	}

	private TrackSegment getTrackSegmentBasedOnDistance(List<TrackSegment> trackSegments, double distance) {
		double trackSegmentLength = trackSegments.get(0).getDistance();
		TrackSegment relevantTrackSegment = trackSegments.get(0);
		for (TrackSegment trackSegment : trackSegments) {
			trackSegmentLength += trackSegment.getDistance();
			if(distance < trackSegmentLength) {
				return trackSegment;
			}
		}
		return relevantTrackSegment;
	}
	
	public static ArrayList<TrackSegment> getTrack(final String raceUUID) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			DefaultHandler handler = new DefaultHandler() {

				double minHeight = 0.0;
				double maxHeight = 0.0;
				double distance = 0.0;
				double previousX;
				
				double minX, maxX, minY, maxY = 0.0;
			
				List<Point2D.Double> chart = new ArrayList<Point2D.Double>();
				
				Point2D.Double lastPoint;
				
				int i = 0;
				public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
					
					if (qName.equalsIgnoreCase("LINE")) {
						double x1 = Double.parseDouble(attributes.getValue("x1"));
						double y1 = Double.parseDouble(attributes.getValue("y1"));
						double x2 = Double.parseDouble(attributes.getValue("x2"));
						double y2 = Double.parseDouble(attributes.getValue("y2"));
						
						if(i == 0) {
							minX = x2;
							minY = y2;
							previousX = x2;
						}
						
						minX = (x1 < minX)?x1:minX;
						maxX = (x2 > maxX)?x2:maxX;
						minY = (y1 < minY)?y1:minY;
						maxY = (y2 > maxY)?y2:maxY;
						
						if(x2 > previousX) {
							chart.add(new Point2D.Double(x2, y2));
							previousX = x2;
						}
						lastPoint = new Point2D.Double(x2, y2);
						i++;
					} else if (qName.equalsIgnoreCase("SVG")) {
						minHeight = Double.parseDouble(attributes.getValue("minHeight"));
						maxHeight = Double.parseDouble(attributes.getValue("maxHeight"));
						distance = Double.parseDouble(attributes.getValue("distance"));
					}
					
				}
				
				@Override
				public void endDocument() throws SAXException {
					chart.add(lastPoint);

					int prev = 0;
					double tempDistance = 0.0;
					for (int i = 0; i < chart.size(); i++) {
						int minI = (i < 15) ? 0 : i-15;
						int maxI = ((i + 15) > (chart.size() - 1)) ? chart.size() : i + 15;
						boolean high = true;
						boolean low = true;
						for (int j = minI; j < maxI; j++) {
							if(chart.get(j).y > chart.get(i).y) high = false;
							if(chart.get(j).y < chart.get(i).y) low = false;
						}
						if(high || low) {
							TrackSegment ts = new TrackSegment(chart.get(i).x - chart.get(prev).x, tempDistance += (chart.get(i).x - chart.get(prev).x), chart.get(i).y, chart.get(prev).y, 15.0, 0.0, 20.0);
							System.out.println((tempDistance += ts.getDistance())+" "+ts.getHeight());
							trackSegments.add(ts);
							prev = i;
						}
					}
					
					ArrayList<TrackSegment> newTrackSegments = new ArrayList<TrackSegment>();
					System.out.println(distance +" "+ maxX +" "+ minX);
					double totalDistance = 0.0;
					for (TrackSegment trackSegment : trackSegments) {
						double transformedDistance = trackSegment.getDistance() * (distance / (maxX - minX));
						totalDistance += transformedDistance;
						TrackSegment segment = new TrackSegment(trackSegment.getDistance() * (distance / (maxX - minX)), totalDistance,
								(maxHeight - trackSegment.getHeight() * (maxHeight / (maxY - minY))) + minHeight, 
								(maxHeight - trackSegment.getPreviousHeight() * (maxHeight / (maxY - minY))) + minHeight, 0.0, 0.0, 20.0);
						newTrackSegments.add(segment);
					}
					trackSegments = newTrackSegments;
					
				}
			};
			
			saxParser.parse(ReadXMLFile.class.getResourceAsStream("alpedhuez2.xml"), handler);
			
			System.out.println("HERE GOES ---");
			for (TrackSegment trSegment : trackSegments) {
				System.out.println(trSegment);
			}
			System.out.println("HERE GOES ---");
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return trackSegments;
	}
	
	public static double[] convertDoubles(List<Double> doubles)
	{
	    double[] ret = new double[doubles.size()];
	    
	    int i = 0;
	    for (double d : ret) {
			ret[i++] = d;
		}
	    return ret;
	}
}