package org.jfree.data;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.jmock.Mockery;
import org.jfree.data.DataUtilities;
import org.jfree.data.KeyedValues;
import org.jfree.data.Values2D;
import org.jmock.Expectations;
import java.util.*;
//Could optimize by separating the tests that don't need the same mocking context
public class DataUtilitiesTestPlus{
	private final String ANSI_RED_BOLD_ESC = "\033[31;1m";
	private final String ANSI_RED_BOLD_END = "\033[0m";
	private final String FAILED_MSG = ANSI_RED_BOLD_ESC + "FAILED: " + ANSI_RED_BOLD_END ;
	private Mockery contextValues2D;

	private Mockery contextMockV2DArgument;
	private Values2D mockValues2D;

	private Values2D mockV2DArgument;
	private Mockery mockingContextKVInput;
	private Mockery mockingContextKVOutput;	
	private KeyedValues mockKVOutput;
	private KeyedValues mockKVInput;

	@Before
	public void setUpContextKeyedValuesInput(){
		mockingContextKVInput = new Mockery();
		mockKVInput = mockingContextKVInput.mock(KeyedValues.class);
		String[] keys = {"KEY 0","KEY 1","KEY 2","KEY 3"};
		Number[] expectedOutputs = {Integer.valueOf(1),null, Integer.valueOf(2),Double.valueOf(1.0/3.0)};
		mockingContextKVInput.checking(new Expectations(){{
			atLeast(1).of(mockKVInput).getItemCount();
			will(returnValue(4));
			for(int i = 0; i < 4; i++){
				atLeast(1).of(mockKVInput).getValue(i);
				will(returnValue(expectedOutputs[i]));
				oneOf(mockKVInput).getKey(i);
				will(returnValue(keys[i]));
			}
		}});
		//Key-Value mapping:
		/*
		 * "KEY 0" -> 1
		 * "KEY 1" -> NULL
		 * "KEY 2" -> 2
		 * "KEY 3" -> 1.0/3.0
		 */
	}

