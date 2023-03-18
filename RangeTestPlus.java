package org.jfree.test;

import static org.junit.Assert.*;

import org.jfree.data.Range;
import org.junit.*;
import java.util.Random;

public class RangeTestPlus {

	//because this is the Range class, all the methods in here don't use getters or setters, instead they use the values inputed into the
		//thus we don't need to use Jmock for any of these methods to be fully tested because we are the ones entering the values

		
		private Range biggestRange = new Range(1.9, Double.MAX_VALUE);
	    private Range smallestRange = new Range(Double.MIN_VALUE, 10);
	    private Range onlyPositiveRange = new Range(1.2, 12);
	    private Range onlyNegativeRange = new Range(-12, -1.2);
	    private Range mixedRange = new Range(-12, 12);
	    private Range equalRange = new Range(12, 12);
	    private Range zeroRange = new Range(0,0);
	    
	    private double[][] rangedBoundsArray = {{1.9, Double.MAX_VALUE},
									    		{Double.MIN_VALUE, 10},
									    		{1.2, 12},
									    		{-12, -1.2},
									    		{-12, 12},
									    		{12, 12},
									    		{0,0}};
	    
	    private double[][] testNumbers = new double[][] {{-7, -1},	//true
			{1, 7},		//true
			{-7, 7},	//true
			{-15, -7},	//true
			{-19, 4},	//true
			{-9, 15},	//true
			{2, 19},	//true
			{-20, -11},	//false
			{11, 20},	//false
			{0, 0}};	//true 
	
	    private Range[] rangedArray = {biggestRange, smallestRange, onlyPositiveRange, onlyNegativeRange, mixedRange, equalRange, zeroRange};
	
		//copied form the given code in the Gitgub 
		private Range exampleRange;
		private Random random;
	    @BeforeClass public static void setUpBeforeClass() throws Exception {
	    }

	    
	    //copied form the given code in the Gitgub. 
	    //Creates a new Range object with a range of -1 to 1
	    @Before
	    public void setUpq() throws Exception { 
	    	exampleRange = new Range(-10, 10);
	    	//create an object called random to generate two random numbers where on is positive and the other is negative
	    	random = new Random();
	    }
	    
	    //getLowerBoundTest test the getLowerBound method which should return the lower bound Range
	    //in the case of Range(-1, 1) it should be -1
	    @Test
	    public void getLowerBoundTest() {
	    	
		    	//create the expected return array that covers both negative and positive numbers and 0
		    	double[] expected = new double[] {1.9, Double.MIN_VALUE, 1.2, -12, -12, 12, 0};
	            boolean fail = false;
	
	            for (int i = 0; i < 7; i++) {
	                //try the getLowerBound method
	                try {
	
	                    exampleRange = rangedArray[i];
	
	                    System.out.print(String.format("Testing Range.getLowerBound with %s as the lower bound: ", expected[i])); 
	                    assertEquals("The lower bound value for the range should be " + expected[i],
	                            expected[i], exampleRange.getLowerBound(), .000000001d);
	                    System.out.print("Test Passed \n");
	                    System.out.println(String.format("Expected \"%s\" and was \"%s\" \n", expected[i], exampleRange.getLowerBound()));
		    		
		    	}catch(AssertionError e) {	//if the AssertionError is thrown that means the expected value was not returned
		    		
		    		//not sure how to make it fail without making the getLowerBound number equal a "fake" lower bound number *********
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    		
		    	} 
	    	}	
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }

