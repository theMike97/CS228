package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException;
import java.util.Random;

/**
 *  
 * @author
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if you need ...
	Point[] points;
	String fileName;
	
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/** 
	 * Constructor accepts an input array of points. 
	 * in the array. 
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		points = super.points;
	}
	
	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 */
	public MergeSorter(String inputFileName) throws FileNotFoundException
	{
		super(inputFileName);
		points = super.points;
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
	 *
	 */
	@Override 
	public void sort(int order)
	{
		sortByAngle = (order == 2) ? true : false;
		setComparator();
		
		merge(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array
	 * @param tmp   point array
	 * @param first int smallest index of array
	 * @param last	int largest index of array
	 */
	private void mergeSortRec(Point[] pts, Point[] tmp, int first, int last)
	{
		if (first < 0 || last >= pts.length)
			throw new IllegalArgumentException("Index out of bounds");
		
		if (first >= last) return; // base case
		
		int i, j, k;
		int mid = (first + last) / 2;
		
		mergeSortRec(pts, tmp, first, mid); // sort first half
		mergeSortRec(pts, tmp, mid+1, last); // sort second half
		
		// combine sorted arrays
		k = first;
		for (i = first, j = mid+1; i <= mid && j <= last; ) {
//			if (pts[i].compareTo(pts[j]) == -1)
			if (pointComparator.compare(pts[i], pts[j]) == -1) 
				tmp[k++] = pts[i++];
			else
				tmp[k++] = pts[j++];
		}
		
		while (i <= mid) {
			tmp[k++] = pts[i++];
		}
		while (j <= last) {
			tmp[k++] = pts[j++];
		}
		if (k != last + 1) throw new RuntimeException("Index Error!");
		
		for (k = first; k <= last; k++) {
			pts[k] = tmp[k];
		}
	}

	
	// Other private methods in case you need ...
	
	/**
	 * This method instantiates a temporary Point[] array
	 * and calls mergeSortRec
	 * 
	 * @param pts
	 */
	private void merge(Point[] pts) {
		if (pts == null || pts.length == 0) throw new IllegalArgumentException("Invalid array");
		
		int t;
		Point[] tmp = new Point[pts.length];
		mergeSortRec(pts, tmp, 0, pts.length - 1);
	}

}
