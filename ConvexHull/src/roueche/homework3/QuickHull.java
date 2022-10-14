package roueche.homework3;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class QuickHull {

	public static void main(String[] args) {
		ArrayList<Point> points = new ArrayList<Point>();
		ArrayList<Point> convex = new ArrayList<Point>();
		
		Random rand = new Random();
		
		for( int i = 0; i < 800; i++) {    
		    int x = rand.nextInt(450);
		    int y = rand.nextInt(450);
		    points.add(new Point(x, y));
		}
		
		StdDraw.setCanvasSize(500, 500);
		StdDraw.setXscale(0, 500);
		StdDraw.setYscale(0, 500);
		
		StdDraw.setPenColor(Color.blue);
		
		for(Point p: points) {
			StdDraw.filledCircle(p.x, p.y, 6);
		}
		
		double startTime = System.currentTimeMillis();
		draw(quickhullStart(points, convex));
		double endTime = System.currentTimeMillis();
		
		double runTime = (endTime - startTime);
		
		System.out.println(runTime);
		
	}
	
	public static void draw(ArrayList<Point> hull) {
		StdDraw.setPenColor(Color.red);
		Point p = hull.get(0);
		
		for(int i = 1; i < hull.size(); i++) {
			Point p2 = hull.get(i);
			StdDraw.line(p.x, p.y, p2.x, p2.y);
			p = p2;
		}
	}
	
	public static int distance(Point p, Point q, Point r) {
		int v1x = q.x - p.x;
		int v1y = q.y - p.y;
		int v2x = r.x - p.x;
		int v2y = r.y - p.y;
		return Math.abs((v1x * v2y) - (v1y * v2x));
	}
	
	public static ArrayList<Point> quickhullStart(ArrayList<Point> points, ArrayList<Point> results) {
		Point xMin = xMin(points);
		Point xMax = xMax(points);
		
		ArrayList<Point> sUpper = new ArrayList<>();
		ArrayList<Point> sLower = new ArrayList<>();
		ArrayList<Point> upperHull = new ArrayList<>();
		ArrayList<Point> lowerHull = new ArrayList<>();
		
		for(Point p: points) {
			if(leftTurn(xMin, xMax, p)) {
				sUpper.add(p);
			} else {
				sLower.add(p);
			}
		}
		
		
		
		lowerHull = quickhull(sLower, xMin, xMax, lowerHull);
		upperHull = quickhull(sUpper, xMin, xMax, upperHull);
		
		results.add(xMin);
		results.addAll(upperHull);
		results.add(xMax);
		results.addAll(lowerHull);
		
		return results;
	}
	
	public static ArrayList<Point> quickhull(ArrayList<Point> points, Point min, Point max, ArrayList<Point> result) {
		if(points.size() != 0) {
			int furthest = distance(min, max, points.get(0));
			int count = 0;
			
			ArrayList<Point> left = new ArrayList<>();
			ArrayList<Point> right = new ArrayList<>();
			
			for(int i = 1; i < points.size(); i++) {
				int temp = distance(min, max, points.get(i));
				if(furthest < temp) {
					furthest = temp;
					count = i;
				}
	 		}
			
			Point maxPoint = points.get(count);
			furthest = 0;
			points.remove(count);
			
			for(Point p: points) {
				if(leftTurn(min, maxPoint, p)) {
					left.add(p);
				} else if(!leftTurn(max, maxPoint, p)) {
					right.add(p);
				}
			}
	
			quickhull(left, min, maxPoint, result);
			result.add(maxPoint);
			quickhull(right, maxPoint, max, result);
		}
		
		return result;
		
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
	
	public static Point xMax(ArrayList<Point> points) {
		Point max = points.get(0);
		for(int i = 1; i < points.size(); ++i) {
			if(points.get(i).x > max.x) {
				max = points.get(i);
			}
		}
		return max;
	}
	
	public static Point xMin(ArrayList<Point> points) {
		Point min = points.get(0);
		for(int i = 1; i < points.size(); ++i) {
			if(points.get(i).x < min.x) {
				min = points.get(i);
			}
		}
		return min;
	}
}
