package com.uxsino.sets.tests;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.uxsino.sets.ISet;
import com.uxsino.sets.ISetEngine;
import com.uxsino.sets.Set;
import com.uxsino.sets.implementation.SetEngineImplementation;

import static org.junit.Assert.*;

public class SetEngineImplementationTest{

		//
		// Simple tests to validate functional behavior
		//
	    @Test public void BasicTest() {
	    	
	    	//ISetEngine setEngine = new SetEngineImplementation(); 
	    	ISetEngine setEngine = new SetEngineImplementation();
	    	
	    	ArrayList<ISet> sets = new ArrayList<ISet>(); 
	    	
	    	sets.add(new Set(1, new Integer[] {1,2,3,4}));
	    	sets.add(new Set(2, new Integer[] {1,4,5,6}));	
	    	sets.add(new Set(3, new Integer[] {9,7,8,1}));
	    	sets.add(new Set(4, new Integer[] {1,7,8,9,99}));
	    	sets.add(new Set(5, new Integer[] {7,2,8,9,99}));

	    	setEngine.Load(sets);
	    	
	    	Integer[] superSets = setEngine.FindSupersetsOf(new Integer[] {1,2});
	    	assertEquals(1, superSets.length);
	    	assertArrayEquals(new Integer[] {1}, superSets);
	    	
	    	superSets = setEngine.FindSupersetsOf(new Integer[] {1,5,12});	    	
	    	assertEquals(0, superSets.length);
	    	
	    	superSets = setEngine.FindSupersetsOf(new Integer[] {1,7,8,9});	    	
	    	assertEquals(1, superSets.length);
	    	assertArrayEquals(new Integer[] {4}, superSets);
	    	
	    	superSets = setEngine.FindSupersetsOf(new Integer[] {1,7});	    	
	    	assertEquals(2, superSets.length);
	    	assertTrue(Arrays.asList(superSets).contains(3));
	    	assertTrue(Arrays.asList(superSets).contains(4));

	    }
	    
	    @Test public void PerfomanceTest() {
	    	
	    	//ISetEngine setEngine = new SetEngineImplementation(); 
	    	ISetEngine setEngine = new SetEngineImplementation();
	    	
	    	
	    	Collection<ISet> sets = LoadSetsFromCSV("Sets.csv");
	    	
	    	//C# : Dictionary<int, ISet> setsByID = sets.ToDictionary( s => s.ID, s => s );

	    	HashMap<Integer, ISet> setsByID = new HashMap<Integer, ISet>();
	    	for(ISet s:sets) setsByID.put(s.getID(), s); 
	    	 	
	    	setEngine.Load(sets);
	    	
	    	//
	    	// Run the test once and throw away the first results
	    	//
	    	
	    	RunPerformanceTests(setEngine, setsByID);
	    	RunPerformanceTests(setEngine, setsByID);
	    	RunPerformanceTests(setEngine, setsByID);
	    	RunPerformanceTests(setEngine, setsByID);
	    	
	    	float cumulativeTime = 0; 
	    	
	    	for(int i = 0; i<100; i++) 
	    	{
	    		float time = RunPerformanceTests(setEngine, setsByID);
	    		System.out.println("Cumulative time for performance test #"+ (i+1) + ": "+ time/1000000 + " ms");
	    		cumulativeTime += time; 
	    	}
	    	
	    	System.out.println("\nAverage time (out of 100 runs) to run the tests: " + (cumulativeTime/100)/1000000 + "ms"); 
	    
	    }
	    