	    //getUpperBoundTest tests the getUpperBound method which should return the upper bound Range
	    //in the case of Range(-1, 1) it should be 1
	    @Test
	    public void getUpperBoundTest() {
	    	
	    	//create the expected return array that covers both negative and positive numbers and 0
	    	double[] expected = new double[] {Double.MAX_VALUE, 10, 12, -1.2, 12, 12, 0};
            boolean fail = false;

            //try the getUpperBound method
            for (int i = 0; i < 7; i++) {
                try {

                    exampleRange = rangedArray[i];

                    System.out.print(String.format("Testing Range.getUpperBound with %s as the upper bound: ", expected[i])); 
                    assertEquals("The upper bound value for the range should be " + expected[i],
                            expected[i], exampleRange.getUpperBound(), .000000001d);
                    System.out.print("Test Passed \n");
                    System.out.println(String.format("Expected \"%s\" and was \"%s\" \n", expected[i], exampleRange.getUpperBound()));
		    	
		    	} catch(AssertionError e) {	//if the AssertionError is thrown that means the expected value was not returned 
		    		
		    		//not sure how to make it fail without making the getLowerBound number equal a "fake" lower bound number *********
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    	
		    	}
	    	}
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	    //getLengthTest tests the getLength method which should returns the length between the two end points 
	    //in the case of Range(-1, 1) it should return 2 since 1 - (-1) = 2
	    @Test
	    public void getLengthTest() {
	    	/*testNumbers should look like this:
	    	 * first array = [-ve number between -100 and -50, -ve number between -50 and 0] (if both numbers are -ve)
	    	 * second array = [0,0]
	    	 * third array = [+ve number between 0 and 50, +ve number between 50 and 100] (if both numbers are +ve)
	    	 * fourth array = [-ve number between -100 and 0, +ve number between 0 and 100] (if one number is -ve and the other is +ve)
	    	 * 
	    	 * can't check if the lower bound is +ve and the upper bound is -ve because that isn't in the Parameters of the Range class
	    	 * */
	    										
	    	double[] expected = new double[7];
	    	boolean fail = false;
	    	
	    	for (int i = 0; i < 7; i++) {
	    		expected[i] = (rangedBoundsArray[i][1] - rangedBoundsArray[i][0]);
	    	}
	    	
	    	//try the getLength method
	    	for (int j = 0; j < 7; j++) {
	    		try {
	    			
	    			exampleRange = rangedArray[j];
		    		
		    		System.out.print(String.format("Testing Range.getLength with Range(%s, %s): ", rangedBoundsArray[j][0], rangedBoundsArray[j][1])); 
		    		assertEquals(String.format("The length value of \"%s\" and \"%s\" should be \"%s\"\n", rangedBoundsArray[j][0], rangedBoundsArray[j][1], expected[j]),
		    				expected[j], exampleRange.getLength(), .000000001d);
		    		System.out.print("Test Passed \n");
		    		System.out.println(String.format("Expected \"%s\" and was \"%s\" \n", expected[j], exampleRange.getLength()));
		    		
		    	}catch(AssertionError e) {	//if error was thrown then the expected value was not returned
		    		
		    		//not sure how to make it fail without making the getLowerBound number equal a "fake" lower bound number *********
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    		
		    	}
	    	}
		    
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	    //this isn't a test we created as it was copied from the Github but it does still test he getCentralValue method
	    //getCenterValueTest tests the getCenterValue method which should return the median of the two end points of the range 
	    //in the case of Range(-1, 1),  -1 to 1 should be 0
	    @Test
	    public void getCentraValueTest() {
	    	/*testNumbers should look like this:
	    	 * first array = [-ve number between -100 and -50, -ve number between -50 and 0] (if both numbers are -ve)
	    	 * second array = [0,0]
	    	 * third array = [+ve number between 0 and 50, +ve number between 50 and 100] (if both numbers are +ve)
	    	 * fourth array = [-ve number between -100 and 0, +ve number between 0 and 100] (if one number is -ve and the other is +ve)
	    	 * 
	    	 * can't check if the lower bound is +ve and the upper bound is -ve because that isn't in the Parameters of the Range class
	    	 * */
	    										
	    	double[] expected = new double[7];
	    	boolean fail = false;
	    	
	    	for (int i = 0; i < 4; i++) {
	    		expected[i] = (rangedBoundsArray[i][1] + rangedBoundsArray[i][0]) / 2;
	    	}
	    	
	    	//try the getCentraValue method
	    	for (int j = 0; j < 4; j++) {
	    		try {
	    			
	    			exampleRange = rangedArray[j];
		    		
		    		System.out.print(String.format("Testing Range.getCentraValue with Range(%s, %s): ", rangedBoundsArray[j][0], rangedBoundsArray[j][1])); 
		    		assertEquals(String.format("The central value of \"%s\" and \"%s\" should be \"%s\"\n", rangedBoundsArray[j][0], rangedBoundsArray[j][1], expected[j]),
		    				expected[j], exampleRange.getCentralValue(), .000000001d);
		    		System.out.print("Test Passed \n");
		    		System.out.println(String.format("Expected \"%s\" and was \"%s\" \n", expected[j], exampleRange.getCentralValue()));
		    		
		    	}catch(AssertionError e) {	//if error was thrown then the expected value was not returned
		    		
		    		//not sure how to make it fail without making the getLowerBound number equal a "fake" lower bound number *********
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    		
		    	}
	    	}
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	    //containsTest tests the contain method which will return True if the value sent in is in the range otherwise return false
	    //in the case of Range(-1, 1), if the value is 0 it should return true if the number is -6 it should return False
	    @Test
	    public void containsTest() {
	    	
	    	//create a testNumbers double array that will hold the values being sent into the contains method 
	    	double[] testNumbers = new double[] {-7, 16, 0};
	    	boolean fail = false;
	    	
	    	//create a expected return value
	    	boolean[] expected = new boolean[] {true, false, true};
	        
	    	//try the contains method
	    	System.out.print("Testing Range.contains with Range(-10, 10): "); 
	    	for (int i = 0; i < 3; i++) {
	    		try {
		    		
		    		assertEquals(String.format("The boolean value expected for \"%s\" is \"%s\"\n", testNumbers[i], expected[i]),
		    		        expected[i], exampleRange.contains(testNumbers[i]));
		    		System.out.print("Test Passed \n");
		    		System.out.println(String.format("Expected \"%s\" and was \"%s\" for %s\n", expected[i], exampleRange.contains(testNumbers[i]), testNumbers[i]));
		    		
		    	}catch(AssertionError e) {	//if error was thrown then the expected value was not returned
		    		
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    		
		    	}
	    	} 	
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	    //intersectsWithBoundsTest is testing the intersects method which should return true if the range intersects with the specified bounds
	    //other wise it should return false
	    //so if we sent in a lower bound of -15 and upper bound of -5 it intersects with Range(-10, 10) thus it will return true
	    @Test
	    public void intersectsWithBoundsTest() {
	    	
	    	//create a expected return value
	    	boolean[] expected = new boolean[] {true, true, true, true, true, true, true, false, false, true};
	    	boolean fail = false;
	    	
	    	//try the contains method
	    	System.out.print("Testing Range.intersects with Range(-10, 10) using lower bound and upper bound numbers: \n"); 
	    	for (int i = 0; i < 10; i++) {
	    		try {
		    		
		    		assertEquals(String.format("The boolean value expected for lower bound of \"%s\" and upper bound of \"%s\" is \"%s\"\n", testNumbers[i][0], testNumbers[i][1], expected[i]),
		    		        expected[i], exampleRange.intersects(testNumbers[i][0], testNumbers[i][1]));
		    		System.out.print(String.format("Test Passed for lower bound of %s and upper bound of %s \n", testNumbers[i][0], testNumbers[i][1]));
		    		System.out.println(String.format("Expected \"%s\" and was \"%s\" \n", expected[i], exampleRange.intersects(testNumbers[i][0], testNumbers[i][1])));
		    		
		    	}catch(AssertionError e) {	//if error was thrown then the expected value was not returned
		    		
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    		
		    	}
	    	} 	
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	    //intersectsWithRangesTest is testing the intersects method which should return true if the range intersects with the specified range
	    //other wise it should return false
	    //so if we sent in a range of -15, -5 it intersects with Range(-10, 10) thus it will return true
	    @Test
	    public void intersectsWithRangesTest() {
	    	
	    	//create a expected return value
	    	boolean[] expected = new boolean[] {true, true, true, true, true, true, true, false, false, true};
	    	Range temp;
	    	boolean fail = false;
	    	
	    	//try the contains method
	    	System.out.print("Testing Range.intersects with Range(-10, 10) using another Range: \n"); 
	    	for (int i = 0; i < 10; i++) {
	    		try {
	    			
	    			temp = new Range(testNumbers[i][0], testNumbers[i][1]);
		    		
		    		assertEquals(String.format("The boolean value expected for Range(%s, %s) is \"%s\" \n", testNumbers[i][0], testNumbers[i][1], expected[i]),
		    		        expected[i], exampleRange.intersects(temp));
		    		System.out.print(String.format("Test Passed for Range(%s, %s) \n", testNumbers[i][0], testNumbers[i][1]));
		    		System.out.println(String.format("Expected \"%s\" and was \"%s\" \n", expected[i], exampleRange.intersects(temp)));
		    		
		    	}catch(AssertionError e) {	//if error was thrown then the expected value was not returned
		    		
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    		
		    	}
	    	} 
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	    //constrainTest is testing the constrain method which should return a value within the range that is closes to the value 
	    //sent into the function
	    //so if we sent in a value of -15 and the Range is (-10, 10) the method should return -10 
	    //another case is if we sent in a value of 9 and the Range is (-10, 10) the method should return 9
	    @Test
	    public void constrainTest() {
	    	
	    	//create a testNumbers double array that will hold the values being sent into the contains method 
	    	double[] testNumbers = new double[] {-15, 0, 9, 15};
	    	
	    	//create a expected return value
	    	double[] expected = new double[] {-10, 0, 9, 10};
	    	boolean fail = false;
	    	
	    	//try the contains method
	    	System.out.print("Testing Range.constrain with Range(-10, 10): \n"); 
	    	for (int i = 0; i < 4; i++) {
	    		try {
		    		
		    		assertEquals(String.format("The return value expected is \"%s\" for \"%s\" \n", expected[i], testNumbers[i]),
		    		        expected[i], exampleRange.constrain(testNumbers[i]),  .000000001d);
		    		System.out.print(String.format("Test Passed with the input being \"%s\" \n", testNumbers[i]));
		    		System.out.println(String.format("Expected \"%s\" and was \"%s\" \n", expected[i], exampleRange.constrain(testNumbers[i])));
		    		
		    	}catch(AssertionError e) {	//if error was thrown then the expected value was not returned
		    		
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    		
		    	}
	    	} 
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	    //combineTest tests the combine method in Range which will return the combined range of two existing ranges
	    //NOTE: either range can be null and if both are null the return value should be null
	    //in case of Range(-10, 10) and Range (-10, 10) the return should be Range(-20, 20) 
	    //in case of Range(null) and Range(-10,10) return should be Range(-10, 10)
	    //in case of Range(null) and Range(null) return should be Range(null)
	    @Test
	    public void combineTest() {
	    	
	    	//create a testNumbers double array that will hold the values being sent into the combine method 
	    	double[][] testNumbers = new double[][] {{-7, -1},	//true
	    											{1, 7},		//true
	    											{},	//true
	    											{-15, 7},	//true
	    											{0, 0},	//true
	    											{},	//true
	    											{2, 10},	//true
	    											{-20, -11},	//false
	    											{11, 20},	//false
	    											{0, 0},
	    											{},
	    											{}};	//true 
	    	
	    	//Create a range object without initialization making it null
	        Range emptyRange = null;
	    											
	    	//create a expected return value
	    	Range[] expected = new Range[] {new Range(-7, 7), new Range(-15, 7), new Range(0, 0), new Range(-20, 10), new Range(0, 20), emptyRange};
	    	Range temp;
	    	boolean fail = false;
	    	
	    	Range firstRange;
	    	Range secondRange;
	    	
	    	//used for the expected value calls since the for loop increases by 2
	    	int counter = 0;
	    	
	    	//try the contains method
	    	System.out.print("Testing Range.combine: \n"); 
	    	for (int i = 0; i < 11; i += 2) {
	    		try {
	    			
	    			if (testNumbers[i].length > 0) {
	    				firstRange = new Range(testNumbers[i][0], testNumbers[i][1]);
	    			} else {
	    				firstRange = null;
	    			}
	    			
	    			if (testNumbers[i + 1].length > 0) {
	    				secondRange = new Range(testNumbers[i + 1][0], testNumbers[i + 1][1]);
	    			} else {
	    				secondRange = null;
	    			}
	    			
	    			
	    			temp = Range.combine(firstRange, secondRange);
	    			
	    			
		    		if (firstRange == null && secondRange != null) {
		    			assertEquals(String.format("The return value expected is Range(%s, %s) when combining Range(null) with Range(%s, %s) \n", expected[counter].getLowerBound(), expected[counter].getUpperBound(), 
			    				testNumbers[i + 1][0], testNumbers[i + 1][1]),
			    		        expected[counter], temp);
		    			System.out.print(String.format("Test Passed with the input being Range(null) and Range(%s, %s) \n", secondRange.getLowerBound(), secondRange.getUpperBound()));
			    		System.out.println(String.format("Expected Range(%s, %s) and was Range(%s, %s) \n", expected[counter].getLowerBound(), expected[counter].getUpperBound(), temp.getLowerBound(), temp.getUpperBound()));
		    		} else if (firstRange != null && secondRange == null) {
		    			assertEquals(String.format("The return value expected is Range(%s, %s) when combining Range(%s, %s) with Range(null) \n", expected[counter].getLowerBound(), expected[counter].getUpperBound(), 
			    				testNumbers[i][0], testNumbers[i][1]), 
		    					expected[counter], temp);
		    			System.out.print(String.format("Test Passed with the input being Range(%s, %s) and Range(null) \n", firstRange.getLowerBound(), firstRange.getUpperBound()));
			    		System.out.println(String.format("Expected Range(%s, %s) and was Range(%s, %s) \n", expected[counter].getLowerBound(), expected[counter].getUpperBound(), temp.getLowerBound(), temp.getUpperBound()));
		    		} else if (firstRange == null && secondRange == null) {
		    			assertEquals("The return value expected is Range(null) when combining Range(null) with Range(null) \n",
			    		        expected[counter], temp);
		    			System.out.print("Test Passed with the input being Range(null) and Range(null) \n");
			    		System.out.println("Expected Range(null) and was Range(null) \n");
		    		} else {
		    			assertEquals(String.format("The return value expected is Range(%s, %s) when combining Range(%s, %s) with Range(%s, %s) \n", expected[counter].getLowerBound(), expected[counter].getUpperBound(), 
			    				testNumbers[i][0], testNumbers[i][1], testNumbers[i + 1][0], testNumbers[i + 1][1]),
			    		        expected[counter], temp);
		    			System.out.print(String.format("Test Passed with the input being Range(%s, %s) and Range(%s, %s) \n", firstRange.getLowerBound(), firstRange.getUpperBound(), secondRange.getLowerBound(), secondRange.getUpperBound()));
			    		System.out.println(String.format("Expected Range(%s, %s) and was Range(%s, %s) \n", expected[counter].getLowerBound(), expected[counter].getUpperBound(), temp.getLowerBound(), temp.getUpperBound()));
		    		}
		    		counter += 1;
		    	}catch(AssertionError e) {	//if error was thrown then the expected value was not returned
		    		
		    		counter += 1;
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    		
		    	}
	    	} 	
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	  //combineTest tests the combine method in Range which will return the combined range of two existing ranges
	    //NOTE: either range can be null and if both are null the return value should be null
	    //in case of Range(-10, 10) and Range (-10, 10) the return should be Range(-20, 20) 
	    //in case of Range(null) and Range(-10,10) return should be Range(-10, 10)
	    //in case of Range(null) and Range(null) return should be Range(null)
	    @Test
	    public void combineIgnoringNaNTest() {
	    	
	    	//create a testNumbers double array that will hold the values being sent into the combine method 
	    	double[][] testNumbers = new double[][] {{-7, -1},	//true
	    											{1, 7},		//true
	    											{},	//true
	    											{-15, 7},	//true
	    											{0, 0},	//true
	    											{},	//true
	    											{2, 10},	//true
	    											{-20, -11},	//false
	    											{11, 20},	//false
	    											{0, 0},
	    											{},
	    											{},
	    											{Double.NaN, 8},
	    											{-10, Double.NaN},
	    											{Double.NaN, Double.NaN},
	    											{-10, 8},
	    											{Double.NaN, Double.NaN},
	    											{Double.NaN, Double.NaN}};	//true 
	    	
	    	//Create a range object without initialization making it null
	        Range emptyRange = null;
	    											
	    	//create a expected return value
	    	Range[] expected = new Range[] {new Range(-7, 7), new Range(-15, 7), new Range(0, 0), new Range(-20, 10), new Range(0, 20), emptyRange, new Range(-10, 8), new Range(-10, 8), emptyRange};
	    	Range temp;
	    	boolean fail = false;
	    	
	    	Range firstRange;
	    	Range secondRange;
	    	
	    	//used for the expected value calls since the for loop increases by 2
	    	int counter = 0;
	    	
	    	//try the contains method
	    	System.out.print("Testing Range.combineIgnoringNaN: \n"); 
	    	for (int i = 0; i < 17; i += 2) {
	    		try {
	    			
	    			if (testNumbers[i].length > 0) {
	    				firstRange = new Range(testNumbers[i][0], testNumbers[i][1]);
	    			} else {
	    				firstRange = null;
	    			}
	    			
	    			if (testNumbers[i + 1].length > 0) {
	    				secondRange = new Range(testNumbers[i + 1][0], testNumbers[i + 1][1]);
	    			} else {
	    				secondRange = null;
	    			}
	    			
	    			
	    			temp = Range.combineIgnoringNaN(firstRange, secondRange);
	    			
	    			
		    		if (firstRange == null && secondRange != null) {
		    			assertEquals(String.format("The return value expected is Range(%s, %s) when combining Range(null) with Range(%s, %s) \n", expected[counter].getLowerBound(), expected[counter].getUpperBound(), 
			    				testNumbers[i + 1][0], testNumbers[i + 1][1]),
			    		        expected[counter], temp);
		    			System.out.print(String.format("Test Passed with the input being Range(null) and Range(%s, %s) \n", secondRange.getLowerBound(), secondRange.getUpperBound()));
			    		System.out.println(String.format("Expected Range(%s, %s) and was Range(%s, %s) \n", expected[counter].getLowerBound(), expected[counter].getUpperBound(), temp.getLowerBound(), temp.getUpperBound()));
		    		} else if (firstRange != null && secondRange == null) {
		    			assertEquals(String.format("The return value expected is Range(%s, %s) when combining Range(%s, %s) with Range(null) \n", expected[counter].getLowerBound(), expected[counter].getUpperBound(), 
			    				testNumbers[i][0], testNumbers[i][1]), 
		    					expected[counter], temp);
		    			System.out.print(String.format("Test Passed with the input being Range(%s, %s) and Range(null) \n", firstRange.getLowerBound(), firstRange.getUpperBound()));
			    		System.out.println(String.format("Expected Range(%s, %s) and was Range(%s, %s) \n", expected[counter].getLowerBound(), expected[counter].getUpperBound(), temp.getLowerBound(), temp.getUpperBound()));
		    		} else if (firstRange == null && secondRange == null || temp == null) {
		    			assertEquals("The return value expected is Range(null) when combining Range(null) with Range(null) \n",
			    		        expected[counter], temp);
		    			System.out.print("Test Passed with the input being Range(null) and Range(null) \n");
			    		System.out.println("Expected Range(null) and was Range(null) \n");
		    		} else {
		    			assertEquals(String.format("The return value expected is Range(%s, %s) when combining Range(%s, %s) with Range(%s, %s) \n", expected[counter].getLowerBound(), expected[counter].getUpperBound(), 
			    				testNumbers[i][0], testNumbers[i][1], testNumbers[i + 1][0], testNumbers[i + 1][1]),
			    		        expected[counter], temp);
		    			System.out.print(String.format("Test Passed with the input being Range(%s, %s) and Range(%s, %s) \n", firstRange.getLowerBound(), firstRange.getUpperBound(), secondRange.getLowerBound(), secondRange.getUpperBound()));
		    			System.out.println(String.format("Expected Range(%s, %s) and was Range(%s, %s) \n", expected[counter].getLowerBound(), expected[counter].getUpperBound(), temp.getLowerBound(), temp.getUpperBound()));
		    		}
		    		counter += 1;
		    	}catch(AssertionError e) {	//if error was thrown then the expected value was not returned
		    		
		    		counter += 1;
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    		
		    	}
	    	} 	
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	    //expandToIncludeTest will test the expandToInclude method that takes in a Range object and a value and will expand the range 
	    //to include the value within it if needed
	    //if Range(-10,10) and value is -15 then return will be Range(-15,10)
	    //if Range(-10,10) and value is -5 then return will be Range(-10,10)
	    @Test
	    public void expandToIncludeTest() {
	    	
	    	//create a testNumbers double array that will hold the values being sent into the contains method 
	    	double[][] testNumbers = new double[][] {{-7, -1, 20},	
	    											{1, 7, -20},		
	    											{7, 10, 19},	
	    											{-15, -7, -1},	
	    											{-19, 4, 0},	
	    											{0, 0, 0}};	
	    											
	    	//the testNumbers arrays are as follows [lower bound, upper bound, value to expand to]
	    	
	    	//create a expected return value
	    	Range[] expected = new Range[] {new Range(-7, 20), new Range(-20, 7), new Range(7, 19), new Range(-15, -1), new Range(-19, 4), new Range(0, 0)};
	    	Range temp;
	    	Range outcome;
	    	boolean fail = false;
	    	
	    	//try the contains method
	    	System.out.print("Testing Range.expandToInclude: \n"); 
	    	for (int i = 0; i < 6; i++) {
	    		try {
	    			
	    			temp = new Range(testNumbers[i][0], testNumbers[i][1]);
	    			outcome = Range.expandToInclude(temp, testNumbers[i][2]);
		    		
		    		assertEquals(String.format("The return value expected is Range(%s, %s)\n", expected[i].getLowerBound(), expected[i].getUpperBound()),
		    		        expected[i], outcome);
		    		System.out.print(String.format("Test Passed for Range(%s, %s) with a value of \"%s\" \n", testNumbers[i][0], testNumbers[i][1], testNumbers[i][2]));
		    		System.out.println(String.format("Expected Range(%s, %s) and was Range(%s, %s) \n", expected[i].getLowerBound(), expected[i].getUpperBound(), 
		    				outcome.getLowerBound(), outcome.getUpperBound()));
		    		
		    	}catch(AssertionError e) {	//if error was thrown then the expected value was not returned
		    		
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    		
		    	}
	    	} 	
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	    //expandTest will test the expand method which will take in a Range object, double lowerMargin and double upperMargin where the two doubles
	    //are expressed as percentages in which the upper and lower ranges will change
	    //in case of Range(-10, 10) if the lower margin is 0.5 and upper margin is 0.5 then the return should be Range(-15, 15)
	    @Test
	    public void expandTest() {
	    	
	    	//create a testNumbers double array that will hold the values being sent into the contains method 
	    	double[][] testNumbers = new double[][] {{-7, -1, random.nextDouble(), random.nextDouble()},	
	    											{1, 7, random.nextDouble(), random.nextDouble()},		
	    											{7, 10, random.nextDouble(), random.nextDouble()},	
	    											{-15, -7, random.nextDouble(), random.nextDouble()},	
	    											{-19, 4, random.nextDouble(), random.nextDouble()},	
	    											{0, 0, random.nextDouble(), random.nextDouble()}};	
	    	
	    	//the testNumbers are arranged like this: [lower bound, upper bound, random lowerMargin, random upperMargin]
	        //the margins are between 0 and 1
	    											
	    	//the testNumbers arrays are as follows [lower bound, upper bound, value to expand to]
	    	
	    	//create a expected return value
	    	Range[] expected = new Range[6];
	    	Range temp;
	    	Range outcome;
	    	double firstBound;
	    	double secondBound;
	    	boolean fail = false;
	    	
	    	for (int j = 0; j < 6; j++) {
	    		firstBound = (testNumbers[j][0] * (1 + testNumbers[j][2]));
	    		secondBound = (testNumbers[j][1] * (1 + testNumbers[j][3]));
	    		expected[j] = new Range(firstBound, secondBound);
	    	}
	    	
	    	//try the contains method
	    	System.out.print("Testing Range.expand: \n"); 
	    	for (int i = 0; i < 6; i++) {
	    		try {
	    			
	    			temp = new Range(testNumbers[i][0], testNumbers[i][1]);
	    			outcome = Range.expand(temp, testNumbers[i][2], testNumbers[i][3]);
		    		
		    		assertEquals(String.format("The return value expected is Range(%s, %s)\n", expected[i].getLowerBound(), expected[i].getUpperBound()),
		    		        expected[i], outcome);
		    		System.out.print(String.format("Test Passed for Range(%s, %s) with a values of \"%s\" for the lowerMargin and \"%s\" for the upperMargin \n", testNumbers[i][0], testNumbers[i][1], testNumbers[i][2], testNumbers[i][3]));
		    		System.out.println(String.format("Expected Range(%s, %s) and was Range(%s, %s) \n", expected[i].getLowerBound(), expected[i].getUpperBound(), 
		    				outcome.getLowerBound(), outcome.getUpperBound()));
		    		
		    	}catch(AssertionError e) {	//if error was thrown then the expected value was not returned
		    		
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
		    		fail = true;
		    	}
	    	} 
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	    //shiftWithoutZeroCrossingTest tests the shift method which takes in Range object and a double value which changes the lower bound and
	    //upper bound by that value
	    //in case of Range(-10, 10) if the value is 10 then the return should be Range(0, 20)
	    @Test
	    public void shiftWithoutZeroCrossingTest() {
	    	
	    	//create a testNumbers double array that will hold the values being sent into the contains method 
	    	double[][] testNumbers = new double[][] {{-7, -1, random.nextDouble() * 101},	
	    											{1, 7, random.nextDouble() * -101},		
	    											{7, 10, random.nextDouble()},	
	    											{-15, -7, random.nextDouble() * -1},	
	    											{0, 0, 0}};	
	    	
	    	//the testNumbers are arranged like this: [lower bound, upper bound, random lowerMargin, random upperMargin]
	        //the margins are between 0 and 1
	    											
	    	//the testNumbers arrays are as follows [lower bound, upper bound, value to expand to]
	    	
	    	//create a expected return value
	    	Range[] expected = new Range[5];
	    	Range temp;
	    	Range outcome;
	    	double firstBound;
	    	double secondBound;
	    	boolean fail = false;
	    	
	    	for (int j = 0; j < 5; j++) {
	    		firstBound = (testNumbers[j][0] + testNumbers[j][2]);
	    		secondBound = (testNumbers[j][1] + testNumbers[j][2]);
	    		if ((testNumbers[j][0] < 0 && firstBound > 0) || (testNumbers[j][0] > 0 && firstBound < 0)) {
    				firstBound = 0;
    			}
    			if ((testNumbers[j][1] < 0 && secondBound > 0) || (testNumbers[j][1] > 0 && secondBound < 0)) {
    				secondBound = 0;
    			}
	    		expected[j] = new Range(firstBound, secondBound);
	    	}
	    	
	    	//try the contains method
	    	System.out.print("Testing Range.shift without allowZeroCrossing boolean: \n"); 
	    	for (int i = 0; i < 5; i++) {
	    		try {
	    			
	    			temp = new Range(testNumbers[i][0], testNumbers[i][1]);
	    			outcome = Range.shift(temp, testNumbers[i][2]);
		    		
		    		assertEquals(String.format("The return value expected is Range(%s, %s)\n", expected[i].getLowerBound(), expected[i].getUpperBound()),
		    		        expected[i], outcome);
		    		System.out.print(String.format("Test Passed for Range(%s, %s) with a value of \"%s\" as the Delta \n", testNumbers[i][0], testNumbers[i][1], testNumbers[i][2]));
		    		System.out.println(String.format("Expected Range(%s, %s) and was Range(%s, %s) \n", expected[i].getLowerBound(), expected[i].getUpperBound(), 
		    				outcome.getLowerBound(), outcome.getUpperBound()));
		    		
		    	}catch(AssertionError e) {	//if error was thrown then the expected value was not returned
		    		
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    		
		    	}
	    	} 	
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	    //shiftWithZeroCrossingTest tests the shift method which takes in Range object, a double value which changes the lower bound and
	    //upper bound by that value, and a boolean variable that will allow to cross the zero point
	    //in case of Range(-10, 10) if the value is 10 then the return should be Range(0, 20)
	    //in case of Range(0, 10) if the value if -10 and the boolean value is false then the return should be Range(0,0)
	    @Test
	    public void shiftWithZeroCrossingTest() {
	    	
	    	//create a testNumbers double array that will hold the values being sent into the contains method 
	    	double[][] testNumbers = new double[][] {{-7, -1, random.nextDouble() * 101},
	    											{-7, -1, random.nextDouble() * 101},	
	    											{1, 7, random.nextDouble() * -101},
	    											{1, 7, random.nextDouble() * -101},		
	    											{7, 10, random.nextDouble()},	
	    											{-15, -7, random.nextDouble() * -1},	
	    											{0, 0, 0}};	
	    											
	    	boolean[] testNumbersBool = new boolean[] {true, false, true, false, false, false, true};
	    	boolean fail = false;
	    	//the testNumbers are arranged like this: [lower bound, upper bound, random lowerMargin, random upperMargin]
	        //the margins are between 0 and 1
	    											
	    	//the testNumbers arrays are as follows [lower bound, upper bound, value to expand to]
	    	
	    	//create a expected return value
	    	Range[] expected = new Range[7];
	    	Range temp;
	    	Range outcome;
	    	double firstBound;
	    	double secondBound;
	    	
	    	for (int j = 0; j < 7; j++) {
	    		firstBound = (testNumbers[j][0] + testNumbers[j][2]);
	    		secondBound = (testNumbers[j][1] + testNumbers[j][2]);
	    		
	    		if (testNumbersBool[j] == false) {
	    			if ((testNumbers[j][0] < 0 && firstBound > 0) || (testNumbers[j][0] > 0 && firstBound < 0)) {
	    				firstBound = 0;
	    			}
	    			if ((testNumbers[j][1] < 0 && secondBound > 0) || (testNumbers[j][1] > 0 && secondBound < 0)) {
	    				secondBound = 0;
	    			}
	    		}
	    			
	    		expected[j] = new Range(firstBound, secondBound);
	    	}
	    	
	    	//try the contains method
	    	System.out.print("Testing Range.shift with allowZeroCrossing boolean: \n"); 
	    	for (int i = 0; i < 7; i++) {
	    		try {
	    			
	    			temp = new Range(testNumbers[i][0], testNumbers[i][1]);
	    			outcome = Range.shift(temp, testNumbers[i][2], testNumbersBool[i]);
		    		
		    		assertEquals(String.format("The return value expected is Range(%s, %s)\n", expected[i].getLowerBound(), expected[i].getUpperBound()),
		    		        expected[i], outcome);
		    		System.out.print(String.format("Test Passed for Range(%s, %s) with a value of \"%s\" as the Delta and \"%s\" as the boolean value\n", testNumbers[i][0], testNumbers[i][1], testNumbers[i][2], testNumbersBool[i]));
		    		System.out.println(String.format("Expected Range(%s, %s) and was Range(%s, %s) \n", expected[i].getLowerBound(), expected[i].getUpperBound(), 
		    				outcome.getLowerBound(), outcome.getUpperBound()));
		    		
		    	}catch(AssertionError e) {	//if error was thrown then the expected value was not returned
		    		
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    	}
	    	} 
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	    //scaleTest will test the scale method which takes in a Range object and a double factor which will multiply the range bounds by the factor
	    //in case of Range(-10, 10) and a factor of 2 the return should be Range(-20, 20)
	    @Test
	    public void scaleTest() {
	    	
	    	//create a testNumbers double array that will hold the values being sent into the contains method 
	    	double[][] testNumbers = new double[][] {{-7, -1, random.nextDouble() * 101},	
	    											{1, 7, random.nextDouble() * 51},		
	    											{7, 10, random.nextDouble() * 10},	
	    											{-15, -7, random.nextDouble()},
	    											{-10, 10, 2},	
	    											{0, 0, 0}};	
	    	
	    	//the testNumbers are arranged like this: [lower bound, upper bound, random lowerMargin, random upperMargin]
	        //the margins are between 0 and 1
	    											
	    	//the testNumbers arrays are as follows [lower bound, upper bound, value to expand to]
	    	
	    	//create a expected return value
	    	Range[] expected = new Range[6];
	    	Range temp;
	    	Range outcome;
	    	double firstBound;
	    	double secondBound;
	    	boolean fail = false;
	    	
	    	for (int j = 0; j < 6; j++) {
	    		firstBound = (testNumbers[j][0] * testNumbers[j][2]);
	    		secondBound = (testNumbers[j][1] * testNumbers[j][2]);
	    		expected[j] = new Range(firstBound, secondBound);
	    	}
	    	
	    	//try the contains method
	    	System.out.print("Testing Range.scale: \n"); 
	    	for (int i = 0; i < 6; i++) {
	    		try {
	    			
	    			temp = new Range(testNumbers[i][0], testNumbers[i][1]);
	    			outcome = Range.scale(temp, testNumbers[i][2]);
		    		
		    		assertEquals(String.format("The return value expected is Range(%s, %s)\n", expected[i].getLowerBound(), expected[i].getUpperBound()),
		    		        expected[i], outcome);
		    		System.out.print(String.format("Test Passed for Range(%s, %s) with a value of \"%s\" as the factor \n", testNumbers[i][0], testNumbers[i][1], testNumbers[i][2]));
		    		System.out.println(String.format("Expected Range(%s, %s) and was Range(%s, %s) \n", expected[i].getLowerBound(), expected[i].getUpperBound(), 
		    				outcome.getLowerBound(), outcome.getUpperBound()));
		    		
		    	}catch(AssertionError e) {	//if error was thrown then the expected value was not returned
		    		
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    	}
	    	} 	
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	    //equalsTest tests the method equals which will compare the current object with an arbitrary object and will return true
	    //if they are equal and false otherwise 
	    //if the current object is Range(-10, 10) and the arbitrary object is Range(-10,10) the return value is true
	    //if the current object is Range(-10, 10) and the arbitrary object is Range(-15, 15) the return value is false
	    @Test
	    public void equalsTest() {
	    	
	    	//create a testNumbers double array that will hold the values being sent into the contains method 
	    	double[][] testNumbers = new double[][] {{-7, -1},	
	    											{-7, -1},
	    											{-7, -1},	
	    											{-5, -2},		
	    											{20, 20},	
	    											{-20, -20},	
	    											{-19, 4},	
	    											{-19, 4},	
	    											{-19, 4},	
	    											{-19, 5},
	    											{2, 19},	
	    											{2, 19},
	    											{2, 19},	
	    											{1, 2},	
	    											{0, 0},	
	    											{0, 0}};
	    	
	    	//create a expected return value
	    	boolean[] expected = new boolean[] {true, false, false, true, false, true, false, true};
	    	Range temp;
	    	int counter = 0;
	    	boolean fail = false;
	    	
	    	//try the contains method
	    	System.out.print("Testing Range.equals: \n"); 
	    	for (int i = 0; i < 15; i += 2) {
	    		try {
		    		
	    			exampleRange = new Range(testNumbers[i][0], testNumbers[i][1]);
	    			temp = new Range(testNumbers[i + 1][0], testNumbers[i + 1][1]);
	    			
		    		assertEquals(String.format("The boolean value expected for Range(%s, %s) and Range(%s, %s) is \"%s\"\n", testNumbers[i][0], testNumbers[i][1],
		    				testNumbers[i + 1][0], testNumbers[i + 1][1], expected[counter]),
		    		        expected[counter], exampleRange.equals(temp));
		    		System.out.print(String.format("Test Passed for Range(%s, %s) and Range(%s, %s)\n", testNumbers[i][0], testNumbers[i][1], testNumbers[i + 1][0], testNumbers[i + 1][1]));
		    		System.out.println(String.format("Expected \"%s\" and was \"%s\"\n", expected[counter], exampleRange.equals(temp)));
		    		counter++;
		    	}catch(AssertionError e) {	//if error was thrown then the expected value was not returned
		    		counter++;
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    		
		    	}
	    	} 	
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	    //isNaNRangeTest tests wither the upper and lower bounds are NaN, if both are NaN then returns true otherwise returns false
	    //in case of Range(-10, NaN) it will return false as not both are NaN just the upper bound is NaN
	    //in case of Range(NaN, NaN) it will return true as both are NaN
	    @Test
	    public void isNaNRangeTest() {
	    	
	    	//create a testNumbers double array that will hold the values being sent into the contains method 
	    	double[][] testNumbers = new double[][] {{-7, -1},	
	    											{-5, Double.NaN},
	    											{10, Double.NaN},		
	    											{Double.NaN, 20},
	    											{Double.NaN, -40},	
	    											{Double.NaN, Double.NaN},	
	    											{19, 40},		
	    											{-1, 2},	
	    											{0, 0}};
	    	
	    	//create a expected return value
	    	boolean[] expected = new boolean[] {false, false, false, false, false, true, false, false, false};
	    	boolean fail = false;
	    	
	    	//try the contains method
	    	System.out.print("Testing Range.isNaNRange: \n"); 
	    	for (int i = 0; i < 9; i++) {
	    		try {
		    		
	    			exampleRange = new Range(testNumbers[i][0], testNumbers[i][1]);
	    			
		    		assertEquals(String.format("The boolean value expected for Range(%s, %s) is \"%s\"\n", testNumbers[i][0], testNumbers[i][1], expected[i]),
		    		        expected[i], exampleRange.isNaNRange());
		    		System.out.print(String.format("Test Passed for Range(%s, %s)\n", testNumbers[i][0], testNumbers[i][1]));
		    		System.out.println(String.format("Expected \"%s\" and was \"%s\"\n", expected[i], exampleRange.isNaNRange()));
		    	}catch(AssertionError e) {	//if error was thrown then the expected value was not returned
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    		
		    	}
	    	} 	
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	    //hashCodeTest will test whether the hashCode's are created properly by creating two variables of different names BUT same values
	    //then it will make sure that the two variables are equal and then make sure their hashCode's are equal. If the hashCode's are
	    //equal then the hashCode method is correct otherwise there is something that needs to be fixed
	    //
	    //in case of x = Range(-10,10) and y = Range(-10,10), x.equals(y) is true and y.equals(x) is true
	    //then we test x.hashCode() == y.hashCode() then the method is correct otherwise not correct
	    @Test
	    public void hashCodeTest() {
	    	
	    	//create a testNumbers double array that will hold the values being sent into the contains method 
	    	double[][] testNumbers = new double[][] {{-7, -1},	
	    											{-7, -1},		
	    											{-19, 4},	
	    											{-19, 4},	
	    											{2, 19},	
	    											{2, 19},
	    											{0, 0},	
	    											{0, 0}};
	    	
	    	//Note: there isn't really an expected value for this method
	    	Range temp;
	    	boolean fail = false;
	    											
	    	//try the contains method
	    	System.out.print("Testing Range.hashCode: \n"); 
	    	for (int i = 0; i < 7; i += 2) {
	    		try {
		    		
	    			exampleRange = new Range(testNumbers[i][0], testNumbers[i][1]);
	    			temp = new Range(testNumbers[i + 1][0], testNumbers[i + 1][1]);
	    			
		    		assertEquals(String.format("Variables for exampleRange = Range(%s, %s) are not equal to temp = Range(%s, %s) \n", testNumbers[i][0], testNumbers[i][1],
		    				testNumbers[i + 1][0], testNumbers[i + 1][1]), true, exampleRange.equals(temp) && temp.equals(exampleRange));
		    		System.out.print(String.format("Variables for exampleRage = Range(%s, %s) and temp = Range(%s, %s) are equal. Now testing if hashCodes are equal\n", testNumbers[i][0],
		    				testNumbers[i][1], testNumbers[i + 1][0], testNumbers[i + 1][1]));
		    		assertEquals(String.format("HashCodes for exampleRange = \"%s\" and temp = \"%s\" are not equal\n", exampleRange.hashCode(), temp.hashCode()), true, exampleRange.hashCode() == temp.hashCode());
		    		System.out.println(String.format("exampleRange.hashCode = \"%s\" equals to temp.hashCode = \"%s\"\nTest Passed\n", exampleRange.hashCode(), temp.hashCode()));
		    	}catch(AssertionError e) {	//if error was thrown then the expected value was not returned
		    		System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
		    	}
	    	} 	
	    	
	    	if (fail) {
	    		fail();
	    	}
	    }
	    
	  //toStringTest test the toString overloaded method which should return "Range[(double)lower, (double)upper]"
	    //In the case of Range(-1, 1) it should return "Range[-1.0, 1.0]"
	    @Test
	    public void toStringTest() {
	    	
			//created the expected return string
	    	//create a testNumbers double array that will hold the values being sent into the contains method 
	    	double[][] testNumbers = new double[][] {{-7, -1},	//true
	    											{1, 7},		//true
	    											{-7, 7},	//true
	    											{-15, -7},	//true
	    											{-19, 4},	//true
	    											{-9, 15},	//true
	    											{2, 19},	//true
	    											{-20, -11},	//false
	    											{11, 20},	//false
	    											{0, 0}};	//true 
			
	    	String[] expected = new String[10];
	    	boolean fail = false;
	    	
	    	for (int i = 0; i < 10; i++) {
	        	expected[i] = "Range[" + testNumbers[i][0] + "," + testNumbers[i][1] + "]";
	        }
			//try the toString method
	        System.out.print("Testing toString method: \n");
	        for (int i = 0; i < 10; i++) {
	        	try{
	    			
		        	exampleRange = new Range(testNumbers[i][0], testNumbers[i][1]);
		        	
//		        	System.out.println("");
		        	assertEquals(String.format("The exampleRange.toString = %s which isn't equal to the expected = %s\n", exampleRange.toString(), expected[i]), 
		        			expected[i], exampleRange.toString());
		        	System.out.println(String.format("The exampleRange.toString = %s is equal to the expected = %s \n", exampleRange.toString(), expected[i]));
				}catch(AssertionError e){		//if the AssertionError is thrown that means the expected value was not returned
					
					System.out.print("Test FAILED\n");
					System.out.println(e.toString() + "\n");
					fail = true;
				}
	        }
	        
	        if (fail) {
	    		fail();
	    	}
	    }
	    
	    
	    @After
	    public void tearDown() throws Exception {
	    }

	    @AfterClass
	    public static void tearDownAfterClass() throws Exception {
	    }

}
