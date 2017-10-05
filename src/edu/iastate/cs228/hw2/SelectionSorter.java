package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 


/**
 *  
 * @author
 *
 */

/**
 * 
 * This class implements selection sort.   
 *
 */

public class SelectionSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	private Point[] points;
	String inputFile;
	
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/**
	 * Constructor takes an array of points.
	 *  
	 * @param pts  
	 */
	public SelectionSorter(Point[] pts)  
	{
		super(pts);
		points = pts;
	}	

	
	/**
	 * Constructor reads points from a file.
	 * @throws FileNotFoundException
	 * 
	 * @param inputFileName  name of the input file
	 */
	public SelectionSorter(String inputFileName) throws FileNotFoundException
	{
		super(inputFileName);
		inputFile = inputFileName;
	}
	
	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
	 *
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
	 *
	 */
	@Override 
	public void sort(int order)
	{
		switch (order) {
		case 1: 
			for (int i = 0; i < points.length - 1; i++) {
				int index = i;
				for (int j = i+1; j < points.length; j++) {
					if (points[j].compareTo(points[index]) == -1) {
						index = j;
					}
					Point small = points[index];
					points[index] = points[i];
					points[i] = small;
				}
			}
			break;
			
		case 2: 
			// TODO
			break;
		default: 
			System.err.println("Invalid order");
		}
	}	
}
