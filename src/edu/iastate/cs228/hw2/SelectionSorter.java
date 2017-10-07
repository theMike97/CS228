package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.util.Arrays;
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
		points = super.points;
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
		points = super.points;
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
		sortByAngle = (order == 2) ? true : false;
		setComparator();
		
		if (points == null || points.length == 0)
			throw new IllegalArgumentException("Null pointer or 0 size");
		
		for (int i = 0; i < points.length - 1; i++) {
//			System.out.println(i);
			int min = i;
			
			for (int j = i + 1; j < points.length; j++) {
				
//				System.out.println(points[min] + ", " + points[j]);
//				System.out.println(pointComparator.compare(points[j], points[index]) == -1);
				
				if (pointComparator.compare(points[j], points[min]) == -1)
					min = j;
			}
			swap(min, i);
		}
	}
}
