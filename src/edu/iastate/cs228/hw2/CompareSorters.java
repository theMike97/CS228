package edu.iastate.cs228.hw2;

/**
 *  
 * @author
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Perform the four sorting algorithms over each sequence of integers, comparing 
	 * points by x-coordinate or by polar angle with respect to the lowest point.  
	 * 
	 * @param args
	 * @throws FileNotFoundException 
	 **/
	public static void main(String[] args) throws FileNotFoundException 
	{		
		// TODO 
		// 
		// Conducts multiple sorting rounds. In each round, performs the following: 
		// 
		//    a) If asked to sort random points, calls generateRandomPoints() to initialize an array 
		//       of random points. 
		//    b) Reassigns to elements in the array sorters[] (declared below) the references to the 
		//       four newly created objects of SelectionSort, InsertionSort, MergeSort and QuickSort. 
		//    c) Based on the input point order, carries out the four sorting algorithms in a for 
		//       loop that iterates over the array sorters[], to sort the randomly generated points
		//       or points from an input file.  
		//    d) Meanwhile, prints out the table of runtime statistics.
		// 
		// A sample scenario is given in Section 2 of the assignment description. 
		// 	
		AbstractSorter[] sorters = new AbstractSorter[4];
		
		// Within a sorting round, every sorter object write its output to the file 
		// "select.txt", "insert.txt", "merge.txt", or "quick.txt" if it is an object of 
		// SelectionSort, InsertionSort, MergeSort, or QuickSort, respectively. 
		
//		Point[] pts = {new Point(1,0), new Point(-3,-9), new Point(0,0), new Point(0,-10), new Point(8,4), new Point(3,3)};
		Random generator = new Random();
		
		Point[] pts = generateRandomPoints(1000, generator);
//		System.out.println(Arrays.toString(pts));
		
		InsertionSorter is = new InsertionSorter(pts);
		SelectionSorter ss = new SelectionSorter(pts);
		MergeSorter ms = new MergeSorter(pts);
		QuickSorter qs = new QuickSorter(pts);
		
		sorters[0] = is;
		sorters[0].sort(1);
		
		sorters[1] = ss;
		sorters[1].sort(1);
		
		sorters[2] = ms;
		sorters[2].sort(1);
		
		sorters[3] = qs;
		sorters[3].sort(1);
		
//		try {
//			sorters[0].outputFileName = "insert.txt";
//			sorters[0].writePointsToFile();
//			
//			sorters[1].outputFileName = "select.txt";
//			sorters[1].writePointsToFile();
//		} catch (IOException e) {
//			System.err.println("Oops.  There was an error writing your file!");
//		}
		
		for (AbstractSorter sorter : sorters) {
			System.out.println(Arrays.toString(sorter.getSortedPoints()));
			System.out.println(sorter.stats());
		}
	}
	
	
	/**
	 * This method generates a given number of random points to initialize randomPoints[].
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 of assignment description document on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{
		if (numPts < 1) throw new IllegalArgumentException("Not enough points!"); 
		
		Point[] points = new Point[numPts];
		for (int i = 0; i < points.length; i++) {
			points[i] = new Point(rand.nextInt(101) - 50, rand.nextInt(101) - 50);
		}
		return points;
	}
}
