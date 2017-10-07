package edu.iastate.cs228.hw2;

import java.util.Arrays;

/**
 *  
 * @author
 *
 */

import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * This abstract class is extended by SelectionSort, InsertionSort, MergeSort,
 * and QuickSort. It stores the input (later on the sorted) sequence and records
 * the employed sorting algorithm, the comparison method, and the time spent on
 * sorting.
 *
 */

public abstract class AbstractSorter {

	protected Point[] points; // Array of points operated on by a sorting algorithm.
								// The number of points is given by points.length.

	protected String algorithm = null; // "selection sort", "insertion sort",
										// "merge sort", or "quick sort". Initialized by a subclass
										// constructor.
	protected boolean sortByAngle; // true if last sort was done by polar angle and false
									// if by x-coordinate

	protected String outputFileName; // "select.txt", "insert.txt", "merge.txt", or "quick.txt"

	protected long sortingTime; // execution time in nanoseconds.

	protected Comparator<Point> pointComparator; // comparator which compares polar angle if
													// sortByAngle == true and x-coordinate if
													// sortByAngle == false

	private Point lowestPoint; // lowest point in the array, or in case of a tie, the
								// leftmost of the lowest points. This point is used
								// as the reference point for polar angle based comparison.

	// Add other protected or private instance variables you may need.

	protected AbstractSorter() {
		// No implementation needed. Provides a default super constructor to subclasses.
		// Removable after implementing SelectionSorter, InsertionSorter, MergeSorter,
		// and QuickSorter.
//		points = new Point[0];
	}

	/**
	 * This constructor accepts an array of points as input. Copy the points into
	 * the array points[]. Sets the instance variable lowestPoint.
	 * 
	 * @param pts
	 *            input array of points
	 * @throws IllegalArgumentException
	 *             if pts == null or pts.length == 0.
	 */
	protected AbstractSorter(Point[] pts) throws IllegalArgumentException {
		points = new Point[pts.length];
		lowestPoint = pts[0];
		
		if (pts.length == 0)
			throw new IllegalArgumentException();

		for (int i = 0; i < pts.length; i++) {
			points[i] = pts[i]; // copy

			if (points[i].compareTo(lowestPoint) == -1) {
				lowestPoint = points[i]; // keeps lowestPoint current
			} else if (points[i].compareTo(lowestPoint) == 0) { // tie case
				if (points[i].getX() < lowestPoint.getX())
					points[i] = lowestPoint;
			}
		}
	}

	/**
	 * This constructor reads points from a file. Sets the instance variables
	 * lowestPoint and outputFileName.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException
	 *             when the input file contains an odd number of integers
	 */
	protected AbstractSorter(String inputFileName) throws FileNotFoundException, InputMismatchException {
		
		File input = new File(inputFileName);		
		String nl = "";
		
		if (input.exists()) {
			
			try (Scanner sc = new Scanner(input)) {

				while (sc.hasNextLine()) {
					nl += sc.nextLine() + " ";
				}
				nl = nl.substring(0, nl.length() - 1);
				
				String[] strarr;
				strarr = nl.split(" ");
				
				int[] intarr = new int[strarr.length];
				try {
					for (int i = 0; i < strarr.length; i++) {
						intarr[i] = Integer.parseInt(strarr[i]);
					}
				} catch (NumberFormatException ex) {
					System.err.println("Invalid input.");
				}
				
				int numOfPts = intarr.length / 2;
				if (intarr.length % 2 != 0 || intarr.length == 0)
					throw new InputMismatchException("Incomplete point.");
				
				points = new Point[numOfPts];
				lowestPoint = new Point(intarr[0], intarr[1]);
				
				int index = 0;
				
				for (int i = 0; i < numOfPts; i++) {
					points[i] = new Point(intarr[index++], intarr[index++]);
					
					if (points[i].compareTo(lowestPoint) == -1) {
						lowestPoint = points[i]; // keeps lowestPoint current
					} else if (points[i].compareTo(lowestPoint) == 0) { // tie case
						if (points[i].getX() < lowestPoint.getX())
							points[i] = lowestPoint;
					}
				}
				System.out.println(Arrays.toString(intarr));
			}
		} else {
			throw new FileNotFoundException("File " + input + " does not exist!");
		}
		System.out.println(Arrays.toString(points));
	}

