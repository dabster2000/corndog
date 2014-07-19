package dk.corndog.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

import static dk.corndog.enums.SegmentType.*;
import dk.corndog.enums.SegmentType;
import dk.corndog.model.race.RaceDay;
import dk.corndog.model.race.TrackSegment;

public class Terrain {

	public int parts = 160;
	public int[] tops = new int[parts + 1];
	public int[][] important;	
	
	static Random random = new Random();
	
	public static double uniform() {
        return random.nextDouble();
    }
	
	public static void main(String[] args) {
		new Terrain().generateTerrainV2(200000, 1);
		
        /*double H = Double.parseDouble("1.1"); 
        double s = Math.pow(2, 2*H); 
        curve(0.0, 0.5, 0.8, 0.5, .01, s);*/ 
		
		//new Terrain().generateTerrain();
	}
	
	public static double exp(double lambda) {
        return -Math.log(1 - uniform()) / lambda;
    }
	
	public void generateTerrainV2(int length, int type) {
		double sectionLength = 12500.0;
		
		double[] sections = new double[(int)Math.ceil(length/sectionLength)];
		double startHeight;
		
		switch (type) {
		case 1:
			startHeight = Math.random() * 700;
			double previousHeight = startHeight;
			int mountains = 2;
			SegmentType previousSegmentType = FLAT;
			for (int i = 0; i < sections.length; i++) {
				if(mountains > 0 && previousHeight < 900 && i > 2 && previousSegmentType != MOUNTAIN && previousSegmentType != TALL_DECLINE) {
					//System.out.println("mountain");
					//double maxHeight = (end - start) / distance;
					double maxHeight = 0.09 * sectionLength + previousHeight;
					double minHeight = 0.05 * sectionLength + previousHeight;
					sections[i] = previousHeight + (Math.random() * (maxHeight - minHeight));
					previousSegmentType = MOUNTAIN;
					mountains--;
				} else  { //if (previousHeight > 900 || mountains == 0)
					if(previousSegmentType == MOUNTAIN) {
						//System.out.println("tall drop");
						double maxDecline = previousHeight - 0.07 * sectionLength;
						double minDecline = previousHeight - 0.05 * sectionLength;
						double r = Math.random();
						sections[i] = previousHeight + (r * (maxDecline - minDecline));
						previousSegmentType = TALL_DECLINE;
						//System.out.println(previousHeight +"+ ("+r+" * ("+maxDecline+" - "+minDecline+"))");
					} else {
						double r = Math.random();
						if(r <= 0.3) {
							//System.out.println("shallow drop");
							double maxDecline = previousHeight - 0.04 * sectionLength;
							double minDecline = previousHeight - 0.01 * sectionLength;
							sections[i] = previousHeight + (Math.random() * (maxDecline - minDecline));
							previousSegmentType = SHALLOW_DECLINE;
						} else if (r <= 0.6 && r > 0.3) {
							//System.out.println("flat");
							sections[i] = previousHeight;
							previousSegmentType = FLAT;
						} else if (r > 0.6) {
							double maxHeight = 0.05 * sectionLength + previousHeight;
							double minHeight = 0.01 * sectionLength + previousHeight;
							sections[i] = previousHeight + Math.random() * (maxHeight - minHeight);
							previousSegmentType = HILL;
						}
					}
				}
				if(sections[i] < 0) sections[i] = 0;
				System.out.println(sections[i]+";"+previousSegmentType+"("+((sections[i] - previousHeight)/sectionLength)+")");
				previousHeight = sections[i];				
				//StdDraw.line(i*12.5, previousHeight/10, i+1*12.5, sections[i]/10);
			}
			break;
		default:
			break;
		}
	}
	
	public ArrayList<TrackSegment> getTerrain(String raceUUID) {
		return ReadXMLFile.getTrack(raceUUID);
	}
	
