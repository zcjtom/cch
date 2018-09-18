package com.uxsino.sets.implementation;


import java.util.Collection;

import com.uxsino.sets.ISet;
import com.uxsino.sets.ISetEngine;


public class SetEngineImplementation implements ISetEngine {

	
	public void Load(Collection<ISet> sets)
	{
		for(ISet set:sets) 
		{
			//load me
		}
	}



	public Integer[] FindSupersetsOf(Integer[] set)
	{
			
		return new Integer[] {};
    }


}