	    //returns, in nanoseconds, the cumulative time required to run all performance tests
	    private long RunPerformanceTests(ISetEngine setEngine, HashMap<Integer, ISet> setsByID) 
	    {
	    	long cumulativeTime = 0;
	    	
	    	//
	    	// First test
	    	//
	    	

	    	
	    	Integer[] set = new Integer[] {1, 2};
	    	Integer[] superSets = null; 
	    	   	
	    	long start = System.nanoTime();    	
	    	superSets = setEngine.FindSupersetsOf(set);
	    	long stop = System.nanoTime();
	    	
	    	assertEquals(5, superSets.length);
	    	AssertSetsAreSupersets(set, superSets, setsByID);
	    	
	    	cumulativeTime += (stop - start);
	    	
	    	//System.out.println("Test 1 took " + (stop-start) + "ms");
	    	
	    	//
            // Second test
            //
	    	set = new Integer[] { 1310, 206 };
	    	
	    	start = System.nanoTime();            
            superSets = setEngine.FindSupersetsOf(set);
            stop = System.nanoTime();
            
            assertEquals(218, superSets.length);
            AssertSetsAreSupersets(set, superSets, setsByID);

            cumulativeTime += (stop - start);

            //System.out.println("Test 2 took " + (stop-start) + "ms");
	    	
            //
            // Third test
            //
            set = new Integer[] { 39484, 4510 };

            start = System.nanoTime();
            superSets = setEngine.FindSupersetsOf(set);
            stop = System.nanoTime();
            assertEquals(240, superSets.length);
            AssertSetsAreSupersets(set, superSets, setsByID);

            cumulativeTime += (stop - start);

            //System.out.println("Test 3 took " + (stop-start) + "ms");
            
            
            //
            // Fourth test
            //
            set = new Integer[] { 5318 };
            
            start = System.nanoTime();
            superSets = setEngine.FindSupersetsOf(set);
            stop = System.nanoTime();
            
            assertEquals(6938, superSets.length);
            AssertSetsAreSupersets(set, superSets, setsByID);

            cumulativeTime += (stop - start);

            //System.out.println("Test 4 took " + (stop-start) + "ms");
            
            
            //
            // Fifth test
            //
            set = new Integer[] { 31253, 36863, 107, 25002, 49 };
            
            start = System.nanoTime();
            superSets = setEngine.FindSupersetsOf(set);
            stop = System.nanoTime();
            
            assertEquals(0, superSets.length);
            AssertSetsAreSupersets(set, superSets, setsByID);

            cumulativeTime += (stop - start);

            //System.out.println("Test 5 took " + (stop-start) + "ms");
            
            
            //
            // Sixth test
            //
            set = new Integer[] { 7248, 517, 16339, 39484, 9992, 20976, 25640, 5318, 107, 1861, 6341, 292, 25211, 83 };

            start = System.nanoTime();
            superSets = setEngine.FindSupersetsOf(set);
            stop = System.nanoTime();
            
            assertEquals(0, superSets.length);
            AssertSetsAreSupersets(set, superSets, setsByID);

            cumulativeTime += (stop - start);

            //System.out.println("Test 6 took " + (stop-start) + "ms");
            
            
            //
            // Seventh test
            //
            set = new Integer[] { 65, 381, 12416, 5318, 15830 };
            
            start = System.nanoTime();
            superSets = setEngine.FindSupersetsOf(set);
            stop = System.nanoTime();
            
            assertEquals(0, superSets.length);
            AssertSetsAreSupersets(set, superSets, setsByID);

            cumulativeTime += (stop - start);

            //System.out.println("Test 7 took " + (stop-start) + "ms");
            
            
            //
            // Eight test
            //
            set = new Integer[] { 1747, 2616, 29470, 5288, 1091 };
            
            start = System.nanoTime();
            superSets = setEngine.FindSupersetsOf(set);
            stop = System.nanoTime();
            
            assertEquals(7, superSets.length);
            AssertSetsAreSupersets(set, superSets, setsByID);

            cumulativeTime += (stop - start);

            //System.out.println("Test 8 took " + (stop-start) + "ms");
            
            //
            // Ninth test
            //
            set = new Integer[] { 1747, 2616, 29470, 5288};
            
            start = System.nanoTime();
            superSets = setEngine.FindSupersetsOf(set);
            stop = System.nanoTime();
            
            assertEquals(386, superSets.length);
            AssertSetsAreSupersets(set, superSets, setsByID);

            cumulativeTime += (stop - start);

            //System.out.println("Test 9 took " + (stop-start) + "ms");
            
            
            //
            // Tenth test
            //
            set = new Integer[]  { 65, 5318 };
            
            start = System.nanoTime();
            superSets = setEngine.FindSupersetsOf(set);
            stop = System.nanoTime();
            
            assertEquals(0, superSets.length);
            AssertSetsAreSupersets(set, superSets, setsByID);

            cumulativeTime += (stop - start);

            //System.out.println("Test 10 took " + (stop-start) + "ms");
            
            
            //
            // Eleventh test
            //
            set = new Integer[]  { 7248, 517, 16339, 39484, 9992, 20976, 25640, 5318, 107, 1861, 6341, 292, 25211 };
            
            start = System.nanoTime();
            superSets = setEngine.FindSupersetsOf(set);
            stop = System.nanoTime();  
            
            assertEquals(1, superSets.length);
            AssertSetsAreSupersets(set, superSets, setsByID);

            cumulativeTime += (stop - start);

            //System.out.println("Test 11 took " + (stop-start) + "ms");
        	
           // System.out.println("Cumulative time " + cumulativeTime + "ms");
            
	    	return cumulativeTime;
	    	
	    }
	    