	public void generateTerrain() {
		Random r = new Random();
		int mountains = r.nextInt(5) + 5;
		int part = parts / mountains;
		important = new int[parts / part][3];
		int rocky = 0;
		System.out.println("(mountains,part) = " + mountains + "," + part);
		tops[0] = 0;
		int count = 0;
		for (int n = part; n < parts; n += part) {
			int type = r.nextInt(6);
			if (type < 3) {
				tops[n] = tops[n - part] + r.nextInt(300) - 100;
				if (tops[n] > tops[n - part]) {
					important[count][0] = n;
					important[count][1] = tops[n];
					important[count][2] = new Float((100 / (new Float(part)
							.floatValue() * 250))
							* (tops[n] - tops[n - part]) * 100).intValue();
					if (important[count][2] == 0.0)
						System.out.println("here: " + count + "," + n);
					System.out.println("Slope: ("
							+ (100 / (new Float(part).floatValue() * 250))
							* (tops[n] - tops[n - part]) * 100 + ")");
					count++;
				}
				System.out.println("(n,tops[n]) = " + n + "," + tops[n]);
			} else if (type >= 3) {
				tops[n] = tops[n - part];
				System.out.println("(n,tops[n]) = " + n + "," + tops[n]);
			} 
			if (tops[n] < 0)
				tops[n] = 0;
			int slope = (tops[n] - tops[n - part]) / part;
			for (int i = n - 1; i > n - part; i--) {
				if ((tops[n] - tops[n - part]) != 0)
					rocky = r.nextInt(10) - 5;
				tops[i] = tops[n] + slope * (i - n) + rocky;
				if (tops[i] < 0)
					tops[i] = 0;
			}
			if (n + part >= parts) {
				for (int i = parts; i > n; i--) {
					if ((tops[n] - tops[n - part]) != 0)
						rocky = r.nextInt(10) - 5;
					tops[i] = tops[n] + slope * (i - n) + rocky;
					if (tops[i] < 0)
						tops[i] = 0;
				}
			}
		}
	}

	public void addTrackToRace(RaceDay race) {
		for (int n = 0; n < parts + 1; n++) {
			TrackSegment trackSegment = new TrackSegment();
			trackSegment.setHeight(tops[n]);
			trackSegment.setDistance(1);
			trackSegment.setHeadwind(0);
			trackSegment.setPosition(0);
			trackSegment.setTemperature(20.0);
			trackSegment.setPreviousHeight(tops[n]);
			if(n > 0) trackSegment.setPreviousHeight(tops[n-1]);
			race.getTrackSegments().add(trackSegment);
		}
	}

	private RenderedImage myCreateImage() {
		int width = 800;
		int height = 200;

		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 800, 200);

		for (int n = 0; n < parts; n++) {
			g2d.setColor(new Color(252, 201, 46));
			Polygon p = new Polygon();
			p.addPoint(n * 5, 199 - tops[n] / 2);
			p.addPoint((n + 1) * 5, 199 - tops[n + 1] / 2);
			p.addPoint((n + 1) * 5, 199);
			p.addPoint(n * 5, 199);

			g2d.fillPolygon(p);
		}
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, 799, 199);
		g2d.dispose();
		return bufferedImage;
	}

	public void saveImageToPNG(RaceDay race) {
		RenderedImage rendImage = myCreateImage(); // Write generated image to a
		// file
		int hashcode = race.getTrackSegments().hashCode();
		//race.image = "profile" + hashcode + ".png";
		/*
		try {
			File file = new File("d:\\profiles\\profile" + hashcode + ".png");
			ImageIO.write(rendImage, "png", file);
		} catch (IOException e) {
			System.err.println(e);
		}
		*/
	}
	
	public static String MungPass(String pass) throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		byte[] data = pass.getBytes(); 
		m.update(data,0,data.length);
		BigInteger i = new BigInteger(1,m.digest());
		return String.format("%1$032X", i);
	}

	    // midpoint displacement method
    public static void curve(double x0, double y0, double x1, double y1, double var, double s) {
        // stop if interval is sufficiently small
        if (Math.abs(x1 - x0) < .01) {
            StdDraw.line(x0, y0, x1, y1);
            return;
        }

        double xm = (x0 + x1) / 2;
        double ym = (y0 + y1) / 2;
        ym = ym + StdRandom.gaussian(0, Math.sqrt(var));
        curve(x0, y0, xm, ym, var/s, s);
        curve(xm, ym, x1, y1, var/s, s);
    } 

}
