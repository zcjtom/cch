package com.uxsino.sets;
import java.util.Collection;

/**
 * 
 * A class which can load a number of sets, and find supersets specified sets
 */

public interface ISetEngine {

    /**
     * Called once to enable you to store/load the data set
     * 
     * @param sets
     */
	public void Load(Collection<ISet> sets);

    /** 
     * Finds the IDs of all supersets of the specified set within the Loaded data
     * 
     * @param set
     */ 
    public Integer[] FindSupersetsOf(Integer[] set);
}

	
	
