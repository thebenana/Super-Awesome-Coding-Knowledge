package roueche.homework3;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class ConvexHullBrute {
	
	public static void main(String args[]) {
		
		ArrayList<Point> coordinates = new ArrayList<Point>();
		ArrayList<Point> convex = new ArrayList<Point>();
		
		Random rand = new Random();
		for( int i = 0; i < 800; i++) {    
		    int x = rand.nextInt(450);
		    int y = rand.nextInt(450);
		    coordinates.add(new Point(x, y));
		}
		
		StdDraw.setCanvasSize(500, 500);
		StdDraw.setXscale(0, 500);
		StdDraw.setYscale(0, 500);
		
		StdDraw.setPenColor(Color.blue);
		
		for(Point p: coordinates) {
			StdDraw.filledCircle(p.x, p.y, 6);
		}
		
		double startTime = System.currentTimeMillis();
		convex = convexHull(coordinates);
		double endTime = System.currentTimeMillis();
		
		double runTime = (endTime - startTime);
		
		System.out.println(runTime);
		
		
		for(int i = 0; i < convex.size(); i += 2) {
			Point p = convex.get(i);
			Point p2 = convex.get(i + 1);
			StdDraw.setPenColor(Color.red);
			StdDraw.line(p.x, p.y, p2.x, p2.y);
		}
		
		
	}
	
	public static ArrayList<Point> convexHull(ArrayList<Point> points) {
		
		ArrayList<Point> convexContainer = new ArrayList<>();
		
		for(Point pi: points) {
			for(Point pj: points) {
				if(pj != pi) {
					int leftTurnCount = 0;
					for(Point pk: points) {
						if(pk != pi && pk != pj) {
							if (leftTurn(pi, pj, pk)) {
								leftTurnCount += 1;
							}
						}
					}
					if(leftTurnCount == 0 || leftTurnCount == points.size() - 2) {
						convexContainer.add(pi);
						convexContainer.add(pj);
					}
				}
				
			}
		}
		return convexContainer;
		
	}
	
	private static boolean leftTurn(Point a, Point b, Point i) {
		
		int v1x, v1y, v2x, v2y;
		
		v1x = b.x - a.x;
		v1y = b.y - a.y;
		
		v2x = i.x - b.x;
		v2y = i.y - b.y;
		
		int crossProduct = (v1x * v2y) - (v1y * v2x);
		
		return crossProduct > 0;
	}
	
	
}
