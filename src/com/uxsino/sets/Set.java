package com.uxsino.sets;
public class Set implements ISet {

	 private Integer m_ID;
     private Integer[] m_Values;

     public Set()
     { 
     }

     public Set(Integer ID, Integer[] values)
     {
         m_Values = values;
         m_ID = ID;
     }
     
       
     public Integer getID() 
     {
    	return m_ID;  
     }
     

     public Integer[] getValues()
     {
    	 return m_Values; 
     }
     
}