	/**
	 * Sorts the elements in points[].
	 * 
	 * a) in the non-decreasing order of x-coordinate if order == 1 b) in the
	 * non-decreasing order of polar angle w.r.t. lowestPoint if order == 2
	 * (lowestPoint will be at index 0 after sorting)
	 * 
	 * Sets the instance variable sortByAngle based on the value of order. Calls the
	 * method setComparator() to set the variable pointComparator and use it in
	 * sorting. Records the sorting time (in nanoseconds) using the
	 * System.nanoTime() method. (Assign the time to the variable sortingTime.)
	 * 
	 * @param order
	 *            1 by x-coordinate 2 by polar angle w.r.t lowestPoint
	 *
	 * @throws IllegalArgumentException
	 *             if order is less than 1 or greater than 2
	 */
	public abstract void sort(int order) throws IllegalArgumentException;

	/**
	 * Outputs performance statistics in the format:
	 * 
	 * <sorting algorithm> <size> <time>
	 * 
	 * For instance,
	 * 
	 * selection sort 1000 9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the assignment description.
	 */
	public String stats() {
		String clss = this.getClass().toString();
		clss = clss.substring(6);
		System.out.println(clss);
		String[] cStr = clss.split(".");
		System.out.println(Arrays.toString(cStr));
		algorithm = cStr[cStr.length];
		return this.getClass().toString();
	}

	/**
	 * Write points[] to a string. When printed, the points will appear in order of
	 * increasing index with every point occupying a separate line. The x and y
	 * coordinates of the point are displayed on the same line with exactly one
	 * blank space in between.
	 */
	@Override
	public String toString() {
		String pstr = "";
		for (Point p : points) {
			pstr += p.toString()+"\n";
		}
		return pstr;
	}

	/**
	 * 
	 * This method, called after sorting, writes point data into a file by
	 * outputFileName. It will be used for Mathematica plotting to verify the
	 * sorting result. The data format depends on sortByAngle. It is detailed in
	 * Section 4.1 of the assignment description assn2.pdf.
	 * 
	 * @throws FileNotFoundException
	 */
	public void writePointsToFile() throws FileNotFoundException, IOException {
		FileWriter wr = new FileWriter(outputFileName);
		
		if (!sortByAngle) {
			
			for (Point point : points) {
				wr.write(point.getX() + " " + point.getY() + "\n");
			}
		}
		else {
			wr.write(points[0].getX() + " " + points[0].getY() + "\n");
			for (int i = 1; i < points.length; i++) {
				wr.write(points[i].getX() + " " + points[i].getY() + " "
						+ points[0].getX() + " " + points[0].getY() + " "
						+ points[i].getX() + " " + points[i].getY() + "\n");
			}
		}
		
		wr.flush();
		wr.close();
	}

	/**
	 * Generates a comparator on the fly that compares by polar angle if sortByAngle
	 * == true and by x-coordinate if sortByAngle == false. Set the protected
	 * variable pointComparator to it. Need to create an object of the
	 * PolarAngleComparator class and call the compareTo() method in the Point
	 * class, respectively for the two possible values of sortByAngle.
	 * 
	 */
	protected void setComparator() {
		
		Comparator<Point> comp = new Comparator<Point>() {
			
			PolarAngleComparator pac = new PolarAngleComparator(new Point());
			
			
			@Override
			public int compare(Point x, Point y) {
				if(sortByAngle)
					return pac.comparePolarAngle(x, y);
				else
					return x.compareTo(y);
			}
		};
		
		pointComparator = comp;
	}

	/**
	 * Swap the two elements indexed at i and j respectively in the array points[].
	 * 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j) {
		Point tmp = points[i];
		points[i] = points[j];
		points[j] = tmp;
	}
	
	public Point[] getSortedPoints() {
		return points;
	}
}
