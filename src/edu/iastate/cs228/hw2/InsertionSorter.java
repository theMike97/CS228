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
 * This class implements insertion sort.   
 *
 */

public class InsertionSorter extends AbstractSorter 
{
	// Other private instance variables if you need ...
	Point[] points;
	
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/**
	 * Constructor takes an array of points. 
	 * 
	 * @param pts  
	 */
	public InsertionSorter(Point[] pts) 
	{
		super(pts);
		points = super.points;
	}	

	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 */
	public InsertionSorter(String inputFileName) throws FileNotFoundException 
	{
		super(inputFileName);
		points = super.points;
	}
	
	
	/** 
	 * Perform insertion sort on the array points[] of the parent class AbstractSorter.  
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
	 */
	@Override 
	public void sort(int order)
	{
		
		sortByAngle = (order == 2) ? true : false;
		setComparator();
		
		int size = points.length;
		for (int i = 1; i < size; i++) {
			Point ref = points[i];
			int j = i - 1;
			
			while (j >= 0 && pointComparator.compare(points[j],ref) == 1) {
			//while (j >= 0 && points[j].compareTo(ref) == 1) {
				points[j+1] = points[j];
				j--;
			}
			points[j+1] = ref;
		}
	}
}