	    //
	    // Given all the claimed 'supersets' of the set, verify each one is really a superset
	    //
	    private void AssertSetsAreSupersets(Integer[] set, Integer[] possibleSupersetIDs, HashMap<Integer, ISet> setsByID) {
	    	
	    	 for (int i = 0; i < possibleSupersetIDs.length; i++)
	            {
	                int possibleSupersetID = possibleSupersetIDs[i];

	                ISet possibleSuperset = setsByID.get(possibleSupersetID);
	                
	                assertTrue(IsSuperSet(set, possibleSuperset.getValues()));
	            }
	    }
	    
	    //
	    //return true iff possibleSuperset is a superset of set
	    //
	    private boolean IsSuperSet(Integer[] set, Integer[] possibleSuperset) 
	    {
	    	return IsSupersetOf(Arrays.asList(possibleSuperset), Arrays.asList(set));
	    }  
	    
	    
	    //
	    //return true iff a is a superset of b
	    //
	    private boolean IsSupersetOf(List<Integer> a,  List<Integer> b)
	    {
	        if (a  == null || b == null)
	            throw new IllegalArgumentException( "Cannot compare \"null\" collections.");

	        if (a.size() < b.size())
	        {
	         	return false;
	        }
	            
	        Iterator<Integer> it = b.iterator();
	        
	        while (it.hasNext()) 
	        {
	            if (!a.contains(it.next())) {
	            	System.out.println("b contains something a doesn't");
	            	return false;
	            }
	        }
	        
	        return true;
	        
	    }

	    
	    
	    
	    //
	    // Parse our funny CSV/XML file format and return ISet objects for each row
	    //
	    private Collection<ISet> LoadSetsFromCSV(String filename) {
	    	
	    	Collection<ISet> sets = new ArrayList<ISet>(); 
	    	
	    	File file = new File(filename);
	    	
	    	try 
	    	{
				BufferedReader bufRdr = new BufferedReader(new FileReader(file));
				
				String line = null;
				
				while((line = bufRdr.readLine()) != null) 
				{
					String[] fields = line.split(",");
					
					int ID = Integer.parseInt(fields[0]);
					String xmlValues = fields[1];
					
					String[] stringArray = xmlValues.split("(<[^>]+>[ ]*)+");
					
					//we must cast the contents of our String array to Integers - note, because of our split regex, the first element of stringArray is the empty string
					Integer[] integerArray = new Integer[stringArray.length-1];
					
					for(int i = 1; i<stringArray.length; i++) { 
						integerArray[i-1] = new Integer(stringArray[i]);
					}
					
					sets.add(new Set(ID, integerArray));
					
				}//while
				
				bufRdr.close();
	    	
	    	} 
	    	catch (FileNotFoundException e)
	    	{	
	    		e.printStackTrace();
			} 
	    	catch (IOException e) 
			{
				e.printStackTrace();
			} 
		
			return sets;
	    }
}