	@Before
	public void setUpContextValues2DArgument(){
		contextMockV2DArgument = new Mockery();
		mockV2DArgument = contextMockV2DArgument.mock(Values2D.class);
		contextMockV2DArgument.checking(new Expectations() {
			{
				atLeast(1).of(mockV2DArgument).getColumnCount(); will(returnValue(10));
				atLeast(1).of(mockV2DArgument).getRowCount(); will(returnValue(20));
				for(int i = 0; i < 20; i++) {
					for(int j = 0; j < 10; j++) {
						if((j % 2 == 0 && i % 2 != 0) || (j % 2 !=0 && i % 2 == 0)) {
							allowing(mockV2DArgument).getValue(i,j);
							will(returnValue(Double.valueOf(-Math.PI)));
						}
						else{
							allowing(mockV2DArgument).getValue(i,j);
							will(returnValue(null));
						}
						
					}
				}
			}
		});
	}
	@Before 
	public void setUpContextKeyedValuesOutput(){
		mockingContextKVOutput = new Mockery();
		mockKVOutput = mockingContextKVOutput.mock(KeyedValues.class);
		String[] keys = {"KEY 0","KEY 1","KEY 2","KEY 3"};
		Number[] expectedOutputs = {new Double(0.3),new Double(0.3),new Double(0.9),new Double(1.0)};
		mockingContextKVOutput.checking(new Expectations(){{
			allowing(mockKVOutput).getItemCount(); will(returnValue(4));
			allowing(mockKVOutput).getKeys(); will(returnValue(Arrays.asList(keys)));
			for(int i = 0; i < 4; i++){
				allowing(mockKVOutput).getValue(i);
				will(returnValue(expectedOutputs[i]));
				allowing(mockKVOutput).getKey(i);
				will(returnValue(keys[i]));
			}
		}});
		//Expected Key-Value mapping:
		/*
		 * "KEY 0" -> 0.3
		 * "KEY 1" -> 0.3 (b/c NULL)
		 * "KEY 2" -> 0.9
		 * "KEY 3" -> 1.0
		 */
	}
	@Before
	public void setUpContextValues2D(){
		//Setting up a Values2D object that will return a unique sum for all rows and columns
		contextValues2D = new Mockery();
		mockValues2D = contextValues2D.mock(Values2D.class);
		contextValues2D.checking(new Expectations(){{
			oneOf(mockValues2D).getColumnCount(); will(returnValue(5));
			oneOf(mockValues2D).getRowCount(); will(returnValue(5));
			Double writeValue;
			for(int i = 0; i < 4; i++){
				for(int j = 0; j < 5; j++){
					allowing(mockValues2D).getValue(i, j);
					if(j == 4){
						will(returnValue(null)); continue;
					}
					else if(j % 2 == 0 && i != j)
						writeValue = Double.valueOf(-10*(i+1));
					else if(i == j)
						writeValue = Double.valueOf((double)i/(double)3);
					else
						writeValue = Double.valueOf(1);
					will(returnValue(writeValue));
				}
				System.out.println();
			}
			for(int p = 0; p < 5; p++){
				allowing(mockValues2D).getValue(4,p);
				will(returnValue(null));
			}
			for(int m = 0; m < 5; m++){
				allowing(mockValues2D).getValue(-1,m);
				will(returnValue(null));
				
			}
			for(int n = 0; n< 5; n++){
				allowing(mockValues2D).getValue(n,-1);
				will(returnValue(null));
			}
		}});
		/* 
			The Values2D object should look like:
			i = 0: {   0,    1,   -10,    1, null} -> SUM =   -8
			i = 1: { -20,  1/3,   -20,    1, null} -> SUM = -116/3
			i = 2: { -30,    1,   2/3,    1, null} -> SUM =  -82/3
			i = 3: { -40,    1,   -40,    1, null} -> SUM =  -78
			i = 4: {null, null,	 null, null, null} -> SUM =  0
			__________________________________________________
			total: { -90, 10/3,-208/3,    4, null} (of columns)
			This will make sure that all of the sums are unique
			*/
	}
	@Test
	public void testCalculateColumnTotalTwoArguments(){
		double expected = (double)10/(double)3;
		double actual = DataUtilities.calculateColumnTotal(mockValues2D, 1);
		assertEquals("\033[31;1mFAILED: \033[0m Sum does not match (expected " + expected + ", but was "+actual+")",expected,actual,0.000000000001);
	}
	@Test
	public void testCalculateColumnTotalTwoArgsProhibitsNullInput(){
		try{
			DataUtilities.calculateColumnTotal(null, 1);
			fail();
		}catch(AssertionError ae){
			fail("FAILED!! No action taken on null input.");
		}catch(IllegalArgumentException iae){
			System.out.println("\033[32;1mPASSED \033[0m. Method threw IllegalArgumentException: "+iae.toString());
		}catch(Exception e) {
			System.out.println("\033[31;1mFAILED: \033[0m Method threw exception: "+e.toString());
		}
	}
	@Test
	public void testCalculateColumnTotalTwoArgsIsZeroForNegativeInput(){
		double expected = 0;
		double actual = DataUtilities.calculateColumnTotal(mockValues2D, -1);
		assertEquals("\033[31;1mFAILED: \033[0m Sum does not match (expected " + expected + ", but was "+actual+")",expected,actual,0.000000000001);
	}
	@Test
	public void testCalculateColumnTotalThreeArgs(){
		double expected = (double)1 + (double)1/(double)3;
		int rows[] = {1,2,4};
		try{
			double actual = DataUtilities.calculateColumnTotal(mockValues2D, 1,rows);
			assertEquals("\033[31;1mFAILED: \033[0m Sum does not match",expected,actual,0);
			System.out.println(String.format("\033[32;1mPASSED \033[0m: Expected %f, was %f.",Double.valueOf(expected),Double.valueOf(actual)));
		}catch(AssertionError ae){
			System.out.println(ae.toString());
			fail();
		}
		catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	} 
	@Test
	public void testCalculateColumnTotalThreeArgsProhibitsNullInput(){
		int rows[] = {1,2,4};
		try{
			DataUtilities.calculateColumnTotal(null, 1,null);
			fail();
		}catch(AssertionError ae){
			fail("FAILED!! No action taken on null input.");
		}
		catch(Exception e){
			System.out.println("\033[32;1mPASSED \033[0m. Method threw an exception for null input: "+e.toString());
		}
	}
	/**
	 * @Since 1.4
	 */
	@Test
	public void testCalculateColumnTotalThreeArgsDoesNotModifyFirstArg(){
		int rows[] = {1,2,4};
		try{
			DataUtilities.calculateColumnTotal(mockV2DArgument, 1,rows);
			for(int i = 0; i < 20; i++) {
				for(int j = 0; j < 10; j++) {
					if((j % 2 == 0 && i % 2 != 0) || (j % 2 !=0 && i % 2 == 0)) {
						assertEquals(FAILED_MSG+"Function modified input values",Double.valueOf(-Math.PI),mockV2DArgument.getValue(i,j));
					}
					else{
						assertEquals(FAILED_MSG+"Function modified input values",null,mockV2DArgument.getValue(i,j));
					}
				}
			}
			System.out.println("\033[32;1mPASSED \033[0m. Function does not modify the values of the input Values2D object");
			
		}catch(AssertionError ae){
			fail(ae.toString());
		}
		catch(Exception e){
			System.out.println(FAILED_MSG+"Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	/**
	 * @Since 1.4
	 */
	@Test
	public void testCalculateColumnTotalThreeArgsDoesNotModifySecondArg(){
		int rows[] = {1,2,4};
		int expectedCol = 5;
		int actualCol = 5;
		try{
			DataUtilities.calculateColumnTotal(mockV2DArgument, actualCol,rows);
			assertEquals(FAILED_MSG+"Function modified input values",Double.valueOf(expectedCol),Double.valueOf(actualCol));
			System.out.println("\033[32;1mPASSED \033[0m. Function does not modify the values of the input Values2D object");
			
		}catch(AssertionError ae){
			fail(ae.toString());
		}
		catch(Exception e){
			System.out.println(FAILED_MSG+"Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testCalculateColumnTotalThreeArgsDoesNotModifyThirdArg(){
		int rows[] = {1,2,4};
		int expectedRows[]  = {1,2,4};
		int col = 1;
		try{
			DataUtilities.calculateColumnTotal(mockValues2D, col,rows);
			
			for(int i = 0; i < 3; i++) {
				assertEquals("FAILED. Function modified input values",expectedRows[i],rows[i],0d);
			}
			System.out.println("\033[32;1mPASSED \033[0m. Function does not modify the input value");
			
		}catch(AssertionError ae){
			fail(ae.toString());
		}
		catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testCalculateColumnTotalThreeArgsSkipsInvalidRows(){
		double expected = (double)1 + (double)1/(double)3;
		int rows[] = {-1,2,4,1,12,69};
		double actual = 0;
		try{
			actual = DataUtilities.calculateColumnTotal(mockValues2D, 1,rows);
			assertEquals(ANSI_RED_BOLD_ESC+"FAILED"+ANSI_RED_BOLD_END+ ": Sum does not match",expected,actual,0);
			System.out.println(String.format("\033[32;1mPASSED \033[0m: Expected %f, was %f.",Double.valueOf(expected),Double.valueOf(actual)));
		}catch(AssertionError ae){
			System.out.println(ANSI_RED_BOLD_ESC + "FAILED!!" + ANSI_RED_BOLD_END + String.format("Expected %f, was %f.",Double.valueOf(expected),Double.valueOf(actual)));
			fail();
		}
		catch(Exception e){
			System.out.println("\033[32;1mPASSED \033[0m. Method threw exception for null input: "+e.toString());
			fail();
		}
	}
	@Test
	public void testCalculateColumnTotalThreeArgsZeroForNegativeColumnInput(){
		double expected = 0;
		int rows[] = {1,2,4};
		double actual=0;
		try{
			actual = DataUtilities.calculateColumnTotal(mockValues2D, -1,rows);
			assertEquals("\033[31;1mFAILED: \033[0m Sum does not match",expected,actual,0);
			System.out.println(String.format("\033[32;1mPASSED \033[0m: Expected %f, was %f.",Double.valueOf(expected),Double.valueOf(actual)));
		}catch(AssertionError ae){
			System.out.println(String.format("\033[31;1mFAILED: \033[0m Expected %f, was %f.",Double.valueOf(expected),Double.valueOf(actual)));
			fail();
		}
		catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test 
	public void testCalculateRowTotalTwoArguments(){
		double expected = -8;
		double actual = DataUtilities.calculateRowTotal(mockValues2D, 0);
		assertEquals("\033[31;1mFAILED: \033[0m Sum does not match (expected -8, but was "+actual+")",expected,actual,0);
	}
	@Test 
	public void testCalculateRowTotalTwoArgumentsNegativeRowNumber(){
		double expected = 0.0;
		try{
			double actual = DataUtilities.calculateRowTotal(mockValues2D, -1);
			assertEquals("\033[31;1mFAILED: \033[0m row sum does not match",expected,actual,0);
			System.out.println(String.format("\033[32;1mPASSED \033[0m: Expected %f, was %f.",Double.valueOf(expected),Double.valueOf(actual)));
		}catch(AssertionError ae){
			System.out.println(ae.toString());
			fail();
		}
		catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test 
	public void testCalculateRowTotalTwoArgumentsNullValuesNegativeRowNumber(){
		try{
			DataUtilities.calculateRowTotal(null, -1);
			fail();
		}catch(AssertionError ae){
			System.out.println("\033[31;1mFAILED: \033[0m Expected IllegalArgumentException, none thrown");
			fail();
		}
		catch(IllegalArgumentException ie){
			System.out.println("\033[32;1mPASSED \033[0m: exception thrown \""+ie.toString()+"\"");
		}
		catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testCalculateRowTotalThreeArgs(){
		double expected = -9;
		int cols[] = {2,3,4};
		try{
			double actual = DataUtilities.calculateRowTotal(mockValues2D, 0,cols);
			assertEquals("\033[31;1mFAILED: \033[0m Sum does not match (expected " + expected + " but was "+actual+")",expected,actual,0);
			System.out.println(String.format("\033[32;1mPASSED \033[0m: Expected %f, was %f.",Double.valueOf(expected),Double.valueOf(actual)));
		}catch(AssertionError ae){
			System.out.println(ae.toString());
			fail();
		}
		catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testCalculateRowTotalThreeArgsIsZeroForNoCols(){
		double expected = 0.0;
		int cols[] ={};
		try{
			double actual = DataUtilities.calculateRowTotal(mockValues2D, 0,cols);
			assertEquals("\033[31;1mFAILED: \033[0m Sum does not match (expected " + expected + " but was "+actual+")",expected,actual,0);
			System.out.println(String.format("\033[32;1mPASSED \033[0m: Expected %f, was %f.",Double.valueOf(expected),Double.valueOf(actual)));
		}catch(AssertionError ae){
			System.out.println(ae.toString());
			fail();
		}
		catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testCalculateRowTotalThreeArgsHandlesBadValues(){
		double expected = -9;
		int cols[] = {2,3,4,-1,12};
		try{
			double actual = DataUtilities.calculateRowTotal(mockValues2D, 0,cols);
			assertEquals("\033[31;1mFAILED: \033[0m Sum does not match (expected " + expected + " but was "+actual+")",expected,actual,0);
			System.out.println(String.format("\033[32;1mPASSED \033[0m: Expected %f, was %f.",Double.valueOf(expected),Double.valueOf(actual)));
		}catch(AssertionError ae){
			System.out.println(ae.toString());
			fail();
		}
		catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testCalculateRowTotal3ArgsThrowsIllegalArgumentExceptionOnNull(){
		int cols[] = {2,3,4};
		try{
			double actual = DataUtilities.calculateRowTotal(null, 1,cols);
			fail();
		}catch(AssertionError ae){
			System.out.println("\033[31;1mFAILED: \033[0m Expected IllegalArgumentException, none thrown");
			fail();
		}
		catch(IllegalArgumentException ie){
			System.out.println("\033[32;1mPASSED \033[0m: exception thrown \""+ie.toString()+"\"");
		}
		catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testCreateNumberArray(){
		double[] data = new double[3];
		Number[] expecteds = new Number[3];
		for(int i = 0; i < 3; i++){
			data[i] = Math.PI;
			expecteds[i]= Double.valueOf(data[i]);
		}
		try{
			Number[] actual = DataUtilities.createNumberArray(data);
			
			for(int i = 0; i< actual.length; i++) {
				assertEquals("Values are not the same",expecteds[i].doubleValue(),actual[i].doubleValue(),0);
			}
		}catch(AssertionError ae){
			System.out.println(ae.toString());
			fail();
		}
		catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testCreateNumberArrayProbibitsNull(){
		try{
			Number[] actual = DataUtilities.createNumberArray(null);
			fail();
		}catch(AssertionError ae){
			System.out.println("\033[31;1mFAILED: \033[0m Expected IllegalArgumentException, none thrown");
			fail();
		}
		catch(IllegalArgumentException ie){
			System.out.println("\033[32;1mPASSED \033[0m: exception thrown \""+ie.toString()+"\"");
		}
		catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testCreateNumberArray2D(){
		double[][] data = new double[12][6];
		Number[][] expecteds = new Number[12][6];
		for(int i = 0; i < 12; i++){
			for(int j = 0; j < 6; j++){
				data[i][j] = j % 2 == 0 ? (double)1/13 : (double)-1/13;
				expecteds[i][j] = Double.valueOf(data[i][j]);
			}
		}
		try{
			Number[][] actual = DataUtilities.createNumberArray2D(data);
			for(int i = 0; i <actual.length; i++){
				for(int j = 0; j< actual[i].length; j++) {
					assertEquals("Values are not the same",expecteds[i][j].doubleValue(),actual[i][j].doubleValue(),0);
				}
			}
		}catch(AssertionError ae){
			System.out.println(ae.toString());
			fail();
		}
		catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testCreateNumberArray2DProhibitsNull(){
		System.out.println("Testing createNumberArray2D() rejects null input");
		try{
			Number[][] actual = DataUtilities.createNumberArray2D(null);
			fail();
		}catch(AssertionError ae){
			System.out.println("\033[31;1mFAILED: \033[0m Expected IllegalArgumentException, none thrown");
			fail();
		}
		catch(IllegalArgumentException ie){
			System.out.println("\033[32;1mPASSED \033[0m: exception thrown \""+ie.toString()+"\"");
		}
		catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testDataUtilitiesCloneForValidArray(){
		double[][] data = new double[10][10];
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				data[i][j] = j % 2 == 0 ? (double)1/(double)3 : (double)-1/(double)3;
			}
		}
		try{
			double[][] output = DataUtilities.clone(data);
			for(int i = 0; i < 10; i++){
				for(int j = 0; j < 10; j++){
					assertEquals("\033[31;1mFAILED: \033[0m data at ["+i+"]["+j+"] does not match expected value", data[i][j],output[i][j],0);
				}
			}
		}catch(AssertionError ae){
			System.out.println(ae.toString());
			fail();
		}
		catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testDataUtilitiesCloneForInvalidArray(){
		double[][] data = new double[10][10];
		for(int i = 0; i < 10; i++){
				data[i]=null;
		}
		try{
			double[][] output = DataUtilities.clone(data);
			for(int i = 0; i < 10; i++){
				assertTrue("\033[31;1mFAILED: \033[0m data at ["+i+"] does not match expected value",Arrays.equals(data[i],output[i]));
			}
		}catch(AssertionError ae){
			System.out.println(ae.toString());
			fail();
		}
		catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testDataUtilitiesCloneProhibitsNull(){
		try{
			double[][] output = DataUtilities.clone(null);
			fail();
		}catch(AssertionError ae){
			System.out.println("\033[31;1mFAILED: \033[0m Expected IllegalArgumentException, none thrown");
			fail();
		}catch(IllegalArgumentException ie){
			System.out.println("\033[32;1mPASSED \033[0m: exception thrown \""+ie.toString()+"\"");
		}catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testDataUtilitiesCloneForLargeArray(){
		double[][] data = new double[100][100];
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				data[i][j] = j % 2 == 0 ? (double)1/3 : (double)-1/3;
			}
		}
		try{
			double[][] output = DataUtilities.clone(data);
			for(int i = 0; i < 100; i++){
				for(int j = 0; j < 100; j++){
					assertEquals("\033[31;1mFAILED: \033[0m data at ["+i+"]["+j+"] does not match expected value", data[i][j],output[i][j],0);
				}
			}
		}catch(AssertionError ae){
			System.out.println(ae.toString());
			fail();
		}
		catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
	@Test 
	public void testDataUtilitiesEqualsMethodForUnequal2DArrays(){
		System.out.println("Testing DataUtilities.equal(double[][] a, double[][] b) for unequal 2D double arrays");
		double[][] testMatrix1 = new double[2][2];
		double[][] testMatrix2 = new double[2][2];
		int counter = 0;
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
				testMatrix1[i][j] = counter++;
				testMatrix2[i][j] = -testMatrix1[i][j]/Math.PI;
			}
		}
		assertFalse(DataUtilities.equal(testMatrix1,testMatrix2));
	}
	@Test 
	public void testDataUtilitiesEqualMethodForEqual2DArrays(){
		System.out.println("Testing DataUtilities.equal(double[][] a, double[][] b) for equal 2D double arrays");
		double[][] testMatrix1 = new double[2][2];
		double[][] testMatrix2 = new double[2][2];
		int counter = 0;
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
				testMatrix1[i][j] = counter++;
				testMatrix2[i][j] = testMatrix1[i][j];
			}
		}
		assertTrue(DataUtilities.equal(testMatrix1,testMatrix2));
	}
	@Test 
	public void testDataUtilitiesEqualMethodForDifferentCols2DArrays(){
		System.out.println("Testing DataUtilities.equal(double[][] a, double[][] b) for unequal 2D double arrays");
		double[][] testMatrix1 = new double[2][2];
		double[][] testMatrix2 = new double[2][3];
		int counter = 0;
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
				testMatrix1[i][j] = counter++;
				testMatrix2[i][j] = testMatrix1[i][j];
			}
		}
		for(int i = 0; i < 2; i++){
			testMatrix2[i][2] = -(counter--);
		}
		assertFalse(DataUtilities.equal(testMatrix1,testMatrix2));
	}
	@Test 
	public void testDataUtilitiesEqualsMethod4(){
		System.out.println("Testing DataUtilities.equal(double[][] a, double[][] b) for unequal 2D double arrays");
		double[][] testMatrix1 = new double[2][2];
		double[][] testMatrix2 = new double[3][3];
		int counter = 0;
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
				testMatrix1[i][j] = counter++;
				testMatrix2[i][j] = testMatrix1[i][j];
			}
		}
		for(int i = 0; i < 3; i++){
			for(int j = 0; i < 3; i++){
				testMatrix2[i][j] = -(counter--);
			}
		}
		assertFalse(DataUtilities.equal(testMatrix1,testMatrix2));
	}
	@Test 
	public void testDataUtilitiesEqualsMethod5(){
		System.out.println("Testing DataUtilities.equal(double[][] a, double[][] b) for unequal 2D double arrays");
		double[][] testMatrix1 = new double[2][2];
		double[][] testMatrix2 = null;
		int counter = 0;
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
				testMatrix1[i][j] = counter++;
			}
		}
		assertFalse(DataUtilities.equal(testMatrix1,testMatrix2));
	}
	@Test 
	public void testDataUtilitiesEqualsMethod6(){
		System.out.println("Testing DataUtilities.equal(double[][] a, double[][] b) for unequal 2D double arrays (Mutation)");
		double[][] testMatrix1 = new double[5][2];
		double[][] testMatrix2 = new double[5][2];
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 2; j++){
				testMatrix1[i][j] = 0;
				testMatrix2[i][j] = 0;
			}
		}
		testMatrix1[0][0] = -0xFF;
		assertFalse(DataUtilities.equal(testMatrix1,testMatrix2));
	}
	@Test 
	public void testDataUtilitiesEqualsFalseForLeftArgumentNull(){
		System.out.println("Testing DataUtilities.equal(double[][] a, double[][] b) for unequal 2D double arrays");
		double[][] testMatrix1 = null;
		double[][] testMatrix2 = new double[3][3];
		int counter = 0;
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
				
				testMatrix2[i][j] = counter++;
			}
		}
		assertFalse(DataUtilities.equal(testMatrix1,testMatrix2));
	}
	@Test 
	public void testDataUtilitiesEqualsTrueArgumentNull(){
		System.out.println("Testing DataUtilities.equal(double[][] a, double[][] b) for null 2D double arrays");
		double[][] testMatrix1 = null;
		double[][] testMatrix2 = null;
		assertTrue(DataUtilities.equal(testMatrix1,testMatrix2));
	}
	public void testDataUtilitiesEqualsFalseMutant1(){
		System.out.println("Testing DataUtilities.equal(double[][] a, double[][] b) for unequal 2D double arrays");
		double[][] testMatrix1 = new double[3][3];
		double[][] testMatrix2 = new double[3][3];
		int counter = 0;
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
				testMatrix2[i][j] = counter++;
				testMatrix1[i][j] = testMatrix2[i][j];
				
			}
		}
		testMatrix2[0][0] = Math.PI;
		assertFalse(DataUtilities.equal(testMatrix1,testMatrix2));
	}
	@Test
	public void testGetCumulativePercentagesHasCorrectNumberOfValues(){
		try{
			KeyedValues actual = DataUtilities.getCumulativePercentages(mockKVInput);
			int expected = mockKVOutput.getItemCount();
			int actualLength = actual.getItemCount();
			assertEquals("\033[31;1mFAILED: \033[0m number of key-value pairs does not match",expected,actualLength,0);
		}catch(AssertionError ae){
			System.out.println("FAILED. "+ ae.toString());
			fail();
		}
		catch(Exception e){
			System.out.println("FAILED. Unexpected exception thrown during runtime \""+
			e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testGetCumulativePercentagesReturnsCorrectKeys(){
		//This ONLY tests whether the values match expected values for the specified keys
		//It is unlikely, but still possible
		String[] keys = {"KEY 0","KEY 1","KEY 2","KEY 3"};
		try{
			KeyedValues actual = DataUtilities.getCumulativePercentages(mockKVInput);
			Object actualKeys[] = actual.getKeys().toArray();
			for(int i = 0; i < keys.length;i++) {
				assertEquals("Mismatch between keys",keys[i],actualKeys[i]);
			}
			System.out.println("\033[32;1mPASSED \033[0m");
		}catch(AssertionError ae){
			System.out.println("FAILED. "+ ae.toString());
			fail();
		}
		catch(Exception e){
			System.out.println("FAILED. Unexpected exception thrown during runtime \""+
			e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testGetCumulativePercentagesReturnsCorrectValues(){
		//This ONLY tests whether the values match expected values for the specified keys
		//It is unlikely, but still possible
		try{
			KeyedValues actual = DataUtilities.getCumulativePercentages(mockKVInput);
			for(int i = 0; i < mockKVOutput.getItemCount(); i++){
				Number expectedValue = mockKVOutput.getValue(i).doubleValue();
				Number actualValue = actual.getValue(i).doubleValue();
				assertEquals("\033[31;1mFAILED: \033[0m Values do not match at row " + i,expectedValue.doubleValue(),actualValue.doubleValue(),0.000000000001);
			}
		}catch(AssertionError ae){
			System.out.println("FAILED. "+ ae.toString());
			fail();
		}
		catch(Exception e){
			System.out.println("FAILED. Unexpected exception thrown during runtime \""+
			e.toString()+"\"");
			fail();
		}
	}
	@Test
	public void testGetCumulativePercentagesThrowsIllegalArgumentExceptionForNull(){
		System.out.println("Testing if GetCumulativePercentages throws an illegal argument exception on null input");
		try{
			KeyedValues actual = DataUtilities.getCumulativePercentages(null);
			fail();
		}catch(AssertionError ae){
			System.out.println("\033[31;1mFAILED: \033[0m Expected IllegalArgumentException, none thrown");
			fail();
		}catch(IllegalArgumentException ie){
			System.out.println("\033[32;1mPASSED \033[0m: exception thrown \""+ie.toString()+"\"");
		}catch(Exception e){
			System.out.println("\033[31;1mFAILED: \033[0m Unexpected exception thrown \""+e.toString()+"\"");
			fail();
		}
	}
}
