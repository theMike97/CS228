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
	 **/
	public static void main(String[] args) 
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
		
		Point[] pts = {new Point(0,0), new Point(-3,-9), new Point(0,-10), new Point(8,4), new Point(3,3)};
		
		InsertionSorter is = new InsertionSorter(pts);
		SelectionSorter ss = new SelectionSorter(pts);
		MergeSorter ms = new MergeSorter(pts);
		QuickSorter qs = new QuickSorter(pts);
		
		sorters[0] = is;
		sorters[0].sort(1);
		
		sorters[1] = ss;
		sorters[1].sort(1);
		
		sorters[2] = ms;
//		sorters[2].sort(2);
		
		sorters[3] = qs;
//		sorters[3].sort(2);
		
		System.out.println("\nIS" + Arrays.toString(sorters[0].getSortedPoints()));
		System.out.println("SS" + Arrays.toString(sorters[1].getSortedPoints()));
//		System.out.println("MS" + Arrays.toString(sorters[2].getSortedPoints()));
//		System.out.println("QS" + Arrays.toString(sorters[3].getSortedPoints()));
				
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
		Point[] points = new Point[numPts];
		for (int i = 0; i < numPts; i++) {
			Point pt = new Point((int)(Math.random()*100-49), (int)(Math.random()*100-49));
			points[i] = pt;
		}
		return points;
	}
}
